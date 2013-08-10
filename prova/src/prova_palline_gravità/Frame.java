package prova_palline_gravità;

import java.awt.*;
import javax.swing.*;
import listeners.*;


public class Frame extends JFrame{
	Thread_move th;
	public Frame(){
		super();
		Box total, drawer, buttons;
		JButton regenerate;
		JButton start;
		JPanel panel;
		My_panel p;
		
		setSize(1130, 730);
		Container c=getContentPane();
		
		th=new Thread_move();
		
		panel = new JPanel();
		
		total=new Box(BoxLayout.X_AXIS);
		drawer=new Box(BoxLayout.Y_AXIS);
		buttons=new Box(BoxLayout.Y_AXIS);
		
		start = new JButton("Start");
		regenerate = new JButton("Generate");
		
		p = new My_panel();
		//drawer.setSize((1130-150),(730-39));
		drawer.add(p);
		p.setSize((this.getWidth()-150),(this.getHeight()-39));
		
		Start_Stop_Listener SSL=new Start_Stop_Listener(start,th);
		Regenerate_Listener RL = new Regenerate_Listener(regenerate, start, th);
		start.addActionListener(SSL);
		regenerate.addActionListener(RL);
		buttons.add(start);
		start.setEnabled(false);
		//buttons.add(Box.createVerticalStrut(50));
		buttons.add(regenerate);
		
		total.add(drawer);
		total.add(buttons);
		total.add(Box.createHorizontalStrut(50));
		
		//panel.add(drawer);
		
		c.add(total);
		
		th.setDrawer(p);
		th.setGraphicComponents(this, /*panel,*/ drawer);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void startThread(){
		th.start();
	}

}
