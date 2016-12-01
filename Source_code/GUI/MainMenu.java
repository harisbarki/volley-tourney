package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import GUI.CreateTournament;
import GUI.TopMenu;
import Model.Team;
import Model.Tournament;


public class MainMenu extends JFrame 
{
	private final int WIDTH  = 500;
	private final int HEIGHT = 350;
	
	private MenuBar menuBar;
	private JButton btnCreateTournament;
	private JButton btnRegisterTeam;
	private JButton btnSetSchedule;
	private JButton btnViewSchedule;
	private JButton btnTournamentDetails;
	private JPanel mainPanel;
	private JPanel tournamentDetails;
	
	private JList<String> tournamentList;
	private ArrayList<Tournament> tournaments;
	
	private final String tournamentFile = "tournaments/files.txt";
	
	public MainMenu()
	{
		createMainMenu();
	}
	
	/**
	 * This method builds the gui.
	 */
	public void createMainMenu()
	{
		// set default frame properties
		setTitle("Volleyball Tournament System");
		setSize(WIDTH,HEIGHT);
		setLocation(10,10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
			
		// Getting the Top Menu and initializing it in this pane
		TopMenu tm = new TopMenu(this);
		menuBar = tm.getMenuBar();
		setMenuBar(menuBar);
		tm.initializeMenuBar(menuBar);
		
		// create and format main panel
		mainPanel = new JPanel();
		mainPanel.setSize(400,400);
		
		// set layout
		GridBagLayout gridbag = new GridBagLayout();
		mainPanel.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		
		// create components to add
		btnCreateTournament = new JButton("Create Tournament");
		btnRegisterTeam = new JButton("Register Team");
		btnViewSchedule = new JButton("View Schedule");
		btnSetSchedule = new JButton("Generate Schedule");
		btnTournamentDetails = new JButton("Tournament Details");

		
		// set size of buttons
		Dimension dim = new Dimension(200,25);
		btnRegisterTeam.setPreferredSize(dim);
		btnViewSchedule.setPreferredSize(dim);
		btnSetSchedule.setPreferredSize(dim);
		btnTournamentDetails.setPreferredSize(dim);
		
		tournaments = getTournaments();
				
		// create string list model
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		if(tournaments.isEmpty())
		{
			model.addElement("N/A");
			btnRegisterTeam.setEnabled(false);
			btnSetSchedule.setEnabled(false);
			btnViewSchedule.setEnabled(false);
			btnTournamentDetails.setEnabled(false);
			
		}
		else
		{
			// add tournament names to model
			for(Tournament t : tournaments)
				model.addElement(t.getId() + " " + t.getName());
		}
			
		// initialize list with model
		tournamentList = new JList<String>(model);

		// create scroll pane
		JScrollPane scrollPane = new JScrollPane(tournamentList);
		Dimension d = tournamentList.getPreferredSize();
		d.width = 150;
		d.height = 156;
		scrollPane.setPreferredSize(d);
		
		// handle click event for create tournament
		btnCreateTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event)
			{
				CreateTournament c = new CreateTournament();
				c.setVisible(true);
				dispose();
			}
		});
				
		// handle click event for register team
		btnRegisterTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				try {
					Tournament selectedTournament = getSelectedTournament();

					if(selectedTournament.getTeams().size() == selectedTournament.getNumTeams())
					{
						JOptionPane.showMessageDialog(null, "The tournament's team capacity has been reached", "Registration Closed", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
				        RegisterTeam r = new RegisterTeam(selectedTournament);
				        r.setVisible(true);
				        dispose();
					}
				} catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null,  "Please select a tournament", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//handle click event for set schedule
		btnSetSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Tournament selectedTournament = getSelectedTournament();
					
					if(selectedTournament.getTeams().size() < selectedTournament.getNumTeams()) 
					{
						JOptionPane.showMessageDialog(null, "Please register more teams", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						SetMatches r = new SetMatches(selectedTournament);
						r.setVisible(true);
						dispose();
					}
				} catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null,  "Please select a tournament", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		final MainMenu m = this;
		//handle click event for tournament details
		btnTournamentDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String selectedTournamentName = tournamentList.getSelectedValue();
					Scanner s = new Scanner(selectedTournamentName);
					int selectedTournamentId = s.nextInt();
					Tournament selectedTournament = tournaments.get(selectedTournamentId-1);
					tournamentDetails = new TournamentDetails(selectedTournament, m);
					mainPanel.setVisible(false);
					add(tournamentDetails);
					tournamentDetails.setVisible(true);
					s.close();
				} catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Please select a tournament", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		tournamentList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent listSelectionEvent)
			{
				Tournament t = getSelectedTournament();
				if(t.getBracket() != null)
					btnSetSchedule.setEnabled(false);
			}
		});
		
		// arrange components and add to main panel
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		mainPanel.add(btnCreateTournament, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 4;
		mainPanel.add(scrollPane, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(btnRegisterTeam, gbc);
			
		gbc.gridx = 1;
		gbc.gridy = 2;
		mainPanel.add(btnSetSchedule, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		mainPanel.add(btnViewSchedule, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		mainPanel.add(btnTournamentDetails, gbc);
		
		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
				
		// display frame
		setVisible(true);
	}
	
	public void editTeam(Tournament tournament, Team team) {
		EditTeams r = new EditTeams(tournament, team);
		tournamentDetails.setVisible(false);
		r.setVisible(true);
		add(r);
	}
	
	/**
	 * This method returns a list of saved tournaments.
	 * 
	 * @return The list of saved tournaments
	 */
	public ArrayList<Tournament> getTournaments()
	{	
		String line = null;
		tournaments = new ArrayList<Tournament>();
		
		BufferedReader br;
		
		try
		{
			// open file for reading
			FileInputStream fis = new FileInputStream(tournamentFile);
			br = new BufferedReader(new InputStreamReader(fis));
			
			// attempt to read from file
			while((line = br.readLine()) != null)
			{
				// skip blank line
				if(line == "") continue;
				
				// get tournament from file
				Tournament t = getTournament(line);
				tournaments.add(t);
			}

			br.close();
			return tournaments;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return tournaments;
		}
	}
	
	/**
	 * This method returns a tournament from the file.
	 * 
	 * @param name The tournament file name
	 * @return A saved tournament
	 */
	public Tournament getTournament(String name)
	{
		// tournament object to return
		Tournament t = null;
		
		// variables to store data from file
		String tName, tType; 
		int id, numTeams, minAge, maxAge;
		LocalDate regSD, regED, tSD, tED;
		
		// xml document for to load file
		Document dom;
		
		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
		
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// load the xml file
			dom = db.parse(name);
			
			// get root element
			Element root = dom.getDocumentElement();
			
			// for parsing the dates
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			// get child nodes
			id = Integer.parseInt(getTextValue(root, "id"));
			tName = getTextValue(root, "name");
			tType = getTextValue(root, "type");
			numTeams = Integer.parseInt(getTextValue(root, "numTeams"));
			regSD = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			regED = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			tSD = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			tED = LocalDate.parse(getTextValue(root, "regEndDate"), df);
			minAge = Integer.parseInt(getTextValue(root, "minPlayerAge"));
			maxAge = Integer.parseInt(getTextValue(root, "maxPlayerAge"));
			
			// create and return tournament
			t = new Tournament(tName, tType, tSD, tED, regSD, regED, minAge, maxAge, numTeams);
			t.setID(id);
			return t;
		}
		catch(ParserConfigurationException pce)
		{
            System.out.println(pce.getMessage());
            return t;
        }
		catch(SAXException se)
		{
            System.out.println(se.getMessage());
            return t;
        }
		catch(IOException ioe)
		{
            System.err.println(ioe.getMessage());
            return t;
        }
	}
	
	/**
	 * This method retrieves the text node at the 
	 * specified child node.
	 * 
	 * @param doc The parent node
	 * @param tag The child node
	 * @return The text node value of the child node
	 */
	private String getTextValue(Element root, String child)
	{
	    String value = "";
	    
	    NodeList nl;
	    nl = root.getElementsByTagName(child);
	    
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes())
	    {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    
	    return value;
	}
	
	/** 
	 * This method gets the currently selected tournament.
	 * 
	 * @return The selected tournament
	 */
	public Tournament getSelectedTournament()
	{
		String selectedTournamentName = tournamentList.getSelectedValue();
		Scanner s = new Scanner(selectedTournamentName);
		int selectedTournamentId = s.nextInt();
		Tournament selectedTournament = tournaments.get(selectedTournamentId-1);
		s.close();
		
		return selectedTournament;
	}
}