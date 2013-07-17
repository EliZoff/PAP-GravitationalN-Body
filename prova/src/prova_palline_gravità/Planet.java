package prova_palline_gravità;

public class Planet {
	private int x=0, y=0;
	private double m;
	private int r;
	public Planet(int x, int y, double m, int r){
		this.x=x;
		this.y=y;
		this.m=m;
		this.r=r;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getM() {
		return m;
	}
	public void setM(double m) {
		this.m = m;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}

}
