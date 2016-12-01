package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Model.Bracket;
import Model.Match;

public class ViewSchedules extends JFrame
{
	private final int WIDTH = 500;
	private final int HEIGHT = 430;
	
	private Bracket bracket;
	
	private MenuBar menuBar;
	private DefaultListModel<String> matchModel;
	private JList<String> matchList;
	
	
	public ViewSchedules(Bracket bracket)
	{
		this.bracket = bracket;
		addComponents();
	}
	
	/**
	 * This method builds the gui.
	 */
	public void addComponents()
	{
		// set default frame properties
		setSize(WIDTH,HEIGHT);
		setTitle("View Schedules");
		setLocation(10,10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		
		// get the top menu and initialize it in this pane
		TopMenu tm = new TopMenu(this);
		menuBar = tm.getMenuBar();
		setMenuBar(menuBar);
		tm.initializeMenuBar(menuBar);
		
		// create and format main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(400,400);
		GridBagLayout gridbag = new GridBagLayout();
		mainPanel.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10,10,10,10);
		gbc.anchor = GridBagConstraints.WEST;
		
		// initialize string list model
		matchModel = new DefaultListModel<String>();
		
		// add list of matches to model
		Queue<Match> matches = bracket.getMatches();
		
		for(Match m : matches)
			matchModel.addElement(m.getTeam1().getName() + " vs. " + m.getTeam2().getName());
		
		// initialize list with model
		matchList = new JList<String>(matchModel);
		
		// create scroll pane
		JScrollPane scrollPane = new JScrollPane(matchList);
		Dimension d = matchList.getPreferredSize();
		d.width = 150;
		d.height = 156;
		scrollPane.setPreferredSize(d);
		
		// arrange components and add to main panel
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(scrollPane, gbc);
		
		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
	}
	
}
