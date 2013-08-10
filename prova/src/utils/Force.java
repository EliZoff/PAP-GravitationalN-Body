package utils;

public class Force {
	private double val;
	private double fx, fy;
	private String dirX, dirY;
	public Force(double val, double fx, double fy){
		this.val=val;
		this.fx=fx;
		this.fy=fy;
	}
	public Force(double val){
		this.val=val;
	}
	public Force(double val, double fx, double fy, String dirX, String dirY){
		this.val=val;
		this.dirX=dirX;
		this.dirY=dirY;
		this.fx=fx;
		this.fy=fy;
	}
	//calcola la direzione della forza sull'asse x
	public void findDirX(double x1, double x2){
		if((x1-x2)>0){
			//se la x del primo pianeta è > della x del secondo il pianeta dovrà decrementare la propria x per avvicinarcisi
			this.dirX="-";
		}else{
			this.dirX="+";
		}
	}
	public void findDirY(double y1, double y2){
		if((y1-y2)>0){
			this.dirY="-";
		}else{
			this.dirY="+";
		}
	}
	
	public double getValue(){
		return val;
	}
	
	public String getDirX(){
		return dirX;
	}
	public String getDirY(){
		return dirY;
	}
	
	public double getCompX(){
		return fx;
	}
	public void setCompX(double fx){
		this.fx = fx;
	}
	
	public double getCompY(){
		return fy;
	}
	public void setCompY(double fy){
		this.fy = fy;
	}
	
}
