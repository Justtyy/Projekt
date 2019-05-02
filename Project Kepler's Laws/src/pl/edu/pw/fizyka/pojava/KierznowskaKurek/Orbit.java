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
	int showAxis, showOrbit;
	int semiminorAxisStartX;
	int semiminorAxisStartY;
	int semiminorAxisEndX;
	int semiminorAxisEndY;
	int semimajorAxisStartX;
	int semimajorAxisStartY;
	int semimajorAxisEndX;
	int semimajorAxisEndY;
	
	public Orbit(double majorAxis,double minorAxis, double ecc, int panelH, int panelW) {
		semimajorAxis = majorAxis;
		semiminorAxis = minorAxis;
		eccentricity = ecc;
		int intMinorAxis = (int)minorAxis;//potrzebne żeby użyć niżej do panelHeight
		int intMajorAxis = (int)majorAxis;//potrzebne żeby użyć niżej do panelWidth
		panelHeight = ((panelH/2)-(intMinorAxis));
		panelWidth = ((panelW/2)-(intMajorAxis));
		semimajorAxisStartX = panelWidth;
		semimajorAxisStartY = (panelH/2);
		semimajorAxisEndX = ((panelW/2)+(intMajorAxis));
		semimajorAxisEndY = (panelH/2);
		semiminorAxisStartX = (panelW/2);
		semiminorAxisStartY = ((panelH/2)+(intMinorAxis));
		semiminorAxisEndX = (panelW/2);
		semiminorAxisEndY = ((panelH/2)-(intMinorAxis));
		
	}
	
	public void ifShowAxis(int i) {
		showAxis = i;
	}

	public void ifShowOrbit(int i) {
		showOrbit = i;
	}
	
	public void paint(Graphics g) {
		
		if (showOrbit == 1) {
		g.drawOval(panelWidth, panelHeight,(int)(2*semimajorAxis), (int)(2*semiminorAxis));
		}
		if (showAxis == 1) {
			g.drawLine(semimajorAxisStartX, semimajorAxisStartY, semimajorAxisEndX, semimajorAxisEndY);
			g.drawLine(semiminorAxisStartX, semiminorAxisStartY, semiminorAxisEndX, semiminorAxisEndY);
		}
			
	}
}