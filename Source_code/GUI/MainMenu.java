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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import GUI.CreateTournament;
import GUI.TopMenu;
import Model.Tournament;


public class MainMenu extends JFrame 
{
	private final int WIDTH  = 500;
	private final int HEIGHT = 350;
	private final String tournamentFile = "src/tournaments/files.txt";
	
	private MenuBar menuBar;
	private JButton btnCreateTournament;
	private JButton btnRegisterTeam;
	private JButton btnSetSchedules;
	private JButton btnSetMatches;
	private JList<String> tournamentList;
	
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
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(400,400);
		
		// set layout
		GridBagLayout gridbag = new GridBagLayout();
		mainPanel.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		
		// create components to add
		btnCreateTournament = new JButton("Create Tournament");
		btnRegisterTeam = new JButton("Register Team");
		btnSetSchedules = new JButton("Set Schedules");
		btnSetMatches = new JButton("Set Matches");
		
		// set size of buttons
		Dimension dim = new Dimension(150,25);
		btnRegisterTeam.setPreferredSize(dim);
		btnSetSchedules.setPreferredSize(dim);
		btnSetMatches.setPreferredSize(dim);
		
		// retrieve list of tournaments
		ArrayList<Tournament> tournaments = getTournaments();
				
		// create string list model
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		if(tournaments.isEmpty())
		{
			model.addElement("N/A");
			btnRegisterTeam.setEnabled(false);
			btnSetMatches.setEnabled(false);
			btnSetSchedules.setEnabled(false);
		}
		else
		{
			// add tournament names to model
			for(Tournament t : tournaments)
				model.addElement(t.getName());
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
				dispose();
			}
		});
				
		// handle click event for register team
		btnRegisterTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
		        RegisterTeam r = new RegisterTeam();
		        r.setVisible(true);
		        dispose();
			}
		});
		
		// arrange components and add to main panel
		gbc.insets = new Insets(30,10,10,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		mainPanel.add(btnCreateTournament, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		mainPanel.add(scrollPane, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(btnRegisterTeam, gbc);
			
		gbc.gridx = 1;
		gbc.gridy = 2;
		mainPanel.add(btnSetMatches, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		mainPanel.add(btnSetSchedules, gbc);
		
		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
		
		// display frame
		setVisible(true);
	}
	
	/**
	 * This method returns a list of saved tournaments.
	 * 
	 * @return The list of saved tournaments
	 */
	public ArrayList<Tournament> getTournaments()
	{	
		String line = null;
		ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
		
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
		int numTeams, minAge, maxAge;
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
}