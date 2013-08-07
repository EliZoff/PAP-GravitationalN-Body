package prova_palline_gravità;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class My_panel extends JPanel {
	
			
    private ArrayList<Planet> listPl = new ArrayList<Planet>();

    
    public My_panel(){
    	super();
    }
    
    public My_panel(ArrayList<Planet> listPl){
    	super();
    	this.listPl=listPl;
    }
    
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.RED);
    	for(Planet pl:listPl){
    		
    		g.fillOval((int)(pl.getX()-pl.getR()),(int)(pl.getY()-pl.getR()),pl.getR()*2,pl.getR()*2);
    	}
    }    
}
 