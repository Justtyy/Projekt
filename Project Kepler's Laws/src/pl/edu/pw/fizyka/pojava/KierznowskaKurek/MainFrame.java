package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;


public class MainFrame extends JFrame {
	
	
	int semimajorAxis;
	int eccentricity;
	int semiminorAxis;
	Orbit orbit;
	

	JPanel leftPanel, settingsPanel, topPanel, simulationActionPanel; //main panels
	JPanel settingsCenterPanel;
	JPanel choosePlanetPanel, orbitsParametersPanel, distanceToSunPanel, animationsActionsPanel, radioButtonPanel, colorListPanel;
	JPanel distanceLabelPanel, maxDistancePanel, minDistancePanel;
	final JSplitPane splitPane;
	JButton savePlanet, startStopButton;
	JLabel language, distanceLabel, minDistanceLabel, maxDistanceLabel;//labels
	JRadioButton polish, english;
	String[] planetStrings = {"Wybierz planetę", "Merkury","Wenus", "Ziemia", "Mars", "Jowisz", "Saturn", "Uran", "Neptun"};
	String[] colorStrings = {"Wybierz motyw symulacji", "Dzieñ", "Noc"};
	JComboBox planetList, colorList;
	JButton okPlanetButton, okColorButton;
	JRadioButton showOrbit, showAxis;
	JLabel minDistanceToSun, maxDistanceToSun;//values
	Border blackLine, grayLine;
	SimulationField simulationField;
	public MainFrame() throws HeadlessException {
		
		//main frame settings
		super("Prawa Keplera");
		this.setSize(1000,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		//border lines
		blackLine = BorderFactory.createLineBorder(Color.black);
		grayLine = BorderFactory.createLineBorder(Color.gray, 3);
		
		leftPanel = new JPanel();
		topPanel= new JPanel();
		settingsPanel = new JPanel();
		simulationField = new SimulationField(SpecialLayoutWithSlidersPanel.giveSemimajorAxis(), 
		SpecialLayoutWithSlidersPanel.giveEccentricity(), SpecialLayoutWithSlidersPanel.giveSemiminorAxis());
		//teoretycznie tu sie powinno cos zadziac, ale nic sie nie dzieje 
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    splitPane.setLeftComponent(leftPanel);
	    splitPane.setRightComponent(settingsPanel);
	    this.add(splitPane, BorderLayout.CENTER);
		
		leftPanel.setLayout(new BorderLayout());
		
		leftPanel.add(topPanel, BorderLayout.PAGE_START);
		topPanel.setBorder(blackLine);
		topPanel.setLayout(new FlowLayout());
		topPanel.add(savePlanet = new JButton("Zapisz własną planetę"));
		topPanel.add(language = new JLabel("Wybór języka:"));
		topPanel.add(polish = new JRadioButton("polski"));
		topPanel.add(english = new JRadioButton("angielski"));
		ButtonGroup group = new ButtonGroup();
		group.add(polish);
		group.add(english);
		leftPanel.add(simulationActionPanel = new JPanel());
		simulationActionPanel.setBorder(grayLine);
		simulationActionPanel.setLayout(new BorderLayout());
		simulationActionPanel.add(simulationField, BorderLayout.CENTER);
		orbit = new Orbit(SpecialLayoutWithSlidersPanel.giveSemimajorAxis(), 
		SpecialLayoutWithSlidersPanel.giveEccentricity(), SpecialLayoutWithSlidersPanel.giveSemiminorAxis());
		
		
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
		animationsActionsPanel.add(radioButtonPanel = new JPanel());
		radioButtonPanel.add(showOrbit = new JRadioButton("Wyświetl orbitę"));
		radioButtonPanel.add(showAxis = new JRadioButton("Wyświetl osie elipsy"));
		
		//List with colors and motives 
		colorList = new JComboBox(colorStrings);
		colorList.setSelectedIndex(0);
		animationsActionsPanel.add(colorListPanel = new JPanel());
		colorListPanel.add(colorList); 
		colorListPanel.add(okColorButton = new JButton("OK"));
		okColorButton.setToolTipText("Potwierdz wybór motywu animacji");
		
		animationsActionsPanel.add(startStopButton = new JButton("START/STOP"));

	} 
	 
    public static void main(String[] a) {
    	MainFrame frame = new MainFrame();
    	frame.setVisible(true);
    	frame.splitPane.setDividerLocation(0.66);
    	
  }
}