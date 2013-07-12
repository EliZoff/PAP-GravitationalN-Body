package prova;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{
	My_panel p;
	public Frame(){
		super();
		Container c=getContentPane();
		p = new My_panel();
		setSize(300, 300);
		c.add(p);       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public My_panel getP(){
		return p;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame f=new Frame();
		f.getP().setFrame(f);
		Thread_move th=new Thread_move(f, f.getP());
		f.setVisible(true);
		th.start();
		
	}

}
