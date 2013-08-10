package prova_canvas;

import javax.swing.*;

public class Thread_move extends Thread{
	
	JFrame f;
	My_canvas canv;
	
	public Thread_move(JFrame f, My_canvas canv){
		this.canv=canv;
		this.f=f;
	}
	
	public void run(){
		JOptionPane message=new JOptionPane();
		int incY=30;
		int incX=20;
		while(true){
			canv.incY(incY);
			canv.incX(incX);
			canv.repaint();
			//f.repaint();
			if(canv.checkCollisionsY()){
				incY=incY*(-1);
			}
			if(canv.checkCollisionsX()){
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
