package pl.edu.pw.fizyka.pojava.KierznowskaKurek;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//Anna Kierznowska
public class Orbit {
	double semimajorAxis;
	double semiminorAxis;
	double eccentricity;
	int panelHeight;
	int panelWidth;
	int showAxis, showOrbit;
	int semiminorAxisStartX, semiminorAxisStartY, semiminorAxisEndX, semiminorAxisEndY;
	int semimajorAxisStartX, semimajorAxisStartY, semimajorAxisEndX, semimajorAxisEndY;
	int sunPositionX, sunPositionY;
	int focusDistanceFromTheSun;
	Color axisColor, orbitColor;
	
	public Orbit(double majorAxis,double minorAxis, double ecc, int panelH, int panelW) {
		semimajorAxis = majorAxis;
		semiminorAxis = minorAxis;
		eccentricity = ecc;
		int intEccentricity = (int)eccentricity;
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
		focusDistanceFromTheSun =(int) (Math.sqrt((intMajorAxis*intMajorAxis)-(intMinorAxis*intMinorAxis)));
		sunPositionX = ((panelW/2)-focusDistanceFromTheSun-15);
		sunPositionY = ((panelH/2)-15);
	}

	public void ifShowAxis(int i) {
		showAxis = i;
	}
	public void ifShowOrbit(int i) {
		showOrbit = i;
	}
	
	public void paint(Graphics g) {
		
		Color sunColor = new Color(255, 207, 0);
		g.setColor(sunColor);
		g.fillOval(sunPositionX, sunPositionY, 30, 30);
		
		g.setColor(Color.gray);
		
		if (showOrbit == 1) {
		g.drawOval(panelWidth, panelHeight,(int)(2*semimajorAxis), (int)(2*semiminorAxis));
		g.setColor(orbitColor);
		}
		if (showAxis == 1) {
			g.drawLine(semimajorAxisStartX, semimajorAxisStartY, semimajorAxisEndX, semimajorAxisEndY);
			g.drawLine(semiminorAxisStartX, semiminorAxisStartY, semiminorAxisEndX, semiminorAxisEndY);
			g.setColor(axisColor);
		}	
	}
}