package listeners_canvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import prova_gravit�_canvas.*;
import javax.swing.*;
import utils.*;

public class Regenerate_Listener  implements ActionListener{

	private Thread_move th;
	private JButton regenerate, start;
	private Utility util;
	
	
	public Regenerate_Listener(JButton regenerate, JButton start, Thread_move th){
		this.th = th;
		this.regenerate = regenerate;
		this.start = start;
		this.util = new Utility();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] r;
		double[] m;
		int nPlanets = 10;
		if(e.getSource()==regenerate){
			regenerate.setText("Re-Generate");
			start.setEnabled(true);
			r = util.genRadius(nPlanets);
			m = util.genMass(nPlanets);
			th.genPlanets(nPlanets, m, r);
		}
	}

}
