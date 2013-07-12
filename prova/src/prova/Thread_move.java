package prova;

import javax.swing.*;

public class Thread_move extends Thread{
	
	JFrame f;
	My_panel p;
	
	public Thread_move(JFrame f, My_panel p){
		this.p=p;
		this.f=f;
	}
	
	public void run(){
		JOptionPane message=new JOptionPane();
		int incY=30;
		int incX=20;
		while(true){
			p.incY(incY);
			p.incX(incX);
			f.repaint();
			if(p.checkCollisionsY()){
				incY=incY*(-1);
			}
			if(p.checkCollisionsX()){
				incX=incX*(-1);
			}
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
