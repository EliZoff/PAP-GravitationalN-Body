package prova_movimento_pallina;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame{
	My_panel p;
	public Frame(){
		super();
		Container c=getContentPane();
		Box b = new Box(BoxLayout.X_AXIS);
		p = new My_panel();
		b.add(p);
		setSize(300, 300);
		c.add(b);       
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
