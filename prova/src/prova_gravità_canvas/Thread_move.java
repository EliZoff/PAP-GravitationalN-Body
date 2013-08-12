package prova_gravità_canvas;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.swing.*;

import utils.*;

public class Thread_move extends Thread {
	private JFrame f;
	private Canvas drawer;
	//private JPanel panel;
	private Box boxDrawer;
	private ArrayList<Planet> planets;
	private Semaphore startMove, calcForceEnd, calcDpEnd;
	private boolean stop;
	private Utility util;
	

	public Thread_move() {
		planets = new ArrayList<Planet>();
		startMove = new Semaphore(0);
		calcForceEnd = new Semaphore(0);
		calcDpEnd = new Semaphore(0);
		stop = false;
		util = new Utility();
	}

	public void setGraphicComponents(JFrame f, /* JPanel panel, */Box boxDrawer) {
		this.f = f;
		this.boxDrawer = boxDrawer;
		// this.panel = panel;
	}

	public void setDrawer(Canvas drawer) {
		this.drawer = drawer;
	}
	
	public void startMove(){
		this.startMove.release();
	}
	
	public void stopMove(){
		this.stop = true;
	}
	
	public void signalCalcForceEnd(){
		this.calcForceEnd.release();
	}
	
	public void signalCalcDpEnd(){
		this.calcDpEnd.release();
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
					if ((util.calcDist(pl.getX(), pl.getY(), x.getX(), x.getY()) <= (pl.getR() + x.getR()) + 1)) {
						flagColl = true;
					}
				}
				// controllo che il pianeta sia dentro lo schermo
				if ((pl.getX() + pl.getR() < (b.width - 150) && pl.getX() - pl.getR() > 0)
						&& (pl.getY() + pl.getR() < b.height && pl.getY() - pl.getR() > 0) && !flagColl) {
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

	



	public boolean checkCollisions(double x1, double x2, double y1, double y2, double r1, double r2) {
		// controllo se sono avvenute collisioni
		double d = util.calcDist(x1, y1, x2, y2);
		double r = (r1 + r2);
		if (d <= r/* +1 */) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	

	public void run() {
		int dt = 50; // delta time
		
		ExecutorService executorService;
		// double[] ms = {5.97E10,6.39E11,7.34E8,50,35,6,80,20,50,35};
		boolean collision = false; // flag collisioni
		/*
		 * box.setSize((1130-150),(730-39)); p.setSize((1130-150),(730-39));
		 * genPlanets(3, ms, rg); //generazione coordinate casuali dei pianeti
		 */
		while (true) {
			try {
				startMove.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			collision = false;

			
			
			
			
			while (!collision) {
				if(stop){
					try {
						startMove.acquire();
						this.stop = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// calcolo le nuove forze dopo lo spostamento e la
				// risultante
				executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				for(Planet pl:planets){
					CalcForcesThread cf = new CalcForcesThread(pl, planets, this);
					executorService.execute(cf);
				}
				executorService.shutdown();
				try {
					calcForceEnd.acquire(this.planets.size());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("Forces calculated");
				
				
				// trovo lo spostamento (3 PIANETI)
				executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				for(Planet pl:planets){
					CalcdPThread cdP = new CalcdPThread(pl, dt, this);
					executorService.execute(cdP);
				}
				executorService.shutdown();
				try {
					this.calcDpEnd.acquire(this.planets.size());
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				System.out.println("Move done");
				
				
				//controllo le collisioni
				for (Planet pl : planets) {
					for (Planet plo : planets) {
						if (!pl.equals(plo)) {
							if (!collision) {
								collision = checkCollisions(pl.getX(), plo.getX(), pl.getY(), plo.getY(), pl.getR(), plo.getR());
							}
						}
					}
				}
				// ridisegno i pianeti con le nuove posizioni
				drawer.repaint();
				try {
					Thread.sleep(dt);
				} catch (InterruptedException e) {

				}
			}

			System.out.println("collisione");
		}

	}

}
