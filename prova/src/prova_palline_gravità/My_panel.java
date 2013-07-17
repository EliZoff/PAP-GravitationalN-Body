package prova_palline_gravità;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class My_panel extends JPanel {
	
			
	private int x1 = 30;
    private int y1 = 30;
    private int x2 = 350;
    private int y2 = 350;
    
    
    
    int r1=30,r2=30;
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
    public void genBalls(){
    	Rectangle b = this.getBounds();
    	Random rn = new Random(System.nanoTime());
    	//genero casualmente le coordinare del centro della prima palla
    	x1=rn.nextInt(b.width);
    	y1=rn.nextInt(b.height);
    	//se le coordinate genetate posizionano la palla oltre il bordo del panel le rigenero
    	while((x1 +r1 >= b.width||x1-r1<=0)||(y1 +r1 >= b.height||y1-r1<=0)){
    		x1=rn.nextInt(b.width);
        	y1=rn.nextInt(b.height);
    	}
    	//genero casualmente le coordinare del centro della seconda palla
    	x2=rn.nextInt(b.width);
    	y2=rn.nextInt(b.height);
    	//se le coordinate genetate posizionano la palla oltre il bordo del panel o sovrapposta alla prima le rigenero
    	while((calcDist(x1, y1, x2, y2)<=(r1+r2)+1)||(x2 +r2 >= b.width||x2-r2<=0)||(y2 +r2 >= b.height||y2-r2<=0)){
    		x2=rn.nextInt(b.width);
        	y2=rn.nextInt(b.height);
    	}
    	//ridisegno le figure con le nuove coordinate
    	f.repaint();
    	
    }
    public double calcDist(int x1c, int y1c, int x2c, int y2c){
    	//calcola la distanza tra due punti attraverso il teorema di pitagora
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2) + Math.pow((y2c-y1c), 2));
    	return result;
    }
    
    public double calcDistX(int x1c, int x2c){
    	//calcola la distanza tra le coordinate x di due punti
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2));
    	return result;
    }
    public double calcDistY(int y1c, int y2c){
    	//calcola la distanza tra le coordinate y di due punti
    	double result;
    	result=Math.sqrt(Math.pow((y2c-y1c), 2));
    	return result;
    }
    public void incP1(int xinc, int yinc){
    	//incrementa le coordinate della prima palla
    	double c, b, t;
    	double distR, b1, c1;
    	//se la distanza tra i centri delle 2 considerando l'incremento da fare è > della somma dei due raggi
    	if(calcDist(this.x1+ xinc, this.y1 + yinc, this.x2, this.y2)>(r1+r2)) {
    		//posso eseguire l'incremento senza sovrapposizioni tra le due
    		this.x1+=xinc;
    		this.y1+=yinc;
    	}else{
    		//altrimenti calcolo le componenti x e y della distanza rimanente in modo da far collidere le figure
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

    public void incP2(int xinc, int yinc){
    	double c, b, t;
    	double distR, b1, c1;
    	if(calcDist(this.x1, this.y1, this.x2 + xinc, this.y2 + yinc)>(r1+r2)) {
    		this.x2+=xinc;
    		this.y2+=yinc;
    	}else{
    		c = calcDistX(this.x1, this.x2);
    		b = calcDistY(this.y1, this.y2);
    		t = Math.atan2(c, b);
    		distR=calcDist(this.x1, this.y1, this.x2, this.y2) - (r1+r2);
    		c1 = distR*Math.sin(t);
    		b1= distR*Math.cos(t);
    		this.x2-=c1;
    		this.y2-=b1;
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
        if(calcDist(this.x1, this.y1, this.x2, this.y2)<=(r1+r2)+1){
        	//+1 perchè essendo le coordinate int e la distanza double potrebbe rimanere uno spazio tra le figure che al max può essere 1
            return true;
        }
        else {
            return false;
        }
    }
    
}
 