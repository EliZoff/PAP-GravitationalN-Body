package prova;

import java.awt.*;
import javax.swing.*;

public class My_panel extends JPanel {
	static int x = 25;
    static int y = 25;
    JFrame f;
    public My_panel(){
    	super();
    }
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.RED);
    	g.fillOval(x-25,y-25,50,50);
    }
    public void incX(int i){
    	this.x+=i;
    }
    public void incY(int i){
    	this.y+=i;
    }
    
    public void setFrame(JFrame f){
    	this.f=f;
    }
    
    public int checkCollisions() {
    	Rectangle b = f.getBounds();
    	System.out.println(b.height);
        if((x - 25 + 10)< 0) {
            return 0;
        }
        else if(y - 25 + 10< 0) {
            return 1;
        }
        else if(x + 25 +10 > b.width) {//???
            return 2;
        }
        else if(y + 25 + 10 > b.height) {//????
            return 3;
        }
        else {
            return -1;
        }
    }
}
