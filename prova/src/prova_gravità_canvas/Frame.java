package prova_gravità_canvas;

import java.awt.*;

import javax.swing.*;

import listeners_canvas.*;


public class Frame extends JFrame{
	private Thread_move th;
	
	public Frame(){
		super();
		Box boxTotal, boxDrawer, boxButtons;
		JButton regenerate;
		JButton start;
		JPanel panel;
		Canvas drawer;
		
		setSize(1130, 730);
		
		Container c=getContentPane();
		
		panel = new JPanel();
		boxTotal=new Box(BoxLayout.X_AXIS);
		
		boxDrawer=new Box(BoxLayout.Y_AXIS);
		drawer = new My_canvas();
		
		boxButtons=new Box(BoxLayout.Y_AXIS);
		start = new JButton("Start");
		regenerate = new JButton("Generate");
		
		//aggiungo i componenti alla GUI
		boxDrawer.add(drawer);
		drawer.setSize((this.getWidth()-150),(this.getHeight()-39));
		
		boxButtons.add(start);
		start.setEnabled(false);
		//buttons.add(Box.createVerticalStrut(50));
		boxButtons.add(regenerate);
		
		boxTotal.add(boxDrawer);
		boxTotal.add(boxButtons);
		boxTotal.add(Box.createHorizontalStrut(50));
		
		panel.add(boxTotal);
		
		c.add(panel);
		
		//inizializzo il thread che controlla i movimenti dei pianeti
		th=new Thread_move();
		
		th.setDrawer(drawer);
		th.setGraphicComponents(this, /*panel,*/ boxDrawer);
		
		//aggancio i listeners ai bottoni
		Start_Stop_Listener SSL=new Start_Stop_Listener(th);
		Regenerate_Listener RL = new Regenerate_Listener(regenerate, start, th);
		start.addActionListener(SSL);
		regenerate.addActionListener(RL);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void startThread(){
		th.start();
	}

}
