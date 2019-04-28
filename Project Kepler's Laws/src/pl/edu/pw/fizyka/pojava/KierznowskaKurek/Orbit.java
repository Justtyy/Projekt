package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Orbit {
	double semimajorAxis;
	double semiminorAxis;
	double eccentricity;
	
	public Orbit(double majorAxis,double minorAxis, double ecc) {
		semimajorAxis = majorAxis;
		semiminorAxis = minorAxis;
		eccentricity = ecc;
		
	}

	public void paint(Graphics g) {
		g.drawOval(100, 100, (int)semimajorAxis, (int)semiminorAxis);
		
	}	
}