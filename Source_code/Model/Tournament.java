package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class Tournament 
{
	private int id;
	private List<Team> teams;
	private List<Team> seeding;
	private Schedule schedule;
	private String name;
	private String type;
	private LocalDate tournamentStart;
	private LocalDate tournamentEnd;
	private LocalDate registrationStart;
	private LocalDate registrationEnd;
	private int minPlayerAge;
	private int maxPlayerAge;
	private int numTeams;
	private final int minimumTeamSize = 6;
    private static int counter = 1;	// To get how many classes are instantiated

	// constructor creates a tournament	
	public Tournament(String name, String type, LocalDate tournamentStart, LocalDate tournamentEnd, LocalDate registrationStart, LocalDate registrationEnd, int minPlayerAge, int maxPlayerAge, int numTeams) 
	{
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

		teams = new ArrayList<Team>();
		seeding = new ArrayList<Team>();
	}
	
	// accessors
	public int getId(){
		return id;
	}
	
	public String getName()
	{	
		return name;
	}
	
	public String getType()
	{	
		return type;
	}
	
	public LocalDate getStartDate()
	{	
		return tournamentStart;	
	}
	
	public LocalDate getEndDate()
	{
		return tournamentEnd;
	}
	
	public LocalDate getRegStartDate()
	{
		return registrationStart;
	}
	
	public LocalDate getRegEndDate()
	{
		return registrationEnd;
	}
	
	public int getMinAge()
	{
		return minPlayerAge;
	}
	
	public int getMaxAge()
	{
		return maxPlayerAge;
	}
	
	public int getNumTeams()
	{
		return numTeams;
	}
	
	// mutator
	public void setID(int newID)
	{
		id = newID;
	}
	
	// returns whether a the registration is still in progress
	public boolean canRegister()
	{
		if (LocalDate.now().isAfter(registrationStart) && LocalDate.now().isBefore(registrationEnd))
			return true;
		else
			return false;
	}
	
	// returns whether the tournament is in progress
	public boolean inProgress()
	{
		if (LocalDate.now().isAfter(tournamentStart) && LocalDate.now().isBefore(tournamentEnd))
			return true;
		else 
			return false;
	}
	
	// method to add and check validity of team to the tournament
	public boolean addTeam(Team team)
	{
		if (team.size() < minimumTeamSize) 
			return false;
		
		for (Player p : team.getPlayers()) 
			if (p.getAge() < minPlayerAge && p.getAge() > maxPlayerAge) 
				return false;
			else
				teams.add(team);
		
		return true;
	}
	
//	public Team getATeam(String name) {
//		int pos = 0;
//		for (int i = 0; i < teams.size(); i++) 
//		{
//			if (teams.get(i).getName() == name) 
//				pos = i;
//			System.out.println(teams.get(i).getName());
//
//		}
//		return teams.get(pos);
//		
//	}
	
	// method to add and check validity of team to the tournament
	public void addRank(Team team)
	{
		if (teams.contains(team))
		{
			seeding.add(team);
			if (seeding.contains(team)) team.setSeedingPos(seeding.indexOf(team));
		}
	}
	
	public void removeRank(Team team)
	{
		if (seeding.contains(team)) 
		{
			seeding.remove(team);
			team.setSeedingPos(-1);
		}
	}
	
//	// method to add and check validity of team to the tournament
//		public void addTeam(Team team)
//		{
//			if (team.size() < minimumTeamSize) 
//				System.out.println("Team size is lower than minimum limit");
////				return false;
//			
//			for (Player p : team.getPlayers()) 
//				if (p.getAge() < minPlayerAge && p.getAge() > maxPlayerAge) 
//					System.out.println(p.getName() + " is younger than the age limit");
////					return false;
//			
//			teams.add(team);
////			return true;
//		}
	
	// method to remove team from tournament
	public boolean removeTeam(Team team)
	{
		if (teams.contains(team)) {
			teams.remove(team);
			return true;
		}
		return false;
	}
	
	// to create the schedule after registration has ended 
	public void createSchedule()
	{
		schedule = new Schedule();
		schedule.generateSchedule(teams, seeding, type);
	}
	
	// display registered teams in the tournament
	public String showTeams()
	{
		return teams.toString();
	}
	
	// display top ranked teams in the tournament
	public List<Team> showTopTeams()
	{
		return seeding;
	}
	
	//gets the list of teams in the tournament
	public List<Team> getTeams()
	{	
		String line = null;
		String tournamentFile = ("tournaments/" + getName() +".xml" );
		String file = "tournaments/files.txt";
		
		BufferedReader br;
		
		try
		{
			// open file for reading
			FileInputStream fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
			
			
			// attempt to read from file
			while((line = br.readLine()) != null)
			{
				// skip blank line
				if(line == "") continue;
				

				// get teams from file
				teams = getTeamFrom(tournamentFile);
			}
			br.close();
			return teams;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return teams;
		}
	}
		
	//gets the teams saved on the specific tournament file
		public List<Team> getTeamFrom(String name)
		{
			// tournament object to return
			Team t = null;
			ArrayList<Team> tempTeam = new ArrayList<Team>();
			
			// variables to store data from file
			String teamName = null;
			
			// xml document for to load file
			Document dom;
			
			// for building document builder
			DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
			
			try
			{
				// create document using document builder
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				// load the xml file
				dom = db.parse(name);
							
				// get root element
				NodeList child = dom.getElementsByTagName("team");
				
				for (int i = 0; i < child.getLength(); i++)
				{
					
				Node temp = child.item(i);
				
				//	retrieves all the team names
				if (temp.getNodeType() == Node.ELEMENT_NODE)
					{
				Element newRoot = (Element) temp;
				teamName = getTextValue(child, newRoot, "teamName");
				t = new Team(teamName);
				tempTeam.add(t);
					}
				}
				return tempTeam;
				
				
			}
			catch(ParserConfigurationException pce)
			{
	            System.out.println(pce.getMessage());
	            return tempTeam;
	        }
			catch(SAXException se)
			{
	            System.out.println(se.getMessage());
	            return tempTeam;
	        }
			catch(IOException ioe)
			{
	            System.err.println(ioe.getMessage());
	            return tempTeam;
	        }
		}
		
		/**
		 * This method retrieves the text node at the 
		 * specified child node.
		 * 
		 * @param doc The parent node
		 * @param tag The child node
		 * @return The text node value of the child node
		 */
		
		public String getTextValue(NodeList node, Element root, String child)
		{
		    String value = "";
		    
		    node = root.getElementsByTagName(child);
		    
		    if (node.getLength() > 0 && node.item(0).hasChildNodes())
		    {
		        value = node.item(0).getFirstChild().getNodeValue();
		    }
		    
		    return value;
		}
		
		//gets a team object from a given team name.
		public Team getATeam(String name) {		
			Team selectedTeam = null;
			for (Team x : teams)
				if (x.getName() == name) selectedTeam = x;
			return selectedTeam;
			
		}
		
		
		
	
}
