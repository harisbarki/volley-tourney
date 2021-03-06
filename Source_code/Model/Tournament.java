package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * This class stores info for each tournament
 * 
*/
public class Tournament {
	private int id;
	private ArrayList<Team> teams;
	private ArrayList<Team> seeding;
	private String name;
	private String type;
	private LocalDate tournamentStart;
	private LocalDate tournamentEnd;
	private LocalDate registrationStart;
	private LocalDate registrationEnd;
	private int minPlayerAge;
	private int maxPlayerAge;
	private int numTeams;
	private int minimumTeamSize;
	private static int counter = 1; // To get how many classes are instantiated
	private Bracket bracket;

	/**
	 * constructor for Tournament Class
	 * @param name
	 * @param type
	 * @param tournamentStart
	 * @param tournamentEnd
	 * @param registrationStart
	 * @param registrationEnd
	 * @param minPlayerAge
	 * @param maxPlayerAge
	 * @param numTeams
	 */
	public Tournament(String name, String type, LocalDate tournamentStart, LocalDate tournamentEnd,
			LocalDate registrationStart, LocalDate registrationEnd, int minPlayerAge, int maxPlayerAge, int numTeams) {
		this.id = counter;
		counter++;
		this.name = name;
		this.type = type;
		this.tournamentStart = tournamentStart;
		this.tournamentEnd = tournamentEnd;
		this.registrationStart = registrationStart;
		this.registrationEnd = registrationEnd;
		this.minPlayerAge = minPlayerAge;
		this.maxPlayerAge = maxPlayerAge;
		this.numTeams = numTeams;

		this.minimumTeamSize = 6;

		bracket = null;

		teams = new ArrayList<Team>();
		seeding = new ArrayList<Team>();
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return minimum team size
	 */
	public int minimumTeamSize() {
		return minimumTeamSize;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return start date
	 */
	public LocalDate getStartDate() {
		return tournamentStart;
	}

	/**
	 * 
	 * @return end date
	 */
	public LocalDate getEndDate() {
		return tournamentEnd;
	}

	/**
	 * @return Registration start date
	 */
	public LocalDate getRegStartDate() {
		return registrationStart;
	}

	/**
	 * @return Registration end date
	 */
	public LocalDate getRegEndDate() {
		return registrationEnd;
	}

	/**
	 * @return Min age required
	 */
	public int getMinAge() {
		return minPlayerAge;
	}

	/**
	 * @return Max age required
	 */
	public int getMaxAge() {
		return maxPlayerAge;
	}

	/**
	 * @return get number of teams
	 */
	public int getNumTeams() {
		return numTeams;
	}

	/**
	 * Sets new id for tournament
	 * @param newID
	 */
	public void setID(int newID) {
		id = newID;
	}

	/**
	 * returns whether a the registration is still in progress
	 * 
	 * @return registration still in progress flag
	 */
	public boolean canRegister() {
		if (LocalDate.now().isAfter(registrationStart) && LocalDate.now().isBefore(registrationEnd))
			return true;
		else if (inProgress())
			return false;
		else
			return false;
	}

	/**
	 * returns whether the tournament is in progress
	 * 
	 * @return flag of tournament is in progress
	 */
	public boolean inProgress() {
		if (LocalDate.now().isAfter(tournamentStart) && LocalDate.now().isBefore(tournamentEnd))
			return true;
		else
			return false;
	}

	/**
	 * method to add and check validity of team to the tournament
	 * 
	 * @param team
	 * @return
	 */
	public boolean addTeam(Team team) {
		if (team.size() < minimumTeamSize) {
			System.out.println("too small"); // debug
			return false;
		}
		teams.add(team);

		return true;
	}

	/**
	 * Method to add and check validity of team to the tournament
	 * 
	 * @param team
	 */
	public void addRank(Team team) {
		if (teams.contains(team)) {
			seeding.add(team);
			if (seeding.contains(team))
				team.setSeedingPos(seeding.indexOf(team));
		}
	}

	/**
	 * Removes the rank of the team
	 * 
	 * @param team
	 */
	public void removeRank(Team team) {
		if (seeding.contains(team)) {
			seeding.remove(team);
			team.setSeedingPos(-1);
		}
	}

	/**
	 * method to remove team from tournament
	 * 
	 * @param team
	 * @return flag of successful or not
	 */
	public boolean removeTeam(Team team) {
		if (teams.contains(team)) {
			teams.remove(team);
			return true;
		}
		return false;
	}

	/**
	 * display registered teams in the tournament
	 * 
	 * @return string of teams
	 */
	public String showTeams() {
		return teams.toString();
	}

	/**
	 * display top ranked teams in the tournament
	 * 
	 * @return
	 */
	public ArrayList<Team> getSeededTeams() {
		return seeding;
	}

	/**
	 * Set Seeded Teams
	 * 
	 * @param s
	 */
	public void setSeededTeams(ArrayList<Team> s) {
		seeding = s;
	}

	/**
	 * Gets the list of teams in the tournament
	 * 
	 * @return List of teams
	 */
	public ArrayList<Team> getTeams() {
		String line = null;
		String tournamentFile = ("tournaments/" + getId() + ".xml");
		String file = "tournaments/files.txt";

		BufferedReader br;

		try {
			// open file for reading
			FileInputStream fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));

			// attempt to read from file
			while ((line = br.readLine()) != null) {
				// skip blank line
				if (line == "")
					continue;

				// get teams from file
				teams = getTeamFrom(tournamentFile);
			}
			br.close();
			return teams;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return teams;
		}
	}

