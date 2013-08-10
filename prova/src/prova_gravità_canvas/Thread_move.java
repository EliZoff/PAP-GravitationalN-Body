package prova_gravità_canvas;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import utils.Force;
import utils.Planet;

public class Thread_move extends Thread {
	static final double G = 6.67E-11;
	private JFrame f;
	private Canvas drawer;
	//private JPanel panel;
	private Box boxDrawer;
	private ArrayList<Planet> planets;
	boolean restart;

	public Thread_move() {
		restart = false;
		planets = new ArrayList<Planet>();
	}

	public void setGraphicComponents(JFrame f, /* JPanel panel, */Box boxDrawer) {
		this.f = f;
		this.boxDrawer = boxDrawer;
		// this.panel = panel;
	}

	public void setDrawer(Canvas drawer) {
		this.drawer = drawer;
	}

	public double calcDist(double x1c, double y1c, double x2c, double y2c) {
		// calcola la distanza tra due punti attraverso il teorema di pitagora
		double result;
		result = Math.sqrt(Math.pow((x2c - x1c), 2) + Math.pow((y2c - y1c), 2));
		return result;
	}

	public double calcDistX(double x1c, double x2c) {
		// calcola la distanza tra le coordinate x di due punti
		double result;
		result = Math.sqrt(Math.pow((x2c - x1c), 2));
		return result;
	}

	public double calcDistY(double y1c, double y2c) {
		// calcola la distanza tra le coordinate y di due punti
		double result;
		result = Math.sqrt(Math.pow((y2c - y1c), 2));
		return result;
	}

	public double[] genMass(int nPl) {
		double[] ms = new double[nPl];
		double m;
		int esp;
		Random rn = new Random(System.nanoTime());
		for (int i = 0; i < nPl; i++) {
			m = rn.nextDouble() * 10;
			while (m < 1.0) {
				m = rn.nextDouble() * 10;
			}
			esp = rn.nextInt(3) + 10;
			ms[i] = m * Math.pow(10, esp);
		}
		return ms;
	}

	public int[] genRaggio(int nPl) {
		int[] r = new int[nPl];
		Random rn = new Random(System.nanoTime());
		for (int i = 0; i < nPl; i++) {
			r[i] = rn.nextInt(99) + 1;
		}
		return r;
	}

	public void genPlanets(int nPl, double[] m, int[] r) {
		// genera l'insieme di nPl pianeti di massa m e raggio r
		Rectangle b = drawer.getBounds();
		Random rn = new Random(System.nanoTime());
		boolean flagOk = false, flagColl = false;
		Canvas newDrawer;
		boxDrawer.remove(drawer);
		planets.clear();
		for (int i = 0; i < nPl; i++) {
			flagOk = false;
			Planet pl = new Planet(0, 0, m[i], r[i]);
			// genero casualmente le coordinare del centro della prima palla

			// se le coordinate genetate posizionano la palla oltre il bordo del
			// panel o sopra un'altra le rigenero
			while (!flagOk) {
				pl.setX(rn.nextInt(b.width));
				pl.setY(rn.nextInt(b.height));
				// flag controllo collisioni
				flagColl = false;
				// controllo tutta la lista dei pianeti per verificare che le
				// coordinate appena generate evitino collisioni
				for (Planet x : planets) {
					if ((calcDist(pl.getX(), pl.getY(), x.getX(), x.getY()) <= (pl
							.getR() + x.getR()) + 1)) {
						flagColl = true;
					}
				}
				// controllo che il pianeta sia dentro lo schermo
				if ((pl.getX() + pl.getR() < (b.width - 150) && pl.getX()
						- pl.getR() > 0)
						&& (pl.getY() + pl.getR() < b.height && pl.getY()
								- pl.getR() > 0) && !flagColl) {
					flagOk = true;
				}

			}
			planets.add(pl);
		}

		newDrawer = new My_canvas(planets); // nuovo JPanel
		// rimuovo e aggiungo il panel per disegnare i nuovi pianeti
		boxDrawer.add(newDrawer);

		// panel.validate();
		// panel.repaint();
		//box.setSize((1130 - 150), (730 - 39));
		newDrawer.setSize((f.getWidth() - 150), (f.getHeight() - 39));
		this.setDrawer(newDrawer);
		f.validate();

		f.repaint();
	}

	public double calcFg(double m1, double m2, double dist) {
		// calcolo valore della forza
		double f;
		f = (G * m1 * m2) / (dist * dist);
		return f;

	}

