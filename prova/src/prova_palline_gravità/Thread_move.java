package prova_palline_gravità;

import java.awt.Rectangle;
import java.util.*;

import javax.swing.*;

public class Thread_move extends Thread{
	static final double G = 6.67E-11;
	private JFrame f;
	private My_panel p;
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	
	public Thread_move(JFrame f, My_panel p){
		this.p=p;
		this.f=f;
	}
	
	public double calcDist(double x1c, double y1c, double x2c, double y2c){
    	//calcola la distanza tra due punti attraverso il teorema di pitagora
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2) + Math.pow((y2c-y1c), 2));
    	return result;
    }
	public double calcDistX(double x1c, double x2c){
    	//calcola la distanza tra le coordinate x di due punti
    	double result;
    	result=Math.sqrt(Math.pow((x2c-x1c), 2));
    	return result;
    }
    public double calcDistY(double y1c, double y2c){
    	//calcola la distanza tra le coordinate y di due punti
    	double result;
    	result=Math.sqrt(Math.pow((y2c-y1c), 2));
    	return result;
    }
    
    /*public boolean checkCollisions() {
    	Rectangle b = this.getBounds();
        if(calcDist(this.x1, this.y1, this.x2, this.y2)<=(r1+r2)+1){
        	//+1 perchè essendo le coordinate int e la distanza double potrebbe rimanere uno spazio tra le figure che al max può essere 1
            return true;
        }
        else {
            return false;
        }
    }

    
    
    public void incP1(int xinc, int yinc){
    	//incrementa le coordinate della prima palla
    	double c, b, t;
    	double distR, b1, c1;
    	//se la distanza tra i centri delle 2 considerando l'incremento da fare è > della somma dei due raggi
    	if(calcDist(this.x1+ xinc, this.y1 + yinc, this.x2, this.y2)>(r1+r2)) {
    		//posso eseguire l'incremento senza sovrapposizioni tra le due
    		this.x1+=xinc;
    		this.y1+=yinc;
    	}else{
    		//altrimenti calcolo le componenti x e y della distanza rimanente in modo da far collidere le figure
    		c = calcDistX(this.x1, this.x2);
    		b = calcDistY(this.y1, this.y2);
    		t = Math.atan2(c, b);
    		distR=calcDist(this.x1, this.y1, this.x2, this.y2) - (r1+r2);
    		c1 = distR*Math.sin(t);
    		b1= distR*Math.cos(t);
    		this.x1+=c1;
    		this.y1+=b1;
    	}
    }*/

	public void genPlanets(int nPl, double[] m, int[] r){
		//genera l'insieme di nPl pianeti di massa m e raggio r
		Rectangle b = p.getBounds();
		Random rn = new Random(System.nanoTime());
		boolean flagOk=false, flagColl = false;
		for(int i=0; i<nPl; i++){
			flagOk=false;
			Planet pl = new Planet(0,0,m[i],r[i]);
			//genero casualmente le coordinare del centro della prima palla
	    	
	    	//se le coordinate genetate posizionano la palla oltre il bordo del panel o sopra un'altra le rigenero
	    	while(!flagOk)
	    	{
	    		pl.setX(rn.nextInt(b.width));
		    	pl.setY(rn.nextInt(b.height));
	    		//flag controllo collisioni
		    	flagColl = false;
	    		//controllo tutta la lista dei pianeti per verificare che le coordinate appena generate evitino collisioni
	    		for(Planet x:planets)
	    		{
	    			if((calcDist(pl.getX(), pl.getY(), x.getX(), x.getY())<=(pl.getR()+x.getR())+1))
	    			{
	    				flagColl=true;
	    			}
	    		}
	    		//controllo che il pianeta sia dentro lo schermo
	    		if((pl.getX() +pl.getR() < b.width && pl.getX()-pl.getR()>0)&&
	    			(pl.getY() +pl.getR() < b.height && pl.getY()-pl.getR()>0) && !flagColl)
	    		{
	    			flagOk=true;
	    		}
	    		
	    	}
	    	planets.add(pl);
		}
		
    	//f.repaint();
    	
    }
	
	public double calcFg(double m1, double m2, double dist){
		double f;
		f=(G*m1*m2)/(dist*dist);
		return f;
		
	}
	
	public double calcAcc(double f, double m){
		return f/m;
		
	}
	public double calcVel(double vi, double dt, double a){
		double v = vi + a*dt;
		return v;
	}
	
	public double calcdP(double vi, double dt, double a){
		double p = vi*dt + (0.5)*a*(dt*dt);
		return p;
	}
	
	public boolean checkCollisions(double x1, double x2, double y1, double y2, double r1, double r2) {
		double d=calcDist(x1, y1, x2, y2);
		double r = (r1+r2);
        if(d<=r/*+1*/){//????
            return true;
        }
        else {
            return false;
        }
    }
	
	public void run(){
		JOptionPane message=new JOptionPane();
		int dt=50; //delta time
		double a, ax, ay; // accelerazione
		double vi, vix=0, viy, vf, vfx, vfy; // velocità iniziale e finale
		double pi, pix, piy, dp, dpx, dpy; // posizione iniziale e spostamento
		int[] rg = {20,70,5,30,45,5,15,80,25,10}; //array di raggi
		double c, b, c1, b1; //cateti
		double t; //angolo acuto
		double[] ms = {5.97E10,6.39E11,20,50,35,6,80,20,50,35}; //array di masse
		double fx = 0, fy = 0, totFval=0;
		String dirFx = "", dirFy = "";
		My_panel newP;
		Force[] force; // array di forze
		int k=0;
		boolean collision=false; //flag collisioni
		String direction; //direzione della forza
		genPlanets(2, ms, rg); //generazione coordinate casuali dei pianeti
		//planets.add(new Planet(20,300,ms[0],rg[0]));
		//planets.add(new Planet(500,500,ms[1],rg[1]));
		newP = new My_panel(planets); //nuovo JPanel
		//rimuovo e aggiungo il panel per disegnare i nuovi pianeti 
		f.remove(p);
		f.add(newP);
		f.validate();
		f.repaint();
		//calcolo la forza iniziale tra tutti i pianeti
		for(Planet pl:planets){
			force = new Force[planets.size()-1];
			k=0;
			for(Planet plo:planets){
				if(!pl.equals(plo)){
					//tranne che per me stesso
					force[k] = new Force(calcFg(pl.getM(), plo.getM(), calcDist(pl.getX(), pl.getY(), plo.getX(), plo.getY())));
					force[k].findDirX(pl.getX(), plo.getX());
					force[k].findDirY(pl.getY(), plo.getY());
					
					//calcolo la risultante delle forze
					if(k == 0){
						//se è la prima forza calcolata
						b = calcDistX(pl.getX(),plo.getX());
			    		c = calcDistY(pl.getY(), plo.getY());
			    		t = Math.atan2(c, b);
			    		//trovo le componenti x e y
			    		force[k].setCompX(force[k].getValue()*Math.cos(t));
			    		force[k].setCompY(force[k].getValue()*Math.sin(t));
						fx = force[k].getCompX();
						fy = force[k].getCompY();
						//e indico come direzione della risultante quella della forza appena trovata
						dirFx = force[k].getDirX();
						dirFy = force[k].getDirY();
					}else{
						//se non è la prima forza
						b = calcDistX(pl.getX(),plo.getX());
			    		c = calcDistY(pl.getY(), plo.getY());
			    		t = Math.atan2(c, b);
			    		force[k].setCompX(force[k].getValue()*Math.cos(t));
			    		force[k].setCompY(force[k].getValue()*Math.sin(t));
			    		//calcolo la differenza tra le componenti x e y della risultante trovata fin'ora
			    		// e della forza appena trovata
			    		fx = fx - (force[k].getCompX());
			    		fy = fy - (force[k].getCompY());
			    		if(fx < 0){
			    			//se la differenza è negativa allora la nuova forza è prevalente
			    			//quindi la direzione sarà uguale alla forza attuale
			    			dirFx = force[k].getDirX();
			    			fx = -fx;
			    		}
			    		//altrimenti non cambia nulla
			    		if(fy < 0){
			    			dirFy = force[k].getDirY();
			    			fy = -fy;
			    		}
					}
				    k++;
				}
			}
			pl.setForces(force);
			//trovo il valore del modulo della risultante
			
			totFval = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy, 2));
			//e la imposto nell'oggetto
			pl.setTotalForce(new Force(totFval,fx,fy,dirFx,dirFy));
		}
		while(!collision){
			//trovo lo spostamento (2 PIANETI)
			for(Planet pl:planets){
				double tempb,tempc,tempt;
				//trovo la direzione della forza che dipende dalla posizione del pianeta rispetto all'altro
				direction = pl.getTotalForces().getDirX() + pl.getTotalForces().getDirY();
				/*direction = pl.getForces()[0].getDirX()+pl.getForces()[0].getDirY();*/
				//calcolo accelerazione velocità e posizione iniziale
				
				ax = calcAcc(pl.getTotalForces().getCompX(), pl.getM());
				ay = calcAcc(pl.getTotalForces().getCompY(), pl.getM());
				//a = calcAcc(pl.getTotalForces().getValue(), pl.getM());
				a = Math.sqrt(Math.pow(ax, 2)+Math.pow(ay, 2));
				vix = pl.getViX();
				viy = pl.getViY();
				vi = Math.sqrt(Math.pow(pl.getViX(), 2)+Math.pow(pl.getViY(), 2));
				vfx = calcVel(vix, (double)dt, ax);
				vfy = calcVel(viy, (double)dt, ay);
				//vf = calcVel(vi, (double)dt, ax);
				vf = Math.sqrt(Math.pow(vfx, 2)+Math.pow(vfy, 2));
				pix = calcDistX(0, pl.getX());
				piy = calcDistY(0, pl.getY());
				dp = calcdP(vi, dt, a);
				dpx = calcdP(vix, dt, ax); //spostamento
				dpy = calcdP(viy, dt, ay);
				/*c = calcDistX(0, pl.getX()); //cateto triangolo (0,0,x,y)
	    		b = calcDistY(0, pl.getY()); 
	    		t = Math.atan2(c, b); //angolo acuto*/
	    		//problema!!! calcolando la distanza tra 0 e x o y non ottengo i veri cateti e il vero angolo acuto
	    		//questi vanno calcolati considerando la differenza tra le x e y delle coppie di pianeti!!
	    		/*tempb = calcDistX(planets.get(0).getX(),planets.get(1).getX());
	    		tempc = calcDistY(planets.get(0).getY(), planets.get(1).getY());
	    		tempt = Math.atan2(tempc, tempb);
	    		c1 = dp*Math.sin(tempt); //nuovo cateto 
	    		b1= dp*Math.cos(tempt);*/
				switch(direction)
				{
				//a seconda della direzione della forza trovo la nuova posizione
					case "++":
			    		pl.incX(dpx);
			    		pl.incY(dpy);
					break;
					case "--":
						pl.incX(-dpx);
			    		pl.incY(-dpy);
					break;
					case "+-":
						pl.incX(dpx);
			    		pl.incY(-dpy);
					break;
					case "-+":
						pl.incX(-dpx);
			    		pl.incY(dpy);
					break;
				}
				pl.setViX(vfx);//aggiorno la velocità attuale
				pl.setViY(vfy);
			}
			
			for(Planet pl:planets){
				force = new Force[planets.size()-1];
				k=0;
				for(Planet plo:planets){
					if(!pl.equals(plo)){
						//tranne che per me stesso
						force[k] = new Force(calcFg(pl.getM(), plo.getM(), calcDist(pl.getX(), pl.getY(), plo.getX(), plo.getY())));
						force[k].findDirX(pl.getX(), plo.getX());
						force[k].findDirY(pl.getY(), plo.getY());
						//aggiorno le forze tra tutti i pianeti dopo il movimento (cambia r^2)
						
						//calcolo la risultante delle forze aggiornate
						if(k == 0){
							//se è la prima forza calcolata
							b = calcDistX(pl.getX(),plo.getX());
				    		c = calcDistY(pl.getY(), plo.getY());
				    		t = Math.atan2(c, b);
				    		//trovo le componenti x e y
				    		force[k].setCompX(force[k].getValue()*Math.cos(t));
				    		force[k].setCompY(force[k].getValue()*Math.sin(t));
							fx = force[k].getCompX();
							fy = force[k].getCompY();
							//e indico come direzione della risultante quella della forza appena trovata
							dirFx = force[k].getDirX();
							dirFy = force[k].getDirY();
						}else{
							//se non è la prima forza
							b = calcDistX(pl.getX(),plo.getX());
				    		c = calcDistY(pl.getY(), plo.getY());
				    		t = Math.atan2(c, b);
				    		force[k].setCompX(force[k].getValue()*Math.cos(t));
				    		force[k].setCompY(force[k].getValue()*Math.sin(t));
				    		//calcolo la differenza tra le componenti x e y della risultante trovata fin'ora
				    		// e della forza appena trovata
				    		fx = fx - (force[k].getCompX());
				    		fy = fy - (force[k].getCompY());
				    		if(fx < 0){
				    			//se la differenza è negativa allora la nuova forza è prevalente
				    			//quindi la direzione sarà uguale alla forza attuale
				    			dirFx = force[k].getDirX();
				    			fx = -fx;
				    		}
				    		//altrimenti non cambia nulla
				    		if(fy < 0){
				    			dirFy = force[k].getDirY();
				    			fy = -fy;
				    		}
						}
						collision = checkCollisions(pl.getX(), plo.getX(), pl.getY(), plo.getY(), pl.getR(), plo.getR());
					    k++;
					}
				}
				//trovo il valore del modulo della nuova risultante
				totFval = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy, 2));
				//e la imposto nell'oggetto
				pl.setTotalForce(new Force(totFval,fx,fy,dirFx,dirFy));
				pl.setForces(force);//aggiorno le forze con la nuova posizione
			}
			//f.validate();
			//System.out.println(calcDist(planets.get(0).getX(), planets.get(0).getY(), planets.get(1).getX(), planets.get(1).getY()));
			f.repaint();
			try {
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				
			}
		}
		
		System.out.println("break");
		/*System.out.println(p.calcDist());
		System.out.println(p.calcDistX());
		System.out.println(p.calcDistY());*/
		/*while(true){
			//p.incP1(incY, incX);
			//p.incP2(-incX, -incY);
			
			//p.setVisible(true);
			//f.repaint();
			if(p.checkCollisions()){
				JOptionPane.showMessageDialog(f, "Contatto");
				break;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}

}
