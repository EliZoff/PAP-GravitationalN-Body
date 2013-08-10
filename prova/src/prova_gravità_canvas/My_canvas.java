package prova_gravità_canvas;

import java.awt.*;
import java.util.ArrayList;

import utils.Planet;

public class My_canvas extends Canvas{
	
	private ArrayList<Planet> listPl = new ArrayList<Planet>();
    
	public My_canvas(){
		super();
	}
	public My_canvas(ArrayList<Planet> listPl){
    	super();
    	this.listPl=listPl;
    }
	
	
	public void paint(Graphics g){
		g.setColor(Color.RED);
    	for(Planet pl:listPl){
    		
    		g.fillOval((int)(pl.getX()-pl.getR()),(int)(pl.getY()-pl.getR()),pl.getR()*2,pl.getR()*2);
    	}
	}
}
