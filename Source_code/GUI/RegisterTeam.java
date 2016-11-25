package GUI;

import java.awt.EventQueue;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

public class RegisterTeam extends JFrame {

	private JPanel contentPane;
	private JTextField txtTeamName;
	private JTextField txtPlayerName;
	private ArrayList<Player> players;
	
	private MenuBar menuBar;
//	private Tournament tournament;

	private final int WIDTH  = 550;
	private final int HEIGHT = 430;
	
	/**
	 * Create the frame.
	 */
	public RegisterTeam(Tournament tourney) {
//	public RegisterTeam() {
		setTitle("Register Team");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);		
		
		// Getting the Top Menu and initializing it in this pane
		TopMenu tm = new TopMenu(this);
		menuBar = tm.getMenuBar();
		setMenuBar(menuBar);
		tm.initializeMenuBar(menuBar);

		txtTeamName = new JTextField();
		txtTeamName.setBounds(120, 27, 313, 19);
		contentPane.add(txtTeamName);
		txtTeamName.setColumns(10);

		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setBounds(14, 30, 150, 15);
		contentPane.add(lblTeamName);

		JLabel lblPlayerDetails = new JLabel("Player Details");
		lblPlayerDetails.setBounds(14, 107, 132, 15);
		contentPane.add(lblPlayerDetails);

		txtPlayerName = new JTextField();
		txtPlayerName.setBounds(101, 133, 114, 19);
		contentPane.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblPlayerName = new JLabel("Name:");
		lblPlayerName.setBounds(14, 136, 70, 15);
		contentPane.add(lblPlayerName);

		JLabel lblPlayerAge = new JLabel("Age:");
		lblPlayerAge.setBounds(14, 166, 70, 15);
		contentPane.add(lblPlayerAge);

		JSpinner selectAge = new JSpinner(new SpinnerNumberModel(10,0,100,1));
		selectAge.setSize(40, 25);
		selectAge.setLocation(101, 166);
		contentPane.add(selectAge);
		
		players = new ArrayList<Player>();
		
		JList<String> lstPlayers = new JList<String>();
		lstPlayers.setBounds(340, 107, 186, 169);
		contentPane.add(lstPlayers);
		DefaultListModel<String> model = new DefaultListModel<String>();
		lstPlayers.setModel(model);
		
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
		contentPane.add(btnAddPlayer);
		
		JButton btnRegisterTeam = new JButton("Register Team");
		btnRegisterTeam.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String teamName;
				try {
					teamName = txtTeamName.getText();
					
					if (teamName.equals(""))     throw new NullPointerException();
					if (players.size() == 0)     throw new IllegalStateException();
					else {
						Team team = new Team(teamName);
						for (Player p : players) team.addPlayer(p);
						if(tourney.addTeam(team)) {
							System.out.print(tourney.getName() + ": " + tourney.showTeams());
							JOptionPane.showMessageDialog(null, teamName+" has been registered!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Please add more players to the team", "Not enough players", JOptionPane.ERROR_MESSAGE);
						}
												
					}	
				}
				catch(IllegalStateException i) {
					JOptionPane.showMessageDialog(null, "Please add players first", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Please set a name for the team", "Error", JOptionPane.ERROR_MESSAGE);
				}
				txtTeamName.setText("");
				model.removeAllElements();
			}
		});
		btnRegisterTeam.setBounds(14, 350, 150, 23);
		contentPane.add(btnRegisterTeam);
		
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
		btnRemovePlayer.setBounds(340, 301, 186, 23);
		contentPane.add(btnRemovePlayer);
		
		JLabel lblTournament = new JLabel("Tournament:");
		lblTournament.setBounds(14, 62, 150, 14);
		contentPane.add(lblTournament);

		JLabel tmntLbl = new JLabel(tourney.getName());
//		JLabel tmntLbl = new JLabel("a very tourney demo");
		tmntLbl.setBounds(120, 62, 500, 15);
		contentPane.add(tmntLbl);
	
	}
	
//	public static void main(String[] args)
//	{
//		RegisterTeam rt = new RegisterTeam();
//		rt.setVisible(true);
//	}
}
