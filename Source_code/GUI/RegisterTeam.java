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
	private JTextField txtPlayerAge;
	private ArrayList<Player> players;
	
	private MenuBar menuBar;
	private Tournament tournament;

	private final int WIDTH  = 550;
	private final int HEIGHT = 400;
	
	/**
	 * Create the frame.
	 */
//	public RegisterTeam(Tournament tourney) {
	public RegisterTeam() {
		setTitle("Register team");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 454, 374);
		setSize(WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
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
		
//		txtPlayerAge = new JTextField();
//		txtPlayerAge.setBounds(101, 163, 114, 19);
//		contentPane.add(txtPlayerAge);
//		txtPlayerAge.setColumns(10);

//		JComboBox cmbTournament = new JComboBox();
//		cmbTournament.setBounds(101, 57, 313, 24);		
//		contentPane.add(cmbTournament);
//		
//		addWindowListener(new WindowAdapter() {
//			public void windowOpened(WindowEvent arg0) {
//				//ADD TOURNAMENTS TO COMBO BOX
//				players = new ArrayList<Player>();
//			}
//		});
		
		JButton btnRegisterTeam = new JButton("Register Team");
		btnRegisterTeam.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				
				String name = txtTeamName.getText();
				Team team = new Team(name);
				
				for (Player p : players) {
					team.addPlayer(p);
				}
//				String tournamentName = cmbTournament.getSelectedItem().toString();
				
				//Loop through list of tournaments to find correct one
				//Try to add team to tournament 
				
			}
		});
		btnRegisterTeam.setBounds(14, 301, 150, 23);
		contentPane.add(btnRegisterTeam);
		
		JList<String> lstPlayers = new JList<String>();
		lstPlayers.setBounds(228, 107, 186, 169);
		contentPane.add(lstPlayers);
		DefaultListModel<String> model = new DefaultListModel<String>();
		lstPlayers.setModel(model);
		
		JButton btnAddPlayer = new JButton("Add Player");
		btnAddPlayer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String name = txtPlayerName.getText();
				int age = 0;
				try {
					age = Integer.parseInt(txtPlayerAge.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter valid player information", "Error", JOptionPane.ERROR_MESSAGE);
					txtPlayerName.setText("");
					txtPlayerAge.setText("");
					return;
				}
				Player p = new 	Player(name, age);
				players.add(p);
				model.addElement(name + " : " + age);
				txtPlayerName.setText("");
				txtPlayerAge.setText("");
			}
		});
		btnAddPlayer.setBounds(101, 200, 114, 23);
		contentPane.add(btnAddPlayer);
		
		JButton btnRemovePlayer = new JButton("Remove Player");
		btnRemovePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String name = lstPlayers.getSelectedValue();
				name = name.substring(0, name.indexOf(':') - 1);
				
				for (Player p : players) 
					if (p.getName() == name)
						players.remove(p);
				
				
				model.remove(lstPlayers.getSelectedIndex());
				
			}
		});
		btnRemovePlayer.setBounds(228, 301, 186, 23);
		contentPane.add(btnRemovePlayer);
		
		JLabel lblTournament = new JLabel("Tournament:");
		lblTournament.setBounds(14, 62, 150, 14);
		contentPane.add(lblTournament);

//		JLabel tournamentLbl = new JLabel(tourney.getName());
		JLabel tmntLbl = new JLabel("a tourney demo");
		tmntLbl.setBounds(120, 62, 500, 15);
		contentPane.add(tmntLbl);
	
	}
	
	public static void main(String[] args)
	{
		RegisterTeam rt = new RegisterTeam();
		rt.setVisible(true);
	}
}
