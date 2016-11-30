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
	private JButton btnSetMatches;
	private JButton btnAddRanking;
	private JButton btnRemoveRank;
	
	private JList<String> teamList;
	private JList<String> seedingList;
	
	private List<Team> teams;


	public SetMatches(Tournament tourney) {
		
		//setting frame properties
		setTitle("Set Matches");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);	
		
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
		btnSetMatches = new JButton("Set Matches");
		btnAddRanking = new JButton("Add Rank");
		btnRemoveRank = new JButton("Remove Rank");
		
		
		//size of buttons
		Dimension buttonSize = new Dimension(150,25);
		btnSetMatches.setPreferredSize(buttonSize);
		
		teams = getTeams(tourney);
		
		// create string list model for list of teams
		DefaultListModel<String> teamModel = new DefaultListModel<String>();
		
		if (teams.isEmpty())
		{
			teamModel.addElement("No teams registered");
			btnSetMatches.setEnabled(false);
			btnAddRanking.setEnabled(false);
			btnRemoveRank.setEnabled(false);
		}
		else
		{
			// add team names to model
			for (Team t : teams)
				teamModel.addElement(t.getName());
		}
		
//		initialize team list with model
		teamList = new JList<String>(teamModel);
		
//		create string list model for seeded teams
		DefaultListModel<String> rankModel = new DefaultListModel<String>();	
		//initialize seeding list with model
		seedingList = new JList<String>(rankModel);
		
		
//		create scroll pane for list of teams
		JScrollPane teamScrollPane = new JScrollPane(teamList);
		Dimension teamScrollSize = teamList.getPreferredSize();
		teamScrollSize.width = 150;
		teamScrollSize.height = 156;
		teamScrollPane.setPreferredSize(teamScrollSize);
		
//		create scroll pane for ranking of teams
		JScrollPane rankScrollPane = new JScrollPane(seedingList);
		Dimension rankScrollSize = seedingList.getPreferredSize();
		rankScrollSize.width = 150;
		rankScrollSize.height = 156;
		rankScrollPane.setPreferredSize(rankScrollSize);
		
// handle click event for add ranking
		
		btnAddRanking.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedTeamName = teamList.getSelectedValue();
					Team selectedTeam = getATeam(selectedTeamName);
					
						if (!tourney.showTopTeams().contains(selectedTeam)) 
						{
						tourney.addRank(selectedTeam);
						rankModel.addElement(selectedTeam.getName());
						System.out.println(tourney.showTopTeams());
						System.out.println(tourney.showTeams());
						}
						else throw new IllegalStateException();
					
				}
				 catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Please select a Team", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalStateException m) {
					JOptionPane.showMessageDialog(null, "Team already seeded", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
// handle click event for remove rank
		
		btnRemoveRank.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
			try {
				String selectedTeamName = seedingList.getSelectedValue();
				Team selectedTeam = getATeam(selectedTeamName);
				rankModel.removeElement(selectedTeamName);
				tourney.removeRank(selectedTeam);
				System.out.println(tourney.showTopTeams());
				}
			catch(NullPointerException n) {
				JOptionPane.showMessageDialog(null,"Please select a Team", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		
// handle click event for set matches
		
//		btnSetMatches.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent event)
//			{
//				try {
//					
//				}
//				
//			}
//			
//		});
		
		// arrange components and add to main panel
		gbc.insets = new Insets(30,10,10,10);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		mainPanel.add(btnAddRanking, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = -2;
		mainPanel.add(btnRemoveRank, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 3;
		mainPanel.add(btnSetMatches,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 3;
		mainPanel.add(teamScrollPane, gbc);
		
		gbc.gridx = 4;
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
	
	
	public List<Team> getTeams(Tournament tourney)
	{	
		String line = null;
		teams = new ArrayList<Team>();
		String tournamentFile = ("tournaments/" + tourney.getName() +".xml" );
		String file = "tournaments/files.txt";
		
		BufferedReader br;
		
		try
		{
			// open file for reading
			FileInputStream fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
			
			
			// attempt to read from file
			while((line = br.readLine()) != null)
			{
				// skip blank line
				if(line == "") continue;
				

				// get teams from file
				teams = getTeamFrom(tournamentFile);
			}
			br.close();
			return teams;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return teams;
		}
	}
		
		public ArrayList<Team> getTeamFrom(String name)
		{
			// tournament object to return
			Team t = null;
			ArrayList<Team> tempTeam = new ArrayList<Team>();
			
			// variables to store data from file
			String teamName = null;
			
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
				NodeList child = dom.getElementsByTagName("team");
				
				for (int i = 0; i < child.getLength(); i++)
				{
					
				Node temp = child.item(i);
				
				//	retrieves all the team names
				if (temp.getNodeType() == Node.ELEMENT_NODE)
					{
				Element newRoot = (Element) temp;
				teamName = getTextValue(child, newRoot, "teamName");
				t = new Team(teamName);
				tempTeam.add(t);
					}
				}
				return tempTeam;
				
				
			}
			catch(ParserConfigurationException pce)
			{
	            System.out.println(pce.getMessage());
	            return tempTeam;
	        }
			catch(SAXException se)
			{
	            System.out.println(se.getMessage());
	            return tempTeam;
	        }
			catch(IOException ioe)
			{
	            System.err.println(ioe.getMessage());
	            return tempTeam;
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
		
		private String getTextValue(NodeList node, Element root, String child)
		{
		    String value = "";
		    
		    node = root.getElementsByTagName(child);
		    
		    if (node.getLength() > 0 && node.item(0).hasChildNodes())
		    {
		        value = node.item(0).getFirstChild().getNodeValue();
		    }
		    
		    return value;
		}
		
		//gets a team object from a given team name.
		private Team getATeam(String name) {
			
			Team team = null;
			
			for (int i = 0; i < teams.size(); i++) 
			{
				Team currentTeam = teams.get(i);
				
				if (currentTeam.getName() == name)
				{
					team = currentTeam;
				}
			}
			return team;
		}
		
		
		
		
		
	

		
	}

