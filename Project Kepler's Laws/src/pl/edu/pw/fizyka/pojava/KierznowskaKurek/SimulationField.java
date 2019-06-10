package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;

import javax.swing.JPanel;


public class SimulationField extends JPanel implements Runnable{

	Orbit orbit;
	double planetX, planetY;
	double fi;
	int intPlanetX, intPlanetY;
	Color planetColor;

 
	public SimulationField(Orbit o) {
		orbit = o;
	}
	  public void paintComponent(Graphics g) {
		  super.paintComponent(g); 
	        orbit.paint(g);
	        Color defaultPlanetColor = new Color(142, 10, 70);
	        g.setColor(defaultPlanetColor);
	        g.setColor(planetColor);
	        g.fillOval(intPlanetX, intPlanetY, 10, 10);
	       
	  }
	  
	  public void planetMove() {
		  planetX = ((orbit.semimajorAxis)*(Math.cos(fi))+(orbit.semiminorAxisEndX));
			intPlanetX = (int)planetX;
			
			planetY = ((orbit.semiminorAxis)*(Math.sin(fi))+(orbit.semimajorAxisStartX)-5);
			intPlanetY = (int)planetY;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			repaint(); 
	  }
	@Override
	public void run() {
		for(int ii = 0; ii < 1000; ii++) {
		
			for(fi = 0; fi < 1.046; fi+=0.1) {
				planetMove();
			}
		
			for(fi = 1.046; fi < 2.093; fi+=0.08) {
				planetMove();
			}
		
			for(fi = 2.093; fi < 3.139; fi+=0.06) {
				planetMove();
			}
		
			for(fi = 3.139; fi < 4.185; fi+=0.06) {
				planetMove();
			}
		
			for(fi = 4.185; fi < 5.23; fi+=0.08) {
				planetMove();
			}
		
			for(fi = 5.23; fi < 6.28; fi+=0.1) {
				planetMove();
			}
		
		}
	
	}
	public void clearPanel(){
	
		orbit = null;

	}
}