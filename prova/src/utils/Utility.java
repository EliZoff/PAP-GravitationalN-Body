package utils;

import java.util.Random;

public class Utility {
	
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

	public int[] genRadius(int nPl) {
		int[] r = new int[nPl];
		Random rn = new Random(System.nanoTime());
		for (int i = 0; i < nPl; i++) {
			r[i] = rn.nextInt(99) + 1;
		}
		return r;
	}

}
