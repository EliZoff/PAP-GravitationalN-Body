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
		int incY=10;
		int incX=10;
		int dt=50; //delta time
		double a; // accelerazione
		double vi=0, vf; // velocità iniziale e finale
		double pi, dp; // posizione iniziale e spostamento
		int[] rg = {20,70,5,30,45,5,15,80,25,10}; //array di raggi
		double c, b, c1, b1, t; //cateti e angolo acuto
		double[] ms = {5.97E10,6.39E11,20,50,35,6,80,20,50,35}; //array di masse
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
				    k++;
				}
			}
			pl.setForces(force);
		}
		while(!collision){
			//trovo lo spostamento (2 PIANETI)
			for(Planet pl:planets){
				double tempb,tempc,tempt;
				//trovo la direzione della forza che dipende dalla posizione del pianeta rispetto all'altro
				direction = pl.getForces()[0].getDirX()+pl.getForces()[0].getDirY();
				//calcolo accelerazione velocità e posizione iniziale
				a = calcAcc(pl.getForces()[0].getValue(), pl.getM());
				vi = pl.getVi();
				vf = calcVel(vi,(double)dt,a);
				pi = calcDist(0, 0, pl.getX(), pl.getY());
				dp = calcdP(vi, dt, a); //spostamento
				c = calcDistX(0, pl.getX()); //cateto triangolo (0,0,x,y)
	    		b = calcDistY(0, pl.getY()); 
	    		t = Math.atan2(c, b); //angolo acuto
	    		//problema!!! calcolando la distanza tra 0 e x o y non ottengo i veri cateti e il vero angolo acuto
	    		//questi vanno calcolati considerando la differenza tra le x e y delle coppie di pianeti!!
	    		tempb = calcDistX(planets.get(0).getX(),planets.get(1).getX());
	    		tempc = calcDistY(planets.get(0).getY(), planets.get(1).getY());
	    		tempt = Math.atan2(tempc, tempb);
	    		c1 = dp*Math.sin(tempt); //nuovo cateto 
	    		b1= dp*Math.cos(tempt);
				switch(direction)
				{
				//a seconda della direzione della forza trovo la nuova posizione
					case "++":
			    		pl.incX(b1);
			    		pl.incY(c1);
					break;
					case "--":
						pl.incX(-b1);
			    		pl.incY(-c1);
					break;
					case "+-":
						pl.incX(b1);
			    		pl.incY(-c1);
					break;
					case "-+":
						pl.incX(-b1);
			    		pl.incY(c1);
					break;
				}
				pl.setVi(vf);//aggiorno la velocità attuale
				
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
						collision = checkCollisions(pl.getX(), plo.getX(), pl.getY(), plo.getY(), pl.getR(), plo.getR());
					    k++;
					}
				}
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
