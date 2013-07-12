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
		while(true){
			p.incY(10);
			f.repaint();
			if(p.checkCollisions()==3){
				message.showMessageDialog(f, "Collisione!!!!", "Collisione",message.WARNING_MESSAGE);
				break;
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
