package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Model.Player;
import Model.Team;
import Model.Tournament;
import GUI.TopMenu;

/**
 * This class is used to display the create tournament frame which is used to
 * create a new tournament.
 */
public class EditTournament extends JFrame implements ActionListener {
	// instance variables
	private final int WIDTH = 550;
	private final int HEIGHT = 600;
	private final String tournamentFile = "tournaments/files.txt";
	private Tournament editableTournament;

	private JLabel tNameLbl;
	private JLabel tStartDateLbl;
	private JLabel tEndDateLbl;
	private JLabel regStartDateLbl;
	private JLabel regEndDateLbl;
	private JLabel minAgeLbl;
	private JLabel maxAgeLbl;
	private JLabel numTeamsLbl;
	private JLabel formatLbl;

	private JLabel tNameErr;
	private JLabel tStartDateErr;
	private JLabel tEndDateErr;
	private JLabel regStartDateErr;
	private JLabel regEndDateErr;
	private JLabel requiredErr;

	private JTextField tName;

	private JSpinner numTeams;
	private JSpinner minAge;
	private JSpinner maxAge;

	private JDatePickerImpl tStartDate;
	private JDatePickerImpl tEndDate;
	private JDatePickerImpl regStartDate;
	private JDatePickerImpl regEndDate;

	private JRadioButton formatDiv;
	private JRadioButton formatSE;
	private JButton submit;

	private MenuBar menuBar;

	public EditTournament(Tournament t) {
		this.editableTournament = t;
		addComponents();
	}

