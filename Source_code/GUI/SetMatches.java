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
		
		teams = tourney.getTeams();
		
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
					Team selectedTeam = tourney.getATeam(selectedTeamName);
					
						if (!selectedTeam.seeded()) 
						{
						tourney.addRank(selectedTeam);
						rankModel.addElement(selectedTeam.getName());
						teamModel.removeElement(selectedTeamName);
						
						System.out.println(tourney.showTopTeams());
						System.out.println(selectedTeam.showSeedPos());
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
				Team selectedTeam = tourney.getATeam(selectedTeamName);
				
				rankModel.removeElement(selectedTeamName);
				teamModel.addElement(selectedTeamName);
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


	
}