	public double calcAcc(double f, double m) {
		// calcolo valore dell'accelerazione
		return f / m;

	}

	public double calcVel(double vi, double dt, double a) {
		// calcolo valore velocità
		double v = vi + a * dt;
		return v;
	}

	public double calcdP(double vi, double dt, double a) {
		// calcolo spostamento
		double p = vi * dt + (0.5) * a * (dt * dt);
		return p;
	}

	public boolean checkCollisions(double x1, double x2, double y1, double y2, double r1, double r2) {
		// controllo se sono avvenute collisioni
		double d = calcDist(x1, y1, x2, y2);
		double r = (r1 + r2);
		if (d <= r/* +1 */) {
			return true;
		} else {
			return false;
		}
	}
	
	public void startThread(){
		restart = true;
	}

	public void run() {
		int dt = 50; // delta time
		double ax, ay; // accelerazione
		double vix = 0, viy = 0, vfx, vfy; // velocità iniziale e finale
		double pix, piy, dpx, dpy; // posizione iniziale e spostamento
		double c, b; // cateti
		double t; // angolo acuto
		// double[] ms = {5.97E10,6.39E11,7.34E8,50,35,6,80,20,50,35};
		double fx = 0, fy = 0, totFval = 0;
		String dirFx = "", dirFy = "";
		Force[] force; // array di forze
		int k = 0;
		boolean collision = false; // flag collisioni
		String direction; // direzione della forza
		/*
		 * box.setSize((1130-150),(730-39)); p.setSize((1130-150),(730-39));
		 * genPlanets(3, ms, rg); //generazione coordinate casuali dei pianeti
		 */
		while (true) {
			if (restart) {//USARE UN SEMAFORO
				collision = false;
				// calcolo la forza iniziale tra tutti i pianeti
				for (Planet pl : planets) {
					force = new Force[planets.size() - 1];
					k = 0;
					for (Planet plo : planets) {
						if (!pl.equals(plo)) {
							// tranne che per me stesso
							force[k] = new Force(calcFg(
									pl.getM(),
									plo.getM(),
									calcDist(pl.getX(), pl.getY(), plo.getX(),
											plo.getY())));
							force[k].findDirX(pl.getX(), plo.getX());
							force[k].findDirY(pl.getY(), plo.getY());

							// calcolo la risultante delle forze
							if (k == 0) {
								// se è la prima forza calcolata
								b = calcDistX(pl.getX(), plo.getX());
								c = calcDistY(pl.getY(), plo.getY());
								t = Math.atan2(c, b);
								// trovo le componenti x e y
								force[k].setCompX(force[k].getValue()
										* Math.cos(t));
								force[k].setCompY(force[k].getValue()
										* Math.sin(t));
								fx = force[k].getCompX();
								fy = force[k].getCompY();
								// e indico come direzione della risultante
								// quella della forza appena trovata
								dirFx = force[k].getDirX();
								dirFy = force[k].getDirY();
							} else {
								// se non è la prima forza
								b = calcDistX(pl.getX(), plo.getX());
								c = calcDistY(pl.getY(), plo.getY());
								t = Math.atan2(c, b);
								force[k].setCompX(force[k].getValue()*Math.cos(t));
								force[k].setCompY(force[k].getValue()*Math.sin(t));
								// calcolo la differenza tra le componenti x e y
								// della risultante trovata fin'ora
								// e della forza appena trovata
								fx = fx - (force[k].getCompX());
								fy = fy - (force[k].getCompY());
								if (fx < 0) {
									// se la differenza è negativa allora la
									// nuova forza è prevalente
									// quindi la direzione sarà uguale alla
									// forza attuale
									dirFx = force[k].getDirX();
									fx = -fx;
								}
								// altrimenti non cambia nulla
								if (fy < 0) {
									dirFy = force[k].getDirY();
									fy = -fy;
								}
							}
							k++;
						}
					}
					pl.setForces(force);
					// trovo il valore del modulo della risultante

					totFval = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
					// e la imposto nell'oggetto
					pl.setTotalForce(new Force(totFval, fx, fy, dirFx, dirFy));
				}
				while (!collision) {
					// trovo lo spostamento (2 PIANETI)
					for (Planet pl : planets) {
						// trovo la direzione della forza risultante che dipende
						// dalla posizione del pianeta rispetto agli altri
						direction = pl.getTotalForces().getDirX() + pl.getTotalForces().getDirY();

						// calcolo le due componenti dell'accelerazione e
						// velocità
						// attreaverso le componenti della forza risultante
						ax = calcAcc(pl.getTotalForces().getCompX(), pl.getM());
						ay = calcAcc(pl.getTotalForces().getCompY(), pl.getM());

						// la velocità attuale è una proprietà del pianeta
						vix = pl.getViX();
						viy = pl.getViY();

						vfx = calcVel(vix, (double) dt, ax);
						vfy = calcVel(viy, (double) dt, ay);

						// posizione attuale
						pix = calcDistX(0, pl.getX());
						piy = calcDistY(0, pl.getY());

						// calcolo le componenti dello spostameto
						dpx = calcdP(vix, dt, ax);
						dpy = calcdP(viy, dt, ay);

						switch (direction) {// a seconda della direzione della
											// forza trovo la nuova posizione
						case "++":
							pl.incX(dpx);
							pl.incY(dpy);
							break;
						case "--":
							pl.incX(-dpx);
							pl.incY(-dpy);
							break;
						case "+-":
							pl.incX(dpx);
							pl.incY(-dpy);
							break;
						case "-+":
							pl.incX(-dpx);
							pl.incY(dpy);
							break;
						}
						// aggiorno la velocità attuale
						pl.setViX(vfx);
						pl.setViY(vfy);
					}
					// calcolo le nuove forze dopo lo spostamento e la
					// risultante
					for (Planet pl : planets) {
						force = new Force[planets.size() - 1];
						k = 0;
						for (Planet plo : planets) {
							if (!pl.equals(plo)) {
								// tranne che per me stesso
								force[k] = new Force( calcFg(pl.getM(), plo.getM(), calcDist(pl.getX(), pl.getY(), plo.getX(), plo.getY())));
								force[k].findDirX(pl.getX(), plo.getX());
								force[k].findDirY(pl.getY(), plo.getY());
								// aggiorno le forze tra tutti i pianeti dopo il
								// movimento (cambia r^2)

								// calcolo la risultante delle forze aggiornate
								if (k == 0) {
									// se è la prima forza calcolata
									b = calcDistX(pl.getX(), plo.getX());
									c = calcDistY(pl.getY(), plo.getY());
									t = Math.atan2(c, b);
									// trovo le componenti x e y
									force[k].setCompX(force[k].getValue()*Math.cos(t));
									force[k].setCompY(force[k].getValue()*Math.sin(t));
									fx = force[k].getCompX();
									fy = force[k].getCompY();
									// e indico come direzione della risultante
									// quella della forza appena trovata
									dirFx = force[k].getDirX();
									dirFy = force[k].getDirY();
								} else {
									// se non è la prima forza
									b = calcDistX(pl.getX(), plo.getX());
									c = calcDistY(pl.getY(), plo.getY());
									t = Math.atan2(c, b);
									force[k].setCompX(force[k].getValue()*Math.cos(t));
									force[k].setCompY(force[k].getValue()*Math.sin(t));
									// calcolo la differenza tra le componenti x
									// e y della risultante trovata fin'ora
									// e della forza appena trovata
									fx = fx - (force[k].getCompX());
									fy = fy - (force[k].getCompY());
									if (fx < 0) {
										// se la differenza è negativa allora la
										// nuova forza è prevalente
										// quindi la direzione sarà uguale alla
										// forza attuale
										dirFx = force[k].getDirX();
										fx = -fx;
									}
									// altrimenti non cambia nulla
									if (fy < 0) {
										dirFy = force[k].getDirY();
										fy = -fy;
									}
								}
								if (!collision) {
									collision = checkCollisions(pl.getX(), plo.getX(), pl.getY(), plo.getY(), pl.getR(), plo.getR());
								}
								k++;
							}
						}
						// trovo il valore del modulo della nuova risultante
						totFval = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
						// e la imposto nell'oggetto
						pl.setTotalForce(new Force(totFval, fx, fy, dirFx, dirFy));
						// aggiorno le forze con la nuova posizione
						pl.setForces(force);
					}
					// ridisegno i pianeti con le nuove posizioni
					drawer.repaint();
					try {
						Thread.sleep(dt);
					} catch (InterruptedException e) {

					}
				}

				System.out.println("collisione");
				restart = false;
			}
			System.out.println("??");
		}

	}

}
