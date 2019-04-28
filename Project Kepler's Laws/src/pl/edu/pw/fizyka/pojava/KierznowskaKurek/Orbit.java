package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Orbit {
	double semimajorAxis;
	double semiminorAxis;
	double eccentricity;
	int panelHeight;
	int panelWidth;
	
	public Orbit(double majorAxis,double minorAxis, double ecc, int panelH, int panelW) {
		semimajorAxis = majorAxis;
		semiminorAxis = minorAxis;
		eccentricity = ecc;
		int intMinorAxis = (int)minorAxis;//potrzebne żeby użyć niżej do panelHeight
		int intMajorAxis = (int)majorAxis;//potrzebne żeby użyć niżej do panelWidth
		panelHeight = ((panelH/2)-(intMinorAxis/2));
		panelWidth = ((panelW/2)-(intMajorAxis/2));
		
		
	}

	public void paint(Graphics g) {
		
		g.drawOval(panelWidth, panelHeight, (int)semimajorAxis, (int)semiminorAxis);
		
	}	
}