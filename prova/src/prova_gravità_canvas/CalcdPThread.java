package prova_gravità_canvas;

import utils.*;

public class CalcdPThread implements Runnable{

	private Utility util;
	private Planet pl;
	private int dt;
	
	public CalcdPThread(Planet pl, int dt){
		this.pl = pl;
		this.dt = dt;
		util = new Utility();
	}
	
	@Override
	public void run() {
		calcdP();
		
	}
	
	
	private void calcdP(){
		double ax, ay; // accelerazione
		double vix = 0, viy = 0, vfx, vfy; // velocità iniziale e finale
		double pix, piy, dpx, dpy; // posizione iniziale e spostamento
		String direction; // direzione della forza
		
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
		pix = util.calcDistX(0, pl.getX());
		piy = util.calcDistY(0, pl.getY());

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
	
	private double calcAcc(double f, double m) {
		// calcolo valore dell'accelerazione
		return f / m;

	}

	private double calcVel(double vi, double dt, double a) {
		// calcolo valore velocità
		double v = vi + a * dt;
		return v;
	}

	private double calcdP(double vi, double dt, double a) {
		// calcolo spostamento
		double p = vi * dt + (0.5) * a * (dt * dt);
		return p;
	}
}