	/**
	 * This method builds the gui.
	 */
	public void addComponents() {
		// set default frame properties
		setTitle("Create Tournament");
		setSize(WIDTH, HEIGHT);
		setLocation(10, 10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);

		// Getting the Top Menu and initializing it in this pane
		TopMenu tm = new TopMenu(this);
		menuBar = tm.getMenuBar();
		setMenuBar(menuBar);
		tm.initializeMenuBar(menuBar);

		// create and format main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(400, 400);
		GridBagLayout gridbag = new GridBagLayout();
		mainPanel.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;

		// create labels for fields
		tNameLbl = new JLabel("Tournament Name:");
		tStartDateLbl = new JLabel("Tournament Start Date:");
		tEndDateLbl = new JLabel("Tournament End Date:");
		regStartDateLbl = new JLabel("Registration Start Date:");
		regEndDateLbl = new JLabel("Registration End Date:");
		minAgeLbl = new JLabel("Minimum Player Age:");
		maxAgeLbl = new JLabel("Maximum Player Age:");
		numTeamsLbl = new JLabel("Number of Teams:");
		formatLbl = new JLabel("Tournament Format:");

		// error labels
		tNameErr = new JLabel("*");
		tStartDateErr = new JLabel("*");
		tEndDateErr = new JLabel("*");
		regStartDateErr = new JLabel("*");
		regEndDateErr = new JLabel("*");
		requiredErr = new JLabel("*Please fill in the required fields");

		tNameErr.setForeground(Color.RED);
		tStartDateErr.setForeground(Color.RED);
		tEndDateErr.setForeground(Color.RED);
		regStartDateErr.setForeground(Color.RED);
		regEndDateErr.setForeground(Color.RED);
		requiredErr.setForeground(Color.RED);

		// hide errors
		tNameErr.setVisible(false);
		tStartDateErr.setVisible(false);
		tEndDateErr.setVisible(false);
		regStartDateErr.setVisible(false);
		regEndDateErr.setVisible(false);
		requiredErr.setVisible(false);

		/* create fields for input */
		// text field
		tName = new JTextField(18);
		tName.setText(editableTournament.getName());
		// date pickers
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		UtilDateModel tSDModel = new UtilDateModel();
		JDatePanelImpl tSDPanel = new JDatePanelImpl(tSDModel, p);
		tStartDate = new JDatePickerImpl(tSDPanel, new DateLabelFormatter());
		tStartDate.getJFormattedTextField()
				.setText(DateLabelFormatter.toStringFormat(editableTournament.getStartDate()));

		UtilDateModel tEDModel = new UtilDateModel();
		JDatePanelImpl tEDPanel = new JDatePanelImpl(tEDModel, p);
		tEndDate = new JDatePickerImpl(tEDPanel, new DateLabelFormatter());
		tEndDate.getJFormattedTextField().setText(DateLabelFormatter.toStringFormat(editableTournament.getEndDate()));

		UtilDateModel rSDModel = new UtilDateModel();
		JDatePanelImpl rSDPanel = new JDatePanelImpl(rSDModel, p);
		regStartDate = new JDatePickerImpl(rSDPanel, new DateLabelFormatter());
		regStartDate.getJFormattedTextField()
				.setText(DateLabelFormatter.toStringFormat(editableTournament.getRegStartDate()));

		UtilDateModel rEDModel = new UtilDateModel();
		JDatePanelImpl rEDPanel = new JDatePanelImpl(rEDModel, p);
		regEndDate = new JDatePickerImpl(rEDPanel, new DateLabelFormatter());
		regEndDate.getJFormattedTextField()
				.setText(DateLabelFormatter.toStringFormat(editableTournament.getRegEndDate()));

		// counters
		SpinnerModel minModel = new SpinnerNumberModel(10, 10, 100, 1);
		minAge = new JSpinner(minModel);
		minAge.setValue(editableTournament.getMinAge());

		SpinnerModel maxModel = new SpinnerNumberModel(20, 10, 100, 1);
		maxAge = new JSpinner(maxModel);
		maxAge.setValue(editableTournament.getMaxAge());

		SpinnerModel numModel = new SpinnerNumberModel(2, 2, 100, 1);
		numTeams = new JSpinner(numModel);
		numTeams.setValue(editableTournament.getNumTeams());

		// radio buttons
		formatDiv = new JRadioButton("Divisions");
		formatSE = new JRadioButton("Single Elimination");
		ButtonGroup formatChoice = new ButtonGroup();
		formatChoice.add(formatDiv);
		formatChoice.add(formatSE);
		if (editableTournament.getType().equals("Single Elimination")) {
			formatSE.setSelected(true);
		}
		else {
			formatDiv.setSelected(true);
		}
		
		// main button
		submit = new JButton("Edit");

		// arrange components and add to main panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(tNameLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(tName, gbc);

		gbc.gridx = 2;
		mainPanel.add(tNameErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(regStartDateLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(regStartDate, gbc);

		gbc.gridx = 2;
		mainPanel.add(regStartDateErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(regEndDateLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(regEndDate, gbc);

		gbc.gridx = 2;
		mainPanel.add(regEndDateErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(tStartDateLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(tStartDate, gbc);

		gbc.gridx = 2;
		mainPanel.add(tStartDateErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		mainPanel.add(tEndDateLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(tEndDate, gbc);

		gbc.gridx = 2;
		mainPanel.add(tEndDateErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		mainPanel.add(minAgeLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(minAge, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		mainPanel.add(maxAgeLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(maxAge, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		mainPanel.add(numTeamsLbl, gbc);

		gbc.gridx = 1;
		mainPanel.add(numTeams, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		mainPanel.add(formatLbl, gbc);

		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 0, 0);
		mainPanel.add(formatDiv, gbc);

		gbc.gridy = 9;
		mainPanel.add(formatSE, gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(requiredErr, gbc);

		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(30, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(submit, gbc);

		submit.addActionListener(this);

		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(30, 0, 30, 0));
		add(mainPanel, BorderLayout.NORTH);
	}

	/**
	 * This method is executed when the create button is clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		boolean empty = false;
		boolean valid = true;

		// properties of a tournament
		String name;
		String type;
		LocalDate startDate;
		LocalDate endDate;
		LocalDate rStartDate;
		LocalDate rEndDate;
		int minPlayerAge;
		int maxPlayerAge;
		int teams;

		// check for empty fields
		if (tName.getText().equals("")) {
			tNameErr.setVisible(true);
			requiredErr.setVisible(true);
			empty = true;
		} else {
			tNameErr.setVisible(false);
		}

		if (tStartDate.getJFormattedTextField().getText().equals("")) {
			tStartDateErr.setVisible(true);
			requiredErr.setVisible(true);
			empty = true;
		} else {
			tStartDateErr.setVisible(false);
		}

		if (tEndDate.getJFormattedTextField().getText().equals("")) {
			tEndDateErr.setVisible(true);
			requiredErr.setVisible(true);
			empty = true;
		} else {
			tEndDateErr.setVisible(false);
		}

		if (regStartDate.getJFormattedTextField().getText().equals("")) {
			regStartDateErr.setVisible(true);
			requiredErr.setVisible(true);
			empty = true;
		} else {
			regStartDateErr.setVisible(false);
		}

		if (regEndDate.getJFormattedTextField().getText().equals("")) {
			regEndDateErr.setVisible(true);
			requiredErr.setVisible(true);
			empty = true;
		} else {
			regEndDateErr.setVisible(false);
		}

		// validate fields
		if (!empty) {
			// hide errors
			tNameErr.setVisible(false);
			tStartDateErr.setVisible(false);
			tEndDateErr.setVisible(false);
			regStartDateErr.setVisible(false);
			regEndDateErr.setVisible(false);
			requiredErr.setVisible(false);

			/* get tournament properties */
			// get name
			name = tName.getText();

			// get type based on selection
			if (formatDiv.isSelected())
				type = formatDiv.getText();
			else
				type = formatSE.getText();

			// get min age, max age and num of teams
			minPlayerAge = (int) minAge.getValue();
			maxPlayerAge = (int) maxAge.getValue();
			teams = (int) numTeams.getValue();

			// get dates
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			startDate = LocalDate.parse(tStartDate.getJFormattedTextField().getText(), df);
			endDate = LocalDate.parse(tEndDate.getJFormattedTextField().getText(), df);
			rStartDate = LocalDate.parse(regStartDate.getJFormattedTextField().getText(), df);
			rEndDate = LocalDate.parse(regEndDate.getJFormattedTextField().getText(), df);

			// check dates
			if (startDate.compareTo(endDate) > 0 || rStartDate.compareTo(rEndDate) > 0) {
				if (startDate.compareTo(endDate) > 0) {
					tStartDateErr.setVisible(true);
					tEndDateErr.setVisible(true);
				}

				if (rStartDate.compareTo(rEndDate) > 0) {
					regStartDateErr.setVisible(true);
					regEndDateErr.setVisible(true);
				}

				JOptionPane.showMessageDialog(null, "Start date cannot be after than end date!", "Error",
						JOptionPane.ERROR_MESSAGE);
				valid = false;
			} else {
				if (rEndDate.compareTo(startDate) > 0) {
					JOptionPane.showMessageDialog(null, "Registration should end before tournament begins!", "Error",
							JOptionPane.ERROR_MESSAGE);
					regEndDateErr.setVisible(true);
					valid = false;
				}
			}

			// proceed to create and save tournament if dates are valid
			if (valid) {
				// hide errors
				tStartDateErr.setVisible(false);
				tEndDateErr.setVisible(false);
				regStartDateErr.setVisible(false);
				regEndDateErr.setVisible(false);

				// create tournament object
				Tournament t = new Tournament(name, type, startDate, endDate, rStartDate, rEndDate, minPlayerAge,
						maxPlayerAge, teams);

				// attempt to save details to file
				boolean saved = updateTournament(editableTournament.getName(), t);

				// give user feedback
				if (saved)
					JOptionPane.showMessageDialog(this, "Tournament updated!");
				else
					JOptionPane.showMessageDialog(this, "Something went wrong!");


			}
		}
	}
	
	
	/**
	 * This method attempts to update tournament to the file.
	 * 
	 * @param name
	 *            the name of the file
	 * @param updatedTournament
	 *            Tournament
	 * @return A flag indicating success or failure to update the file
	 */
	public boolean updateTournament(String name, Tournament updatedTournament)
	{	
		// get name for retrieving save file
		String fileName = "tournaments/" + name + ".xml";
		
		// xml document for to load file
		Document dom;
				
		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
				
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
					
			// load the xml file
			dom = db.parse(fileName);
			
			// get root element
			Element root = dom.getDocumentElement();
			
			// Get name element
			NodeList nameList = dom.getElementsByTagName("name");			
			Element nameElement = (Element) nameList.item(0);
			nameElement.setTextContent(updatedTournament.getName());
			
			// Get type element
			NodeList typeList = dom.getElementsByTagName("type");
			Element typeElement = (Element) typeList.item(0);
			typeElement.setTextContent(updatedTournament.getType());
			
			// Get numteams element
			NodeList numteamsList = dom.getElementsByTagName("numTeams");
			Element numteamsElement = (Element) numteamsList.item(0);
			numteamsElement.setTextContent(Integer.toString(updatedTournament.getNumTeams()));
			
			// Get regStartDate element
			NodeList regSDList = dom.getElementsByTagName("regStartDate");
			Element regSDElement = (Element) regSDList.item(0);
			regSDElement.setTextContent(updatedTournament.getRegStartDate().toString());

			// Get regEndDate element
			NodeList regEDList = dom.getElementsByTagName("regEndDate");
			Element regEDElement = (Element) regEDList.item(0);
			regEDElement.setTextContent(updatedTournament.getRegEndDate().toString());
			
			// Get Tournament Start Date element
			NodeList tourSDList = dom.getElementsByTagName("startDate");
			Element tourSDElement = (Element) tourSDList.item(0);
			tourSDElement.setTextContent(updatedTournament.getStartDate().toString());

			// Get Tournament End Date element
			NodeList tourEDList = dom.getElementsByTagName("endDate");
			Element tourEDElement = (Element) tourEDList.item(0);
			tourEDElement.setTextContent(updatedTournament.getEndDate().toString());
			
			// Get minPlayerAge element
			NodeList minPlayerAgeList = dom.getElementsByTagName("minPlayerAge");
			Element minPlayerAgeElement = (Element) minPlayerAgeList.item(0);
			minPlayerAgeElement.setTextContent(Integer.toString(updatedTournament.getMinAge()));
			
			// Get maxPlayerAge element
			NodeList maxPlayerAgeList = dom.getElementsByTagName("maxPlayerAge");
			Element maxPlayerAgeElement = (Element) maxPlayerAgeList.item(0);
			maxPlayerAgeElement.setTextContent(Integer.toString(updatedTournament.getMaxAge()));
			
			try
			{
				// add properties to file and format it
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "tournament.dtd");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				
				// save file
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(fileName)));
				return true;
			}
			catch(TransformerException te)
			{
				System.out.println(te.getMessage());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println(pce.getMessage());
		}
		catch(SAXException se)
		{
            System.out.println(se.getMessage());
        }
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return false;
	}
}
