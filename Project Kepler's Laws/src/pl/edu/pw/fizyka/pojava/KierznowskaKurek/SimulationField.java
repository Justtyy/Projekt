package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class SimulationField extends JPanel{

	Orbit orbit;
	double semimajorAxis;
	double semiminorAxis;
	double eccentricity;
	
	public SimulationField(Orbit o) {
		orbit = o;
	}
	  public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        orbit.paint(g);
	       
	  }
	
	
}