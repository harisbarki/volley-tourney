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
	
	private MenuBar menuBar;
	private JButton btnCreateTournament;
	private JButton btnRegisterTeam;
	private JButton btnSetSchedules;
	private JButton btnSetMatches;
	private JList<String> tournamentList;
	static ArrayList<Tournament> tournaments;
	
	public MainMenu()
	{
		createMainMenu();
	}
	
	public MainMenu(ArrayList<Tournament> tournaments)
	{
		this.tournaments = tournaments;
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
				String selectedTournamentName = tournamentList.getSelectedValue();
				Scanner s = new Scanner(selectedTournamentName);
				int selectedTournamentId = s.nextInt();
				Tournament selectedTournament = tournaments.get(selectedTournamentId-1);
		        RegisterTeam r = new RegisterTeam();
		        r.setVisible(true);
		        s.close();
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
}