package prova_canvas;

import java.awt.*;

import javax.swing.*;

import prova_movimento_pallina.My_panel;

public class Frame extends JFrame{
	
	private My_canvas canv;
	public Frame(){
		super();
		setSize(300, 300);
		Container c=getContentPane();
		JPanel p = new JPanel();
		Box b = new Box(BoxLayout.X_AXIS);
		canv = new My_canvas();
		b.add(canv);
		canv.setSize(this.getWidth()-39,this.getHeight()-39);
		p.add(b);
		c.add(p);       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public My_canvas getCanvas(){
		return canv;
	}

}
