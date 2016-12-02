package GUI;
import Model.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;




public class SetMatches extends JFrame {

	private final int WIDTH = 500;
	private final int HEIGHT = 430;
	
	private MenuBar menuBar;
	private JButton btnCreateBracket;
	private JButton btnSeedTeam;
	private JButton btnRemoveSeed;
	
	private Tournament tournament;
	
	private DefaultListModel<String> rankModel;
	private DefaultListModel<String> teamModel;
	
	private JList<String> teamList;
	private JList<String> seedingList;
	
	private ArrayList<Team> teams;
	
	private final String tournamentFile = "tournaments/files.txt";

	public SetMatches(Tournament tourney) {
		
		//setting frame properties
		setTitle("Set Matches");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);	
		
		this.tournament = tourney;
		
		//Initializing top menu bar
		TopMenu topMenu = new TopMenu(this);
		menuBar = topMenu.getMenuBar();
		setMenuBar(menuBar);
		topMenu.initializeMenuBar(menuBar);
		
		//Main panel layout and format
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(500,500);
		
		GridBagLayout layout = new GridBagLayout();
		mainPanel.setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		
		//buttons
		btnCreateBracket = new JButton("Create Bracket");
		btnSeedTeam = new JButton("Seed Team");
		btnRemoveSeed = new JButton("Remove Seed");		
		
		//size of buttons
		Dimension buttonSize = new Dimension(150,25);
		btnCreateBracket.setPreferredSize(buttonSize);

		teams = tourney.getTeams();
		
		// create string list model for list of teams
		teamModel = new DefaultListModel<String>();
		
		if (teams.isEmpty())
		{
			teamModel.addElement("No teams registered");
			btnCreateBracket.setEnabled(false);
			btnSeedTeam.setEnabled(false);
			btnRemoveSeed.setEnabled(false);
		}
		else
		{
			// add team names to model
			for (Team t : teams)
				teamModel.addElement(t.getName());
		}
		
		// initialize team list with model
		teamList = new JList<String>(teamModel);
		
		// create string list model for seeded teams
		rankModel = new DefaultListModel<String>();	
		//initialize seeding list with model
		seedingList = new JList<String>(rankModel);
		
		
		// create scroll pane for list of teams
		JScrollPane teamScrollPane = new JScrollPane(teamList);
		Dimension teamScrollSize = teamList.getPreferredSize();
		teamScrollSize.width = 150;
		teamScrollSize.height = 156;
		teamScrollPane.setPreferredSize(teamScrollSize);
		
		// create scroll pane for ranking of teams
		JScrollPane rankScrollPane = new JScrollPane(seedingList);
		Dimension rankScrollSize = seedingList.getPreferredSize();
		rankScrollSize.width = 150;
		rankScrollSize.height = 156;
		rankScrollPane.setPreferredSize(rankScrollSize);
		
		// handle click event for add ranking
		btnSeedTeam.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedTeamName = teamList.getSelectedValue();
					Team selectedTeam = tournament.getATeam(selectedTeamName);
					
						if (!selectedTeam.seeded()) 
						{
							tournament.addRank(selectedTeam);
							rankModel.addElement(selectedTeam.getName());
							teamModel.removeElement(selectedTeamName);
						}
						else throw new IllegalStateException();
					
				}
				 catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Please select a team", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalStateException m) {
					JOptionPane.showMessageDialog(null, "Team already seeded", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		// handle click event for remove rank
		btnRemoveSeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
						String selectedTeamName = seedingList.getSelectedValue();
						Team selectedTeam = tournament.getATeam(selectedTeamName);
						
						rankModel.removeElement(selectedTeamName);
						teamModel.addElement(selectedTeamName);
						tournament.removeRank(selectedTeam);
				}
				catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null,"Please select a team", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// handle click event for set matches
		btnCreateBracket.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent event)
			{
				if(tournament.getSeededTeams().size() == tournament.getNumTeams())
				{
					Bracket bracket = new Bracket(tournament,tournament.getSeededTeams());
					tournament.setBracket(bracket);
					saveFile(tournament,tournament.getId() + "");
					ViewSchedules vs = new ViewSchedules(bracket);
					vs.setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please seed all teams", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		// arrange components and add to main panel
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		mainPanel.add(btnSeedTeam, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = -2;
		mainPanel.add(btnRemoveSeed, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 5;
		mainPanel.add(btnCreateBracket,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 3;
		mainPanel.add(teamScrollPane, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 3;
		mainPanel.add(rankScrollPane, gbc);
		
		//set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
		
		//display frame
		setVisible(true);
	}

	/**
	 * This method attempts to save a tournament to a file.
	 * 
	 * @param t The tournament
	 * @param fileName The name of the file
	 * @return A flag indicating success or failure to save the file
	 */
	public boolean saveFile(Tournament t, String fileName)
	{
		Document dom;
		Element e = null;
		
		// configure save path of current file
		fileName = "tournaments/" + fileName + ".xml";
		
		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// load the xml file
			dom = db.parse(fileName);
			
			Element root = dom.getDocumentElement();
			
			NodeList n = root.getElementsByTagName("seededList");
			e = (Element) n.item(0);
			
			for(Team t1 : t.getSeededTeams())
			{
				Element team = dom.createElement("teamName");
				team.appendChild(dom.createTextNode(t1.getName()));
				e.appendChild(team);
			}
			
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
				return false;
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
				return false;
			}
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
			return false;
		}
		catch(SAXException se)
		{
            System.out.println(se.getMessage());
            return false;
        }
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
			return false;
		}
	}
	
}

