package pl.edu.pw.fizyka.pojava.KierznowskaKurek;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.Border;




public class MainFrame extends JFrame {
	
	
	
	Orbit orbit;
	int panelHeight, panelWidth;
	int showAxisValue, showOrbitValue;
	JPanel leftPanel, settingsPanel, topPanel, simulationActionPanel; //main panels
	JPanel settingsCenterPanel;
	JPanel choosePlanetPanel, distanceToSunPanel, animationsActionsPanel, checkBoxPanel, colorListPanel;
	SpecialLayoutWithSlidersPanel orbitsParametersPanel;
	JPanel distanceLabelPanel, maxDistancePanel, minDistancePanel;
	final JSplitPane splitPane;
	JButton savePlanet, startStopButton;
	JLabel language, distanceLabel, minDistanceLabel, maxDistanceLabel;//labels
	JRadioButton polish, english;
	String[] planetStrings = {"Wybierz planetę", "Merkury","Wenus", "Ziemia", "Mars", "Jowisz", "Saturn", "Uran", "Neptun"};
	String[] colorStrings = {"Wybierz motyw symulacji", "Dzień", "Noc"};
	JComboBox planetList, colorList;
	JButton okPlanetButton, okColorButton;
	JCheckBox showOrbit, showAxis;
	JLabel minDistanceToSun, maxDistanceToSun;//values
	Border blackLine, grayLine;
	SimulationField simulationField;
	int counter=0;
	boolean click;
	
	 long time_start, time_stop;
	
	Connection conn = null;
	
