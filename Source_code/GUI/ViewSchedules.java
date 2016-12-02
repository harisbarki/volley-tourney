package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Model.Bracket;
import Model.Match;

public class ViewSchedules extends JFrame
{
	private final int WIDTH = 500;
	private final int HEIGHT = 430;
	
	private Bracket bracket;
	private Match activeMatch;
	
	private MenuBar menuBar;
	private DefaultListModel<String> matchModel;
	private JList<String> matchList;
	private JLabel listLabel;
	private JLabel activeMatchLabel;
	private JLabel team1Label;
	private JLabel team2Label;
	
	private JTextField score1Txt;
	private JTextField score2Txt;
	
	private JButton btnStartMatch;
	private JButton btnGetNextRound;
	private JButton btnUpdateScore;
	
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
		
		listLabel = new JLabel("List of matches:");
		activeMatchLabel = new JLabel("Active match:");
		team1Label = new JLabel("Team 1");
		team2Label = new JLabel("Team 2");
		
		score1Txt = new JTextField(4);
		score1Txt.setHorizontalAlignment(SwingConstants.CENTER);
		score2Txt = new JTextField(4);
		score2Txt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setSize(300, 100);
		
		score1Txt.setEnabled(false);
		score2Txt.setEnabled(false);
		
		scorePanel.add(team1Label);
		scorePanel.add(score1Txt);
		scorePanel.add(score2Txt);
		scorePanel.add(team2Label);
		
		// initialize string list model
		matchModel = new DefaultListModel<String>();
		
		// add list of matches to model
		Queue<Match> matches = bracket.getMatches();
		
		for(Match m : matches)
			matchModel.addElement(m.getTeam1().getName() + " vs. " + m.getTeam2().getName());
		
		// initialize list with model and disable selection
		matchList = new JList<String>(matchModel);
		matchList.setSelectionModel(new NoSelectionModel());
		
		// create scroll pane
		JScrollPane scrollPane = new JScrollPane(matchList);
		Dimension d = matchList.getPreferredSize();
		d.width = 150;
		d.height = 156;
		scrollPane.setPreferredSize(d);
		
		btnStartMatch = new JButton("Start Match");
		
		btnStartMatch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!matchModel.isEmpty())
				{
					activeMatch = bracket.getNextMatch();
					
					team1Label.setText(activeMatch.getTeam1().getName());
					team2Label.setText(activeMatch.getTeam2().getName());
					
					score1Txt.setEnabled(true);
					score2Txt.setEnabled(true);
					
					btnUpdateScore.setEnabled(true);
	
					matchModel.remove(0);
					
					btnStartMatch.setEnabled(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No matches found", "Error", JOptionPane.ERROR_MESSAGE);
					btnStartMatch.setEnabled(false);
				}
			}
		});
		
		btnUpdateScore = new JButton("Finish Match and Update Score");
		btnUpdateScore.setEnabled(false);
		btnUpdateScore.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int score1 = Integer.parseInt(score1Txt.getText());
					int score2 = Integer.parseInt(score2Txt.getText());
					
					activeMatch.finishGame(score1, score2);
					bracket.endMatch(activeMatch);
					
					team1Label.setText("Team 1");
					team2Label.setText("Team 2");
					
					score1Txt.setText("");
					score2Txt.setText("");
					
					score1Txt.setEnabled(false);
					score2Txt.setEnabled(false);
					
					btnStartMatch.setEnabled(true);
					btnUpdateScore.setEnabled(false);
					
					if(matchModel.isEmpty())
						btnGetNextRound.setEnabled(true);
				}
				catch(NumberFormatException numErr)
				{
					JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnGetNextRound = new JButton("Next Round");
		btnGetNextRound.setEnabled(false);
		btnGetNextRound.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Queue<Match> nextRoundMatches = bracket.getMatches();
				
				if(matchList.getModel().getSize() == 0 && !nextRoundMatches.isEmpty())
				{
					for(Match m : nextRoundMatches)
						matchModel.addElement(m.getTeam1().getName() + " vs. " + m.getTeam2().getName());
					btnGetNextRound.setEnabled(false);
					btnStartMatch.setEnabled(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Winner is " + bracket.getChampion().getName() + "!", "Tournament over", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JPanel btnPanel = new JPanel();
		btnPanel.setSize(100,200);
		GridLayout gl = new GridLayout(2,1);
		gl.setVgap(10);
		btnPanel.setLayout(gl);
		btnPanel.add(btnStartMatch);
		btnPanel.add(btnGetNextRound);
		
		// arrange components and add to main panel
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(listLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(scrollPane, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(btnPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(activeMatchLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		mainPanel.add(scorePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		mainPanel.add(btnUpdateScore, gbc);
		
		// set border and add main panel to frame
		mainPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(mainPanel, BorderLayout.NORTH);
	}
	
	/**
	 * This class is used to disable selection in a JList.
	 */
	private static class NoSelectionModel extends DefaultListSelectionModel 
	{
	   @Override
	   public void setAnchorSelectionIndex(final int anchorIndex) {}

	   @Override
	   public void setLeadAnchorNotificationEnabled(final boolean flag) {}

	   @Override
	   public void setLeadSelectionIndex(final int leadIndex) {}

	   @Override
	   public void setSelectionInterval(final int index0, final int index1) { }
	 }
}
