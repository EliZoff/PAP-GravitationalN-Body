package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import prova_palline_gravit�.*;
import javax.swing.*;

public class Start_Stop_Listener implements ActionListener{

	Thread_move th;
	JButton start;
	
	public Start_Stop_Listener(JButton start, Thread_move th){
		this.th = th;
		this.start = start;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start){
			th.startThread();
		}
		
	}

}
