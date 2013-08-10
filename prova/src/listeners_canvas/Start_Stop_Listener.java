package listeners_canvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import prova_gravità_canvas.*;
import javax.swing.*;

public class Start_Stop_Listener implements ActionListener{

	Thread_move th;
	
	public Start_Stop_Listener(Thread_move th){
		this.th = th;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText().equals("Start")){
			((JButton)e.getSource()).setText("Stop");
			th.startMove();
		}else{
			((JButton)e.getSource()).setText("Start");
			th.stopMove();
		}
		
	}

}
