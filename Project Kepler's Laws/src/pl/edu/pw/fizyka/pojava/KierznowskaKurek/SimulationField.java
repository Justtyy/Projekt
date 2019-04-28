package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SimulationField extends JPanel{

	Orbit orbit;
	double semimajorAxis;
	double semiminorAxis;
	double eccentricity;
	
	public SimulationField(double majorAxis,double minorAxis, double ecc) {
		semimajorAxis = majorAxis;
		semiminorAxis = minorAxis;
		eccentricity = ecc;
	
		orbit = new Orbit(semimajorAxis,semiminorAxis,eccentricity);
	}
	  public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        orbit.paint(g);
	        repaint();
	  }
	
	
}