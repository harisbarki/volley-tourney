package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.CreateTournament;
import GUI.TopMenu;

public class MainMenu extends JFrame 
{
	private final int WIDTH  = 400;
	private final int HEIGHT = 300;
	
	private MenuBar menuBar;
	private JButton btnCreateTournament;
	private JButton btnRegisterTeam;
	private JButton btnListTournaments;
	
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
		mainPanel.setSize(250,300);
		
		// set layout
		GridBagLayout gridbag = new GridBagLayout();
		mainPanel.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		
		// create components to add
		btnCreateTournament = new JButton("Create Tournament");
		btnRegisterTeam = new JButton("Register Team");
		btnListTournaments = new JButton("List Tournaments");
		
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
//		        JOptionPane.showMessageDialog(null, "Register Team clicked", "Success", JOptionPane.INFORMATION_MESSAGE);
				RegisterTeam r = new RegisterTeam();
				r.setTitle("Register Team");
				r.setVisible(true);
				dispose();
			}
		});
				
		// handle click event for list tournaments
		btnListTournaments.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				System.out.println("register btn pressed");
			}	
		});
		
		// arrange components and add to main panel
		gbc.gridx = 0;
		gbc.insets = new Insets(30,0,10,0);
		
		gbc.gridy = 0;
		mainPanel.add(btnCreateTournament, gbc);
		
		gbc.gridy = 1;
		mainPanel.add(btnRegisterTeam, gbc);
		
		gbc.gridy = 2;
		mainPanel.add(btnListTournaments, gbc);
		
		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
		
		// display frame
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		MainMenu mm = new MainMenu();
	}
}