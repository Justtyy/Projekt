package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class SimulationField extends JPanel implements Runnable{

	Orbit orbit;
	double planetX, planetY;
	double fi;
	int intPlanetX, intPlanetY;
	
	public SimulationField(Orbit o) {
		orbit = o;
	}
	  public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        orbit.paint(g);
	        Color planetColor = new Color(142, 10, 70);
	        g.setColor(planetColor);
			g.fillOval(intPlanetX, intPlanetY, 10, 10);
	       
	  }
	@Override
	public void run() {
		for(fi = 0; fi < 1000; fi+=0.1) {
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
		
	}
	
	
}