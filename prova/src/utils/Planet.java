package utils;

public class Planet {
	private double x=0, y=0;
	private double m;
	private int r;
	private double p;
	private Force[] f;
	private Force totalForce;
	private double vix, viy;
	
	public Planet(int x, int y, double m, int r){
		this.x=x;
		this.y=y;
		this.m=m;
		this.r=r;
		this.vix=0;
		this.viy=0;
	}
	public void setForces(Force[] f){
		this.f=f;
	}
	public Force[] getForces(){
		return f;
	}
	
	public void setTotalForce(Force f){
		this.totalForce=f;
	}
	public Force getTotalForces(){
		return totalForce;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
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
	
	public void setViX(double vix){
		this.vix=vix;
	}
	public double getViX(){
		return vix;
	}
	
	public void setViY(double viy){
		this.viy=viy;
	}
	public double getViY(){
		return viy;
	}
	
	public void incX(double incX){
		this.x+=incX;
	}
	public void incY(double incY){
		this.y+=incY;
	}
	
}
