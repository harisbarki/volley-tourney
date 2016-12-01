package GUI;
import Model.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;

public class EditTeams extends JPanel {
	
	private JTextField txtTeamName;
	private JTextField txtPlayerName;
	private ArrayList<Player> players;
	private JList<String> lstPlayers;
	private JSpinner selectAge;
	private DefaultListModel<String> model;
	
	private Tournament tournament;
	private Team team;

	private final int WIDTH  = 550;
	private final int HEIGHT = 430;
	
	/**
	 * Create the frame.
	 */
	public EditTeams(Tournament tourney, Team team) {

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);	
		
		this.tournament = tourney;
		this.team = team;
		tournament.removeTeam(team);
		

		txtTeamName = new JTextField(team.getName());
		txtTeamName.setBounds(120, 27, 313, 19);
		add(txtTeamName);
		txtTeamName.setColumns(10);

		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setBounds(14, 30, 150, 15);
		add(lblTeamName);

		JLabel lblPlayerDetails = new JLabel("Player Details");
		lblPlayerDetails.setBounds(14, 107, 132, 15);
		add(lblPlayerDetails);

		txtPlayerName = new JTextField();
		txtPlayerName.setBounds(101, 133, 114, 19);
		add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblPlayerName = new JLabel("Name:");
		lblPlayerName.setBounds(14, 136, 70, 15);
		add(lblPlayerName);

		JLabel lblPlayerAge = new JLabel("Age:");
		lblPlayerAge.setBounds(14, 166, 70, 15);
		add(lblPlayerAge);

		selectAge = new JSpinner(new SpinnerNumberModel(10,0,100,1));
		selectAge.setSize(40, 25);
		selectAge.setLocation(101, 166);
		add(selectAge);
		
		players = new ArrayList<Player>();
		model = new DefaultListModel<String>();
		
		for (Player p : team.getPlayers()) {
			model.addElement(p.getName() + " : " + p.getAge());
			players.add(p);
		}
		
		JButton btnAddPlayer = new JButton("Add Player");
		btnAddPlayer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String name;
				int age;
				try {
					age = (int) selectAge.getValue();
					name  = txtPlayerName.getText();
					
					if(name.equals("") || !name.matches("[a-zA-Z\\s]+") ) {
						throw new NullPointerException();
					}
					else {
						Player p = new 	Player(name, age);
						players.add(p);
						model.addElement(name + " : " + age);
					}
				}
				catch (NullPointerException ex) { // validate input
					JOptionPane.showMessageDialog(null, "Please enter a valid name for each player", "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally {
					txtPlayerName.setText("");
					selectAge.setValue(10);					
				}
			}
		});
		btnAddPlayer.setBounds(101, 200, 114, 23);
		add(btnAddPlayer);
		
		JButton btnEditTeam = new JButton("Edit Team");
		btnEditTeam.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String teamName;
				try {
					teamName = txtTeamName.getText();
					if (teamName.equals(""))     throw new NullPointerException();
					if (players.size() < tournament.minimumTeamSize()) throw new IllegalStateException();
					else {
						Team team = new Team(teamName);
						for (Player p : players) team.addPlayer(p);
						if(tournament.addTeam(team)) {
							System.out.print(tournament.getName() + ": " + tournament.showTeams());
							
							//Edit team in XML????
							
							JOptionPane.showMessageDialog(null,teamName+ " has been registered!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Tournament is already full!", "Error", JOptionPane.ERROR_MESSAGE);
						}							
					}	
				}
				catch(IllegalStateException i) {
					JOptionPane.showMessageDialog(null, "Please add more players", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Please set a name for the team", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditTeam.setBounds(36, 251, 150, 23);
		add(btnEditTeam);
		
		JButton btnRemovePlayer = new JButton("Remove Player");
		btnRemovePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String player;
				try {
					if(lstPlayers.getModel().getSize() == 0) throw new IndexOutOfBoundsException();
					player = lstPlayers.getSelectedValue();
					player = player.substring(0, player.indexOf(':') - 1);
					for (Player p : players) 
						if (p.getName() == player) players.remove(p);
					model.remove(lstPlayers.getSelectedIndex());					
				}
				catch(IndexOutOfBoundsException n) { // handle empty list
					JOptionPane.showMessageDialog(null, "There are no players to remove", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException n) { // handle no player selected
					JOptionPane.showMessageDialog(null, "Please select a player to remove", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRemovePlayer.setBounds(235, 251, 186, 23);
		add(btnRemovePlayer);
		
		JLabel lblTournament = new JLabel("Tournament:");
		lblTournament.setBounds(14, 62, 150, 14);
		add(lblTournament);

		JLabel tmntLbl = new JLabel(tournament.getName());
//		JLabel tmntLbl = new JLabel("a very tourney demo");
		tmntLbl.setBounds(120, 62, 500, 15);
		add(tmntLbl);
		
		JScrollPane sp = new JScrollPane();
		lstPlayers = new JList<String>();
		lstPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstPlayers.setModel(model);
		sp.setViewportView(lstPlayers);
		add(sp);
		sp.setBounds(235, 61, 186, 169);
	
	}
}
