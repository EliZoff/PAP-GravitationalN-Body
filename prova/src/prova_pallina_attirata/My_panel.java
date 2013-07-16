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
    public double calcDist(int x1c, int y1c, int y2c, int x2c){
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2) + Math.pow((y2c-y1c), 2));
    	return result;
    }
    
    public double calcDistX(int x1c, int x2c){
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2));
    	return result;
    }
    public double calcDistY(int y1c, int y2c){
    	double result;
    	result=Math.sqrt(Math.pow((y2c-y1c), 2));
    	return result;
    }
    public void inc(int xinc, int yinc){
    	double c, b, t;
    	double distR, b1, c1;
    	double prova;
    	prova = Math.atan2(Math.sqrt(3), 3);
    	if(calcDist(this.x1+ xinc, this.y1 + yinc, this.x2, this.y2)>(r1+r2)) {
    		System.out.println(calcDist(this.x1+ xinc, this.y1 + yinc, this.x2, this.y2));
    		
    		this.x1+=xinc;
    		this.y1+=yinc;
    	}else{
    		c = calcDistX(this.x1, this.x2);
    		b = calcDistY(this.y1, this.y2);
    		t = Math.atan2(c, b);
    		distR=calcDist(this.x1, this.y1, this.x2, this.y2) - (r1+r2);
    		c1 = distR*Math.sin(t);
    		b1= distR*Math.cos(t);
    		this.x1+=c1;
    		this.y1+=b1;
    	}
    }

    public void incX(int xinc){
    	Rectangle b = this.getBounds();
    	
    	//if(calcDistX()-(compX1+compX2)>=xinc) {
    		//this.x1+=xinc;
    	//}else{
    		//this.x1+=calcDistX()-(compX1+compX2);
    		/*if(x1 + 30 + xinc > b.width){
    			this.x1+=(b.width-x1)-30;
    		}else{
    			this.x1-=x1-30;
    		}*/
    	//}
    }
    public void incY(int yinc){
    	Rectangle b = this.getBounds();
    	//double angolo, compY1, compY2;
    	//angolo=calcDistY()/calcDistX();
    	//compY1=r1*Math.sin(angolo);
    	//compY2=r2*Math.sin(angolo);
    	//System.out.println("Y: "+(calcDistY()-(compY1+compY2)));
    	//if(calcDist(this.x1, this.y1, this.x2, this.y2)-(compY1+compY2)>=yinc) {
    		//this.y1+=yinc;
    	//}else{
    		
    		//this.y1+=calcDistY()-(compY1+compY2);
    		/*if(y1 + 30 + yinc > b.height){
    			this.y1+=(b.height-y1)-30;
    		}else{
    			this.y1-=y1-30;
    		}*/
    			
    	//}
    	
    }
    
    public void setFrame(JFrame f){
    	this.f=f;
    }
    
    public boolean checkCollisions() {
    	Rectangle b = this.getBounds();
    	
        if(calcDist(this.x1, this.y1, this.x2, this.y2)==(r1+r2)){//????
            return true;
        }
        else {
            return false;
        }
    }
    
}
 