	/**
	 * gets the teams saved on the specific tournament file
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Team> getTeamFrom(String name) {
		// tournament object to return
		Team t = null;
		Player p = null;
		ArrayList<Team> tempTeam = new ArrayList<Team>();

		// variables to store data from file
		String teamName = null;
		String playerName = null;
		int playerAge = 0;

		// xml document for to load file
		Document dom;

		// for building document builder
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// load the xml file
			dom = db.parse(name);

			// get root element
			NodeList child = dom.getElementsByTagName("team");

			for (int i = 0; i < child.getLength(); i++) {
				Node temp = child.item(i);
				NodeList childList = child.item(i).getChildNodes();

				// retrieves all the team names
				if (temp.getNodeType() == Node.ELEMENT_NODE) {
					Element teamRoot = (Element) temp;
					teamName = getTextValue(child, teamRoot, "teamName");
					t = new Team(teamName);
				}

				for (int k = 0; k < childList.getLength(); k++) {
					Node temp2 = childList.item(k);

					if ("player".equals(temp2.getNodeName())) {
						Element playerRoot = (Element) temp2;
						playerName = getTextValue(childList, playerRoot, "name");
						playerAge = Integer.parseInt(getTextValue(childList, playerRoot, "age"));
						p = new Player(playerName, playerAge);
						t.addPlayer(p);
					}

				}

				tempTeam.add(t);
			}

			return tempTeam;

		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
			return tempTeam;
		} catch (SAXException se) {
			System.out.println(se.getMessage());
			return tempTeam;
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			return tempTeam;
		}
	}

	/**
	 * This method retrieves the text node at the specified child node.
	 * 
	 * @param doc
	 *            The parent node
	 * @param tag
	 *            The child node
	 * @return The text node value of the child node
	 */
	public String getTextValue(NodeList node, Element root, String child) {
		String value = "";

		node = root.getElementsByTagName(child);

		if (node.getLength() > 0 && node.item(0).hasChildNodes()) {
			value = node.item(0).getFirstChild().getNodeValue();
		}

		return value;
	}

	/**
	 * gets a team object from a given team name.
	 * 
	 * @param name
	 * @return Team A
	 */
	public Team getATeam(String name) {
		Team selectedTeam = null;
		for (Team x : teams)
			if (x.getName() == name)
				selectedTeam = x;
		return selectedTeam;

	}

	/**
	 * 
	 * @return Bracket
	 */
	public Bracket getBracket() {
		return bracket;
	}

	/**
	 * Sets the bracket
	 * 
	 * @param b
	 */
	public void setBracket(Bracket b) {
		bracket = b;
	}

}
