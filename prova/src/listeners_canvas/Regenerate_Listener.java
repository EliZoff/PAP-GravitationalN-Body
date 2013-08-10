package listeners_canvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import prova_gravità_canvas.*;

import javax.swing.*;

public class Regenerate_Listener  implements ActionListener{

	Thread_move th;
	JButton regenerate, start;
	
	public Regenerate_Listener(JButton regenerate, JButton start, Thread_move th){
		this.th = th;
		this.regenerate = regenerate;
		this.start = start;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] r;
		double[] m;
		if(e.getSource()==regenerate){
			regenerate.setText("Re-Generate");
			start.setEnabled(true);
			r = th.genRaggio(3);
			m = th.genMass(3);
			th.genPlanets(3, m, r);
		}
	}

}
