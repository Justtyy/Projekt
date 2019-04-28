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
	
	public SimulationField(Orbit o) {
		//semimajorAxis = majorAxis;
		//semiminorAxis = minorAxis;
		//eccentricity = ecc;
	
		orbit = o;
	}
	  public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        orbit.paint(g);
	       
	  }
	
	
}