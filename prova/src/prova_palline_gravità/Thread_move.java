package prova_palline_gravità;

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
		int incY=10;
		int incX=10;
		/*System.out.println(p.calcDist());
		System.out.println(p.calcDistX());
		System.out.println(p.calcDistY());*/
		while(true){
			p.incP1(incY, incX);
			p.incP2(-incX, -incY);

			f.repaint();
			if(p.checkCollisions()){
				JOptionPane.showMessageDialog(f, "Contatto");
				break;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
