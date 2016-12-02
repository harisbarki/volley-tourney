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
import java.io.FileOutputStream;
import java.io.IOException;
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
	private Team oldTeam;

	private final int WIDTH  = 750;
	private final int HEIGHT = 450;
	
	/**
	 * Create the frame.
	 */
	public EditTeams(Tournament tourney, Team team) {

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);	
		
		this.tournament = tourney;
		this.oldTeam = team;
		

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
		
		JButton btnEditTeam = new JButton("Save");
		btnEditTeam.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String teamName;
				try {
					teamName = txtTeamName.getText();
					if (teamName.equals(""))     throw new NullPointerException();
					if (players.size() < tournament.minimumTeamSize()) throw new IllegalStateException();
					else {
						Team newTeam = new Team(teamName);
						for (Player p : players) newTeam.addPlayer(p);
						if(tournament.addTeam(newTeam)) {
							System.out.print(tournament.getName() + ": " + tournament.showTeams());
							
							tournament.removeTeam(oldTeam);
							deleteTeam(tournament.getName(), oldTeam);
							saveTeam(tournament.getName(), newTeam);
							
							JOptionPane.showMessageDialog(null,teamName+ " has been saved!", "Success!", JOptionPane.INFORMATION_MESSAGE);
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
		btnRemovePlayer.setBounds(300, 251, 186, 23);
		add(btnRemovePlayer);
		
		JLabel lblTournament = new JLabel("Tournament:");
		lblTournament.setBounds(14, 62, 150, 14);
		add(lblTournament);

		JLabel tmntLbl = new JLabel(tournament.getName());
		tmntLbl.setBounds(120, 62, 500, 15);
		add(tmntLbl);
		
		JScrollPane sp = new JScrollPane();
		lstPlayers = new JList<String>();
		lstPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstPlayers.setModel(model);
		sp.setViewportView(lstPlayers);
		add(sp);
		sp.setBounds(300, 61, 186, 169);
	
	}
	
	public void deleteTeam(String name, Team t) {
		
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
