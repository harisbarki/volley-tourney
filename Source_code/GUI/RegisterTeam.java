package GUI;

import java.awt.EventQueue;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
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
import org.xml.sax.SAXException;

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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegisterTeam extends JFrame {

	private JPanel contentPane;
	private JTextField txtTeamName;
	private JTextField txtPlayerName;
	private ArrayList<Player> players;
	private JList<String> lstPlayers;
	private JSpinner selectAge;
	private DefaultListModel<String> model;
	
	private MenuBar menuBar;
	private Tournament tournament;

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
		
		this.tournament = tourney;
		
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

		selectAge = new JSpinner(new SpinnerNumberModel(10,0,100,1));
		selectAge.setSize(40, 25);
		selectAge.setLocation(101, 166);
		contentPane.add(selectAge);
		
		players = new ArrayList<Player>();
		
		lstPlayers = new JList<String>();
		lstPlayers.setBounds(340, 107, 186, 169);
		contentPane.add(lstPlayers);
		model = new DefaultListModel<String>();
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
						if(tournament.addTeam(team)) {
							System.out.print(tournament.getName() + ": " + tournament.showTeams());
							saveTeam(tournament.getName(),team);
							JOptionPane.showMessageDialog(null,teamName+ " has been registered!", "Success!", JOptionPane.INFORMATION_MESSAGE);
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
	
	public void saveTeam(String name, Team t)
	{	
		// get name for retrieving save file
		String fileName = "tournaments/" + name + ".xml";
		
		// xml document for to load file
		Document dom;
				
		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
				
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
					
			// load the xml file
			dom = db.parse(fileName);
			
			// get root element
			Element root = dom.getDocumentElement();
			NodeList teamList = root.getElementsByTagName("teamList");
			
			Element tl = (Element) teamList.item(0);
			Element team = dom.createElement("team");
		
			Element s = dom.createElement("teamName");
			s.appendChild(dom.createTextNode(t.getName()));
			team.appendChild(s);
				

			ArrayList<Player> players = t.getPlayers();
			
			// for each player create node and append name and age for each
			for(int i = 0; i < players.size(); i++)
			{
				Element p = dom.createElement("player");
				Element n = dom.createElement("name");
				Element a = dom.createElement("age");
					
				n.appendChild(dom.createTextNode(players.get(i).getName()));
				a.appendChild(dom.createTextNode("" + players.get(i).getAge()));
				p.appendChild(n);
				p.appendChild(a);
				team.appendChild(p);
			}
			tl.appendChild(team);
			
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
			}
			catch(TransformerException te)
			{
				System.out.println(te.getMessage());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println(pce.getMessage());
		}
		catch(SAXException se)
		{
            System.out.println(se.getMessage());
        }
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}
}
