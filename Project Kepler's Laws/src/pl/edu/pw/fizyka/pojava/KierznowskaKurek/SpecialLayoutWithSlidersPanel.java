package pl.edu.pw.fizyka.pojava.KierznowskaKurek;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SpecialLayoutWithSlidersPanel extends JPanel{
	static double eccentricityValue = 0;
	static double semimajorAxisValue = 0.1;
	static double semiminorAxisValue= 0.1;
	
	
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
        
        
        JLabel semiminorAxisValueField = new JLabel("0.1AU");//etykieta z wartoscia malej polosi, uzupelniona pozniej(w sliderach na biezaco)
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
 
        JLabel eccentricityValueField = new JLabel("0.0");//wartosc mimosrodu pobierana na biezaco ze slidera
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
      	
        JSlider eccentricityValueSlider = new JSlider(JSlider.HORIZONTAL, 0, 999, 0);
        eccentricityValueSlider.setMajorTickSpacing(10);
        eccentricityValueSlider.setMinorTickSpacing(1);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weighty = 0.3;		
        this.add(eccentricityValueSlider, gridBagConstraints);
        //akcja dla slidera dla mimosrodu (powinno byc od 0 do 0.999)
        eccentricityValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				eccentricityValue = eccentricityValueSlider.getValue();
				//double AuxiliaryEccentricityValue = (eccentricityValueSlider.getValue());//pomocnicza wartosc, dzieki ktorej jest lepsza dokladnosc slidera 
//				String stringEccentricityValue = String.valueOf(AuxiliaryEccentricityValue/1000);
//				eccentricityValueField.setText(stringEccentricityValue);
//				semiminorAxisValue = Math.sqrt(1-((eccentricityValue)*(eccentricityValue)))*(semimajorAxisValue); //obliczanie malej pólosi
//				NumberFormat formatter = new DecimalFormat("#0.000");//format dla double do trzech miejsc po przecinku
//				String stringSemiminorAxisValue = formatter.format(semiminorAxisValue);
//				semiminorAxisValueField.setText(eccentricityValue+"AU");
//				//semiminorAxisValueField.setText(stringSemiminorAxisValue+"AU");
//				repaint();
				NumberFormat formatter = new DecimalFormat("#0.000");//format dla double do trzech miejsc po przecinku
				String stringEccentricityValue = formatter.format(eccentricityValue/1000);
				eccentricityValueField.setText(stringEccentricityValue);
				semiminorAxisValue = Math.sqrt(1-((eccentricityValue/1000)*(eccentricityValue/1000)))*(semimajorAxisValue/1000); //obliczanie malej pólosi
				String stringSemiminorAxisValue = formatter.format(semiminorAxisValue);
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
        
        JLabel semimajorAxisValueField = new JLabel("0.1AU");//wartosc wielkiej polosi pobierana na biezaco ze slidera
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
   	
        JSlider semimajorAxisValueSlider = new JSlider(JSlider.HORIZONTAL, 100, 100000, 100);
        //1 AU = 1,495978707×10^11 m
        //semimajorAxisValueSlider.setMajorTickSpacing(1);
        //semimajorAxisValueSlider.setMinorTickSpacing(1);
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 1;
        gridBagConstraints.weighty = 0.1;	
        //Akcja dla slidera dla wielkiej polosi (powinno byc od 0,1 do 100AU)
        semimajorAxisValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				semimajorAxisValue =semimajorAxisValueSlider.getValue();
				//double AuxiliarySemimajorAxisValue = semimajorAxisValueSlider.getValue();//pomocnicza wartosc, dzieki ktorej jest lepsza dokladnosc slidera 
				NumberFormat formatter = new DecimalFormat("#0.000");//format dla double do trzech miejsc po przecinku
				String stringSemimajorValue = formatter.format(semimajorAxisValue/1000);
				semimajorAxisValueField.setText(stringSemimajorValue+"AU");
				semiminorAxisValue = Math.sqrt(1-((eccentricityValue/1000)*(eccentricityValue/1000)))*(semimajorAxisValue/1000); //obliczanie malej pólosi
				String stringSemiminorAxisValue = formatter.format(semiminorAxisValue);
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
		return eccentricityValue/1000;
	}
	public static double giveSemimajorAxis() {
		return semimajorAxisValue/1000;
	}
	public static double giveSemiminorAxis() {
		return semiminorAxisValue;
	}
	
}                             

                            
