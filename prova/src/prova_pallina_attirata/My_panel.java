package prova_pallina_attirata;

import java.awt.*;

import javax.swing.*;

public class My_panel extends JPanel {
	static int x1 = 30;
    static int y1 = 30;
    static int x2 = 350;
    static int y2 = 350;
    int r1=30,r2=50;
    JFrame f;
    public My_panel(){
    	super();
    }
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.RED);
    	g.fillOval(x1-r1,y1-r1,r1*2,r1*2);
    	g.setColor(Color.BLACK);
    	g.fillOval(x2-r2, y2-r2, r2*2, r2*2);
    }
    public double calcDist(){
    	double result;
    	result=Math.sqrt(Math.pow((this.x2-this.x1), 2) + Math.pow((this.y2-this.y1), 2));
    	return result;
    }
    
    public double calcDistX(){
    	double result;
    	result=Math.sqrt(Math.pow((this.x2-this.x1), 2));
    	return result;
    }
    public double calcDistY(){
    	double result;
    	result=Math.sqrt(Math.pow((this.y2-this.y1), 2));
    	return result;
    }

    public void incX(int xinc){
    	Rectangle b = this.getBounds();
    	double angolo, compX1, compX2;
    	angolo=calcDistY()/calcDistX();
    	compX1=r1*Math.cos(angolo);
    	compX2=r2*Math.cos(angolo);
    	System.out.println("X: "+(calcDistY()-(compX1+compX2)));
    	if(calcDistX()-(compX1+compX2)>=xinc) {
    		this.x1+=xinc;
    	}else{
    		this.x1+=calcDistX()-(compX1+compX2);
    		/*if(x1 + 30 + xinc > b.width){
    			this.x1+=(b.width-x1)-30;
    		}else{
    			this.x1-=x1-30;
    		}*/
    	}
    }
    public void incY(int yinc){
    	Rectangle b = this.getBounds();
    	double angolo, compY1, compY2;
    	angolo=calcDistY()/calcDistX();
    	compY1=r1*Math.sin(angolo);
    	compY2=r2*Math.sin(angolo);
    	System.out.println("Y: "+(calcDistY()-(compY1+compY2)));
    	if(calcDist()-(compY1+compY2)>=yinc) {
    		this.y1+=yinc;
    	}else{
    		
    		this.y1+=calcDistY()-(compY1+compY2);
    		/*if(y1 + 30 + yinc > b.height){
    			this.y1+=(b.height-y1)-30;
    		}else{
    			this.y1-=y1-30;
    		}*/
    			
    	}
    	
    }
    
    public void setFrame(JFrame f){
    	this.f=f;
    }
    
    public boolean checkCollisions() {
    	Rectangle b = this.getBounds();
    	double angolo, compY1, compY2, compX1, compX2;
    	angolo=calcDistY()/calcDistX();
    	compY1=r1*Math.sin(angolo);
    	compY2=r2*Math.sin(angolo);
    	compX1=r1*Math.cos(angolo);
    	compX2=r2*Math.cos(angolo);
        /*if((x - 40 + 10)< 0) {
            return 0;
        }
        else if(y - 40 + 10< 0) {
            return 1;
        }
        else if(x + 40 +10 > b.width) {//???
            return 2;
        }
        else
        x + 30 >= b.width) {
        */ 
    	
        if(calcDistY()==(compY1+compY2)||calcDistX()==(compX1+compX2)){//????
            return true;
        }
        else {
            return false;
        }
    }
    
}
 