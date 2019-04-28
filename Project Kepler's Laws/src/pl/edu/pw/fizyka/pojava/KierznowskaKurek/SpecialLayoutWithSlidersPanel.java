package pl.edu.pw.fizyka.pojava.KierznowskaKurek;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class SpecialLayoutWithSlidersPanel extends JPanel{
	static double eccentricityValue = 0;
	static double semimajorAxisValue = 0;
	static double semiminorAxisValue;
	String stringSemiminorAxisValue;
	
	public SpecialLayoutWithSlidersPanel() {
	

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
        
        this.setLayout(layout);

        JLabel orbitsParametersLabel = new JLabel("Parametry orbity:");
        orbitsParametersLabel.setHorizontalAlignment(JLabel.CENTER);
        orbitsParametersLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
    	gridBagConstraints.gridx = 0;//first column
        gridBagConstraints.gridy = 0;//first row
		gridBagConstraints.gridwidth = 2;//2 columns wide
		gridBagConstraints.gridheight = 2;//2 columns height
        gridBagConstraints.weightx = 1.0;//request any extra vertical space
        gridBagConstraints.weighty = 1;//request any extra horizontal space
        this.add(orbitsParametersLabel, gridBagConstraints);   
        
        
        JLabel semiminorAxisValueField = new JLabel("");//etykieta z wartoscia malej polosi, uzupelniona pozniej(w sliderach na biezaco)
        semiminorAxisValueField.setPreferredSize(new Dimension(100, 20));
        semiminorAxisValueField.setBackground(Color.white);
        semiminorAxisValueField.setOpaque(true);	
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2;
        this.add(semiminorAxisValueField, gridBagConstraints);
       
        JLabel eccentricityLabel = new JLabel("Wartoœæ mimoœrodu:");
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1;
        this.add(eccentricityLabel, gridBagConstraints);
 
        JLabel eccentricityValueField = new JLabel("");//wartosc mimosrodu pobierana na biezaco ze slidera
        eccentricityValueField.setPreferredSize(new Dimension(100, 20));
        eccentricityValueField.setBackground(Color.white);
        eccentricityValueField.setOpaque(true);
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        this.add(eccentricityValueField, gridBagConstraints);
      	
        JSlider eccentricityValueSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        eccentricityValueSlider.setMajorTickSpacing(10);
        eccentricityValueSlider.setMinorTickSpacing(1);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weighty = 0.3;		
        this.add(eccentricityValueSlider, gridBagConstraints);
        eccentricityValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				eccentricityValue = ((eccentricityValueSlider.getValue()));
				String stringEccentricityValue = String.valueOf(eccentricityValue);
				eccentricityValueField.setText(stringEccentricityValue);
				semiminorAxisValue =(double) Math.sqrt((semimajorAxisValue*semimajorAxisValue)-(eccentricityValue*eccentricityValue)); //obliczanie malej pólosi
				stringSemiminorAxisValue = String.valueOf(semiminorAxisValue);//przygotowanie do wyswietlenia jako string
				semiminorAxisValueField.setText(stringSemiminorAxisValue+"AU");
				repaint();
			}
		});

        JLabel semimajorAxisLabel = new JLabel("D³ugoœæ wielkiej pó³osi:");
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1;
        this.add(semimajorAxisLabel, gridBagConstraints);
        
        JLabel semimajorAxisValueField = new JLabel();//wartosc wielkiej polosi pobierana na biezaco ze slidera
        semimajorAxisValueField.setPreferredSize(new Dimension(100, 20));
        semimajorAxisValueField.setBackground(Color.white);
        semimajorAxisValueField.setOpaque(true);
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        this.add(semimajorAxisValueField, gridBagConstraints);
   	
        JSlider semimajorAxisValueSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        //1 AU = 1,495978707×10^11 m
        semimajorAxisValueSlider.setMajorTickSpacing(1);
        //semimajorAxisValueSlider.setMinorTickSpacing(1);
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weighty = 0.1;	
        semimajorAxisValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				semimajorAxisValue = (semimajorAxisValueSlider.getValue());
				String stringsemimajorAxisValue = String.valueOf(semimajorAxisValue);
				semimajorAxisValueField.setText(stringsemimajorAxisValue+"AU");
				semiminorAxisValue =(double) Math.sqrt((semimajorAxisValue*semimajorAxisValue)-(eccentricityValue*eccentricityValue)); //obliczanie malej pólosi
				stringSemiminorAxisValue = String.valueOf(semiminorAxisValue);//przygotowanie do wyswietlenia jako string
				semiminorAxisValueField.setText(stringSemiminorAxisValue+"AU");
				repaint();
				
				
			}
		});
        this.add(semimajorAxisValueSlider, gridBagConstraints);
          
        JLabel semiminorAxisLabel = new JLabel("D³ugoœæ ma³ej pó³osi:");
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2;
        this.add(semiminorAxisLabel, gridBagConstraints);

 
 
	}

	public static double giveEccentricity() {
		return eccentricityValue;
	}
	public static double giveSemimajorAxis() {
		return semimajorAxisValue;
	}
	public static double giveSemiminorAxis() {
		return semiminorAxisValue;
	}
	
}                             

                            
