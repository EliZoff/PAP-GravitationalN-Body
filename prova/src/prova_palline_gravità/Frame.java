package prova_palline_gravità;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{
	My_panel p;
	public Frame(){
		super();
		Container c=getContentPane();
		p = new My_panel();
		setSize(700, 700);
		c.add(p);       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public My_panel getP(){
		return p;
	}

	public static void main(String[] args) {
		Frame f=new Frame();
		Thread_move th=new Thread_move(f, f.getP());
		f.setVisible(true);
		th.start();
		
	}

}
