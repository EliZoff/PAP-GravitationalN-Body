package prova;

import java.awt.*;
import javax.swing.*;

public class My_panel extends JPanel {
	static int x = 20;
    static int y = 20;
    public My_panel(){
    	super();
    }
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.RED);
    	g.fillOval(x,y,50,50);
    }
    public void incX(int i){
    	this.x+=i;
    }
    public void incY(int i){
    	this.y+=i;
    }
    
    public int checkCollisions() {
        if(x <= 0) {
            return 0;
        }
        else if(y <= 0) {
            return 1;
        }
        else if(x >= 300) {//???
            return 2;
        }
        else if(y >= 300) {//????
            return 3;
        }
        else {
            return -1;
        }
    }
}