	public MainFrame() throws HeadlessException {
		
		//main frame settings
		super("Prawa Keplera");
		this.setSize(1000,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.addKeyListener(Escape);
		//border lines
		blackLine = BorderFactory.createLineBorder(Color.black);
		grayLine = BorderFactory.createLineBorder(Color.gray, 3);
		
		leftPanel = new JPanel();
		topPanel= new JPanel();
		settingsPanel = new JPanel();
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    splitPane.setLeftComponent(leftPanel);
	    splitPane.setRightComponent(settingsPanel);
	    this.add(splitPane, BorderLayout.CENTER);
		
		leftPanel.setLayout(new BorderLayout());
		
		leftPanel.add(topPanel, BorderLayout.PAGE_START);
		topPanel.setBorder(blackLine);
		topPanel.setLayout(new FlowLayout());		
		topPanel.add(savePlanet = new JButton("Zapisz własną planetę"));
		savePlanet.addActionListener(SaveFileListener);
		topPanel.add(language = new JLabel("Wybór języka:"));
		topPanel.add(polish = new JRadioButton("polski"));
		topPanel.add(english = new JRadioButton("angielski"));
		ButtonGroup group = new ButtonGroup();
		group.add(polish);
		group.add(english);
		leftPanel.add(simulationActionPanel = new JPanel());
		simulationActionPanel.setBorder(grayLine);
		simulationActionPanel.setLayout(new BorderLayout());
		
		
		
		settingsPanel.setLayout(new BorderLayout());
		settingsPanel.add(choosePlanetPanel = new JPanel(), BorderLayout.PAGE_START);
		settingsPanel.add(settingsCenterPanel = new JPanel(), BorderLayout.CENTER);
		settingsPanel.add(animationsActionsPanel = new JPanel(), BorderLayout.PAGE_END);
		
		//Choose planet from list
		choosePlanetPanel.setPreferredSize(new Dimension(100, 50));
		planetList = new JComboBox(planetStrings);
		planetList.setSelectedIndex(0);
		choosePlanetPanel.add(planetList);
		choosePlanetPanel.add(okPlanetButton = new JButton("OK"));
		okPlanetButton.setToolTipText("Potwierdz wybór planety");
		
		settingsCenterPanel.setLayout(new GridLayout(2,1));
		//SpecialLayoutWithSlidersPanel - contains orbit's parameters, like semiminor and semimajor axis and eccentricity
		settingsCenterPanel.add(orbitsParametersPanel = new SpecialLayoutWithSlidersPanel());
		//eccentricity = SpecialLayoutWithSlidersPanel.giveEccentricity();
		orbitsParametersPanel.setPreferredSize(new Dimension(100, 120));
		
		//Settings about maximum and minimum distance from the Sun
		settingsCenterPanel.add(distanceToSunPanel = new JPanel());
		distanceToSunPanel.setLayout(new GridLayout(3,2));
		distanceToSunPanel.add(distanceLabelPanel = new JPanel());
		distanceToSunPanel.add(maxDistancePanel = new JPanel());
		distanceToSunPanel.add(minDistancePanel = new JPanel());
		
		distanceLabelPanel.add(distanceLabel = new JLabel("Odległość od Słońca:"));
		distanceLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		maxDistancePanel.add(maxDistanceLabel = new JLabel("Maksymalna:"));
		maxDistancePanel.add(maxDistanceToSun = new JLabel());
		maxDistanceToSun.setPreferredSize(new Dimension(100, 20));
		maxDistanceToSun.setBackground(Color.white);
		maxDistanceToSun.setOpaque(true);
		minDistancePanel.add(minDistanceLabel = new JLabel("Minimalna:"));
		minDistancePanel.add(minDistanceToSun = new JLabel());
		minDistanceToSun.setPreferredSize(new Dimension(100, 20));
		minDistanceToSun.setBackground(Color.white);
		minDistanceToSun.setOpaque(true);
		
		//Radio buttons about which elements user want to see
		animationsActionsPanel.setLayout(new GridLayout(3,1));
		animationsActionsPanel.setPreferredSize(new Dimension(100, 160));
		animationsActionsPanel.add(checkBoxPanel = new JPanel());
		checkBoxPanel.add(showOrbit = new JCheckBox("Wyświetl orbitę"));
		checkBoxPanel.add(showAxis = new JCheckBox("Wyświetl osie elipsy"));
		showAxis.addActionListener(showAxisListener);
		showOrbit.addActionListener(showOrbitListener);
		
		
		//List with colors and motives 
		colorList = new JComboBox(colorStrings);
		colorList.setSelectedIndex(0);
		animationsActionsPanel.add(colorListPanel = new JPanel());
		colorListPanel.add(colorList); 
		colorListPanel.add(okColorButton = new JButton("OK"));
		okColorButton.setToolTipText("Potwierdz wybór motywu animacji");
		okColorButton.addActionListener(chooseMotive);
		okPlanetButton.addActionListener(OpenPlanetBase);
		
		animationsActionsPanel.add(startStopButton = new JButton("START/STOP"));
		
	    startStopButton.addActionListener(distanceToSunListener);
	    startStopButton.addMouseListener(mouseClickCounterListener);
	} 
	
		MouseListener mouseClickCounterListener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				counter++;
				SimulationtThread simulationThread = new SimulationtThread(); 
				if(counter==1) {
					click=true;//jesli nieparzysta, to na przycisku "stop" i symulacja powinna dzialac
					simulationThread.start();
				}
				else if(counter>1 && counter%2==0) {//jesli parzysta to na przycisku musi byc "start" i symulacja powinna pauzowac
					click=false;
					try {
						simulationThread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					startStopButton.setText("Start");
					repaint();
				}
				else if(counter>1 && counter%2!=0) {//jesli parzysta to na przycisku musi byc "start" i symulacja powinna pauzowac
					click=true;//jesli nieparzysta, to na przycisku "stop" i symulacja powinna dzialac
					simulationThread.start();
				}
			}
			
		};
		class SimulationtThread extends Thread{
			public void run() {
				if(click==true) {
					panelHeight = simulationActionPanel.getHeight();//potrzebne żeby rysowało się na środku panelu
					panelWidth = simulationActionPanel.getWidth();//to też
					orbit = new Orbit(SpecialLayoutWithSlidersPanel.giveSemimajorAxis(), SpecialLayoutWithSlidersPanel.giveSemiminorAxis(), SpecialLayoutWithSlidersPanel.giveEccentricity(), panelHeight, panelWidth);
					simulationField = new SimulationField(orbit);//tu jest komponent do rysowania
					simulationActionPanel.add(simulationField, BorderLayout.CENTER);
					ExecutorService exec = Executors.newFixedThreadPool(1);//to sprawia że planeta się porusza
					exec.execute(simulationField);
					exec.shutdown();
					startStopButton.setText("Stop");
					repaint();
					}			
				}
			}
//	ActionListener startStopButtonActionListener = new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			panelHeight = simulationActionPanel.getHeight();//potrzebne żeby rysowało się na środku panelu
//			panelWidth = simulationActionPanel.getWidth();//to też
//			orbit = new Orbit(SpecialLayoutWithSlidersPanel.giveSemimajorAxis(), SpecialLayoutWithSlidersPanel.giveSemiminorAxis(), SpecialLayoutWithSlidersPanel.giveEccentricity(), panelHeight, panelWidth);
//			simulationField = new SimulationsField(orbit);//tu jest komponent do rysowania
//			simulationActionPanel.add(simulationField, BorderLayout.CENTER);
//			ExecutorService exec = Executors.newFixedThreadPool(1);//to sprawia że planeta się porusza
//			exec.execute(simulationField);
//			exec.shutdown();
//
//			repaint();
//		}
//	};
	

