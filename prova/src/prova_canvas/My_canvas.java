package prova_canvas;

import java.awt.*;

public class My_canvas extends Canvas{
	static int x = 30;
    static int y = 30;
    
	public My_canvas(){
		super();
	}
	public void paint(Graphics g){
		g.setColor(Color.RED);
    	g.fillOval(x-30,y-30,60,60);
	}
	public void incX(int xinc){
    	Rectangle b = this.getBounds();
    	if(x + 30 + xinc <= b.width && x - 30 + xinc >= 0) {
    		this.x+=xinc;
    	}else{
    		if(x + 30 + xinc > b.width){
    			this.x+=(b.width-x)-30;
    		}else{
    			this.x-=x-30;
    		}
    	}
    }
    public void incY(int yinc){
    	Rectangle b = this.getBounds();
    	if(y + 30 + yinc <= b.height && y - 30 + yinc >= 0) {
    		this.y+=yinc;
    	}else{
    		if(y + 30 + yinc > b.height){
    			this.y+=(b.height-y)-30;
    		}else{
    			this.y-=y-30;
    		}
    			
    	}
    	
    }
    
    
    public boolean checkCollisionsX() {
    	Rectangle b = this.getBounds();
    	System.out.println(b.width+","+x);
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
    	
        if(x +30 >= b.width||x-30<=0){//????
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean checkCollisionsY() {
    	Rectangle b = this.getBounds();
    	System.out.println(b.height+","+y);
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
    	
        if(y +30 >= b.height||y-30<=0){//????
            return true;
        }
        else {
            return false;
        }
    }

}
