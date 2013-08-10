package prova_gravità_canvas;

import java.util.ArrayList;

import utils.*;

public class CalcForcesThread implements Runnable{
	private ArrayList<Planet> listPl;
	private Planet pl;
	private Utility util;
	private static final double G = 6.67E-11;
	
	public CalcForcesThread(Planet pl, ArrayList<Planet> listPl){
		this.pl = pl;
		this.listPl = listPl;
		util = new Utility();
	}
	public void run() {
		calcForces();
	}
	
	private void calcForces(){
		// calcolo la forza iniziale tra tutti i pianeti
		Force[] force; // array di forze
		int  k = 0;
		double c, b; // cateti
		double t; // angolo acuto
		double fx = 0, fy = 0, totFval = 0;
		String dirFx = "", dirFy = "";
		String direction;
			force = new Force[listPl.size() - 1];
			k = 0;
			for (Planet plo : listPl) {
				if (!pl.equals(plo)) {
					// tranne che per me stesso
					force[k] = new Force( calcForceVal(pl.getM(), plo.getM(), util.calcDist(pl.getX(), pl.getY(), plo.getX(), plo.getY())));
					force[k].findDirX(pl.getX(), plo.getX());
					force[k].findDirY(pl.getY(), plo.getY());
					// trovo le componenti x e y della forza
					b = util.calcDistX(pl.getX(), plo.getX());
					c = util.calcDistY(pl.getY(), plo.getY());
					t = Math.atan2(c, b);
					
					force[k].setCompX(force[k].getValue()*Math.cos(t));
					force[k].setCompY(force[k].getValue()*Math.sin(t));
					
					// calcolo la risultante delle forze
					if (k == 0) {
						// se è la prima forza calcolata
						
						fx = force[k].getCompX();
						fy = force[k].getCompY();
						// e indico come direzione della risultante
						// quella della forza appena trovata
						dirFx = force[k].getDirX();
						dirFy = force[k].getDirY();
					} else {
						// se non è la prima forza
						// calcolo la risultante delle forze trovate fin'ora considerando le direzioni
						direction = force[k].getDirX() + dirFx;
						switch (direction) {
							case "++":
							case "--":
								fx = fx + (force[k].getCompX());
								break;
							case "+-":
							case "-+":
								fx = fx - (force[k].getCompX());
								break;
						}
						if (fx < 0) {
							// se la risultante è negativa allora
							// devo cambiare la direzione
							dirFx = force[k].getDirX();
							fx = -fx;
						}
						direction = force[k].getDirY() + dirFy;
						switch (direction) {
							case "++":
							case "--":
								fy = fy + (force[k].getCompY());
								break;
							case "+-":
							case "-+":
								fy = fy - (force[k].getCompY());
								break;
						}
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
	
	private double calcForceVal(double m1, double m2, double dist) {
		// calcolo valore della forza
		double f;
		f = (G * m1 * m2) / (dist * dist);
		return f;

	}
}