	//actionlistener ustawiajacy odleglosci planety od Słońca, dodany do startStopButton
	ActionListener distanceToSunListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			NumberFormat formatter = new DecimalFormat("#0.000");//format dla double do trzech miejsc po przecinku
			
			double maxDistanceToSunValue = SpecialLayoutWithSlidersPanel.giveSemimajorAxis()*(1+SpecialLayoutWithSlidersPanel.giveEccentricity());
			String stringMaxDistanceToSun = formatter.format(maxDistanceToSunValue);
			maxDistanceToSun.setText(stringMaxDistanceToSun+"AU");
			
			double minDistanceToSunValue = SpecialLayoutWithSlidersPanel.giveSemimajorAxis()*(1-SpecialLayoutWithSlidersPanel.giveEccentricity());
			String stringMinDistanceToSun = formatter.format(minDistanceToSunValue);
			minDistanceToSun.setText(stringMinDistanceToSun+"AU");
		
		}
	};
	
	ActionListener showAxisListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean selection = showAxis.isSelected();
			if(selection == true) {
				showAxisValue = 1;
			}
			else if(selection == false){
				showAxisValue = 0;
			}
			orbit.ifShowAxis(showAxisValue);
			repaint();
			
			
		}
	};
	
	ActionListener showOrbitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean selection = showOrbit.isSelected();
			if(selection == true) {
				showOrbitValue = 1;
			}
			else if(selection == false){
				showOrbitValue = 0;
			}
			orbit.ifShowOrbit(showOrbitValue);
			repaint();
		}
	};

	ActionListener chooseMotive = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int motive = colorList.getSelectedIndex();
			if(motive==1) {
				topPanel.setBackground(Color.white);
				simulationActionPanel.setBackground(Color.white);
				distanceToSunPanel.setBackground(Color.white);
				choosePlanetPanel.setBackground(Color.white);
				checkBoxPanel.setBackground(Color.white);
				colorListPanel.setBackground(Color.white);
				distanceLabelPanel.setBackground(Color.white);
				maxDistancePanel.setBackground(Color.white);
				minDistancePanel.setBackground(Color.white);
				orbitsParametersPanel.setBackground(Color.white);
				simulationField.setBackground(Color.white);

				Color dayMotive = new Color(14,41,75);
				savePlanet.setBackground(Color.white);
				savePlanet.setForeground(dayMotive);
				language.setForeground(dayMotive);
				startStopButton.setBackground(Color.white);
				startStopButton.setForeground(dayMotive);
				polish.setBackground(Color.white);
				polish.setForeground(dayMotive);
				english.setBackground(Color.white);
				english.setForeground(dayMotive);
				planetList.setBackground(Color.white);
				planetList.setForeground(dayMotive);
				colorList.setBackground(Color.white);
				colorList.setForeground(dayMotive);
				okColorButton.setBackground(Color.white);
				okColorButton.setForeground(dayMotive);
				okPlanetButton.setBackground(Color.white);
				okPlanetButton.setForeground(dayMotive);
				showAxis.setBackground(Color.white);
				showAxis.setForeground(dayMotive);
				showOrbit.setBackground(Color.white);
				showOrbit.setForeground(dayMotive);
				orbitsParametersPanel.orbitsParametersLabel.setForeground(dayMotive);
				orbitsParametersPanel.semimajorAxisValueField.setBackground(Color.white);
				orbitsParametersPanel.semimajorAxisValueField.setForeground(dayMotive);
				orbitsParametersPanel.semimajorAxisLabel.setForeground(dayMotive);
				orbitsParametersPanel.semimajorAxisValueSlider.setBackground(Color.white);
				orbitsParametersPanel.semimajorAxisValueSlider.setForeground(dayMotive);
				orbitsParametersPanel.semiminorAxisValueField.setBackground(Color.white);
				orbitsParametersPanel.semiminorAxisValueField.setForeground(dayMotive);
				orbitsParametersPanel.semiminorAxisLabel.setForeground(dayMotive);
				orbitsParametersPanel.eccentricityValueField.setBackground(Color.white);
				orbitsParametersPanel.eccentricityValueField.setForeground(dayMotive);
				orbitsParametersPanel.eccentricityLabel.setForeground(dayMotive);
				orbitsParametersPanel.eccentricityValueSlider.setBackground(Color.white);
				orbitsParametersPanel.eccentricityValueSlider.setForeground(dayMotive);
				distanceLabel.setForeground(dayMotive);
				maxDistanceToSun.setBackground(Color.white);
				maxDistanceToSun.setForeground(dayMotive);
				maxDistanceLabel.setForeground(dayMotive);
				minDistanceToSun.setBackground(Color.white);
				minDistanceToSun.setForeground(dayMotive);
				minDistanceLabel.setForeground(dayMotive);
				orbit.axisColor = dayMotive;
				orbit.orbitColor = dayMotive;
				simulationField.planetColor = new Color(142, 10, 70);
				
			}
			if(motive==2) {
				topPanel.setBackground(Color.black);
				simulationActionPanel.setBackground(Color.black);
				distanceToSunPanel.setBackground(Color.black);
				choosePlanetPanel.setBackground(Color.black);
				checkBoxPanel.setBackground(Color.black);
				colorListPanel.setBackground(Color.black);
				distanceLabelPanel.setBackground(Color.black);
				maxDistancePanel.setBackground(Color.black);
				minDistancePanel.setBackground(Color.black);
				orbitsParametersPanel.setBackground(Color.black);
				simulationField.setBackground(Color.black);
				
				savePlanet.setBackground(Color.black);
				savePlanet.setForeground(Color.white);
				language.setForeground(Color.white);
				startStopButton.setBackground(Color.black);
				startStopButton.setForeground(Color.white);
				polish.setBackground(Color.black);
				polish.setForeground(Color.white);
				english.setBackground(Color.black);
				english.setForeground(Color.white);
				planetList.setBackground(Color.black);
				planetList.setForeground(Color.white);
				colorList.setBackground(Color.black);
				colorList.setForeground(Color.white);
				okColorButton.setBackground(Color.black);
				okColorButton.setForeground(Color.white);
				okPlanetButton.setBackground(Color.black);
				okPlanetButton.setForeground(Color.white);
				showAxis.setBackground(Color.black);
				showAxis.setForeground(Color.white);
				showOrbit.setBackground(Color.black);
				showOrbit.setForeground(Color.white);
				orbitsParametersPanel.orbitsParametersLabel.setForeground(Color.white);
				orbitsParametersPanel.semimajorAxisValueField.setBackground(Color.black);
				orbitsParametersPanel.semimajorAxisValueField.setForeground(Color.white);
				orbitsParametersPanel.semimajorAxisLabel.setForeground(Color.white);
				orbitsParametersPanel.semimajorAxisValueSlider.setBackground(Color.black);
				orbitsParametersPanel.semimajorAxisValueSlider.setForeground(Color.white);
				orbitsParametersPanel.semiminorAxisValueField.setBackground(Color.black);
				orbitsParametersPanel.semiminorAxisValueField.setForeground(Color.white);
				orbitsParametersPanel.semiminorAxisLabel.setForeground(Color.white);
				orbitsParametersPanel.eccentricityValueField.setBackground(Color.black);
				orbitsParametersPanel.eccentricityValueField.setForeground(Color.white);
				orbitsParametersPanel.eccentricityLabel.setForeground(Color.white);
				orbitsParametersPanel.eccentricityValueSlider.setBackground(Color.black);
				orbitsParametersPanel.eccentricityValueSlider.setForeground(Color.white);
				distanceLabel.setForeground(Color.white);
				maxDistanceToSun.setBackground(Color.black);
				maxDistanceToSun.setForeground(Color.white);
				maxDistanceLabel.setForeground(Color.white);
				minDistanceToSun.setBackground(Color.black);
				minDistanceToSun.setForeground(Color.white);
				minDistanceLabel.setForeground(Color.white);
				orbit.axisColor = Color.white;
				orbit.orbitColor = Color.white;
				simulationField.planetColor = new Color(132,195,190);
			}
		
			else {
				Color defaultMotive = new Color(255,253,253);
				topPanel.setBackground(defaultMotive);
				simulationActionPanel.setBackground(defaultMotive);
				distanceToSunPanel.setBackground(defaultMotive);
				choosePlanetPanel.setBackground(defaultMotive);
				checkBoxPanel.setBackground(defaultMotive);
				colorListPanel.setBackground(defaultMotive);
				distanceLabelPanel.setBackground(defaultMotive);
				maxDistancePanel.setBackground(defaultMotive);
				minDistancePanel.setBackground(defaultMotive);
				orbitsParametersPanel.setBackground(defaultMotive);
				simulationField.setBackground(defaultMotive);

				savePlanet.setBackground(defaultMotive);
				savePlanet.setForeground(Color.black);
				language.setForeground(Color.black);
				startStopButton.setBackground(defaultMotive);
				startStopButton.setForeground(Color.black);
				polish.setBackground(defaultMotive);
				polish.setForeground(Color.black);
				english.setBackground(defaultMotive);
				english.setForeground(Color.black);
				planetList.setBackground(defaultMotive);
				planetList.setForeground(Color.black);
				colorList.setBackground(defaultMotive);
				colorList.setForeground(Color.black);
				okColorButton.setBackground(defaultMotive);
				okColorButton.setForeground(Color.black);
				okPlanetButton.setBackground(defaultMotive);
				okPlanetButton.setForeground(Color.black);
				showAxis.setBackground(defaultMotive);
				showAxis.setForeground(Color.black);
				showOrbit.setBackground(defaultMotive);
				showOrbit.setForeground(Color.black);
				orbitsParametersPanel.orbitsParametersLabel.setForeground(Color.black);
				orbitsParametersPanel.semimajorAxisValueField.setBackground(defaultMotive);
				orbitsParametersPanel.semimajorAxisValueField.setForeground(Color.black);
				orbitsParametersPanel.semimajorAxisLabel.setForeground(Color.black);
				orbitsParametersPanel.semimajorAxisValueSlider.setBackground(defaultMotive);
				orbitsParametersPanel.semimajorAxisValueSlider.setForeground(Color.black);
				orbitsParametersPanel.semiminorAxisValueField.setBackground(defaultMotive);
				orbitsParametersPanel.semiminorAxisValueField.setForeground(Color.black);
				orbitsParametersPanel.semiminorAxisLabel.setForeground(Color.black);
				orbitsParametersPanel.eccentricityValueField.setBackground(defaultMotive);
				orbitsParametersPanel.eccentricityValueField.setForeground(Color.black);
				orbitsParametersPanel.eccentricityLabel.setForeground(Color.black);
				orbitsParametersPanel.eccentricityValueSlider.setBackground(defaultMotive);
				orbitsParametersPanel.eccentricityValueSlider.setForeground(Color.black);
				distanceLabel.setForeground(Color.black);
				maxDistanceToSun.setBackground(defaultMotive);
				maxDistanceToSun.setForeground(Color.black);
				maxDistanceLabel.setForeground(Color.black);
				minDistanceToSun.setBackground(defaultMotive);
				minDistanceToSun.setForeground(Color.black);
				minDistanceLabel.setForeground(Color.black);
				orbit.axisColor = Color.black;
				orbit.orbitColor = Color.black;
				simulationField.planetColor = new Color(142, 10, 70);
				
			}	
		}
};
ActionListener OpenPlanetBase = new ActionListener() {
	public void actionPerformed(ActionEvent g) {
	String chosenPlanet = (String) planetList.getSelectedItem();
			try {
				int planet = planetList.getSelectedIndex();
				//System.out.println(""+planet);
				if(planet==0) {
					System.out.println("Nie wybrano planety");
				}
				else {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://db4free.net/planetbase", "projectuser", "objectprogramming");

					PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM planetlist WHERE Id="+planet);
					
					statement.execute();
					ResultSet rs = statement.getResultSet();
			
					
					ResultSetMetaData md  = rs.getMetaData();
				
					
					while (rs.next()) {
							
						orbitsParametersPanel.eccentricityValue = (double) rs.getObject(3);
						
						orbitsParametersPanel.eccentricityValueSlider.setValue((int) (orbitsParametersPanel.eccentricityValue*1000));
						
						orbitsParametersPanel.semimajorAxisValue = (double) rs.getObject(4);
						
						orbitsParametersPanel.semimajorAxisValueSlider.setValue( (int)(orbitsParametersPanel.semimajorAxisValue*1000));
					}
					
				}	
					
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			finally {
				if (conn!= null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	
};
KeyListener Escape = new KeyListener() {
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	    int key = e.getKeyCode();
	
	    if(key == KeyEvent.VK_ESCAPE);
	    {
	    	System.out.println("escape");
	    	System.exit(0);
	    }
	}
};

ActionListener ReadFileListener = new ActionListener() {
	public void actionPerformed(ActionEvent g) {
		JFileChooser chooser = new JFileChooser("Wybierz plik");
		int result = chooser.showDialog(null, "Wybierz");
		///originalText = "";
		String tekstDoUzupelnienia = "";
		if(JFileChooser.APPROVE_OPTION==result) {
			System.out.println("Wybrano plik: "+ chooser.getSelectedFile().toPath());
			File fileToRead = chooser.getSelectedFile();
			int value = 0;
			 String[] results = null;
			
				try {
					
						Scanner scanner = new Scanner(fileToRead);
						String data;
						while(scanner.hasNextLine()) {
							data = scanner.nextLine();
						System.out.print(data);
								
							}
						scanner.close();
					//	System.out.println("arabes"+results[1]);
						
//						String line = scanner.nextLine();
//						Double ecc = scanner.nextDouble();
//						//String line2 = scanner.nextLine();
//						//float semimajor = scanner.nextFloat();
//						System.out.println(line+ "  "+ecc);
//						System.out.println("line2"+ "  "+semimajor);
//						if(line == "Eccentricity:") {
//							//String ecc = scanner.nextLine();
//							orbitsParametersPanel.eccentricityValue =Double.valueOf(scanner.nextLine());
//							orbitsParametersPanel.eccentricityValueSlider.setValue((int) (orbitsParametersPanel.eccentricityValue*1000));
//
//							System.out.println("ecc "+orbitsParametersPanel.eccentricityValue);
//						}
//						InputStreamReader isr = new InputStreamReader(
//							new FileInputStream(fileToRead));
//							//Charset.forName("UTF-8").newDecoder());
//							BufferedReader bfr = new BufferedReader(isr);
//							StringBuffer buffer1 = new StringBuffer();
//							
							
							
//						while ((originalText = bfr.readLine()) != null) {
//							originalBuffer.append(originalText);
//							tekstDoUzupelnienia = originalText.replace("ż", "?");
//							tekstDoUzupelnienia = tekstDoUzupelnienia.replace("rz", "?");
//							tekstDoUzupelnienia = tekstDoUzupelnienia.replace("ó", "?");
//							tekstDoUzupelnienia = tekstDoUzupelnienia.replace("u", "?");
//							tekstDoUzupelnienia = tekstDoUzupelnienia.replace("h", "?");
//							tekstDoUzupelnienia = tekstDoUzupelnienia.replace("ch", "?");
//						
//							buffer1.append(tekstDoUzupelnienia);	
//						
//						editorPane.setText(buffer1.toString());

//}
					scanner.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		}
		else {
			System.out.println("Nie wybrano pliku");
		}
	}	
};


ActionListener SaveFileListener = new ActionListener() {
	public void actionPerformed(ActionEvent g) {
		JFileChooser chooser = new JFileChooser("Wybierz plik");
		int result = chooser.showDialog(null, "Wybierz");
		File fileToSave;
		//modifiedText = "";
		if(JFileChooser.APPROVE_OPTION==result) {
			System.out.println("Wybrano plik: "+ chooser.getSelectedFile().toPath());
			fileToSave = chooser.getSelectedFile();
			try {
				OutputStreamWriter osr = new OutputStreamWriter(
					new FileOutputStream(fileToSave));					
					BufferedWriter bfr = new BufferedWriter(osr);
					bfr.write("Eccentricity: "+(orbitsParametersPanel.eccentricityValue/1000)+"AU, semimajor axis: "+(orbitsParametersPanel.semimajorAxisValue/1000)
							+"AU, semiminor axis: "+orbitsParametersPanel.semiminorAxisValue);
					
					bfr.close();
}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
		else {
			System.out.println("Nie wybrano pliku");
		}
	}
};
public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			MainFrame f = new MainFrame();
			f.setVisible(true);
			f.splitPane.setDividerLocation(0.66);
		}
	});

}
	
    

    
} 