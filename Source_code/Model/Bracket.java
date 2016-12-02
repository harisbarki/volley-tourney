package Model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class is used to create a tournament bracket
 * based on the tournament type.
 * 
 */
public class Bracket 
{
	private Queue<Match> matches;
	private int step;
	private int round;
	private LocalDate startDate;
	private LocalDate endDate;
	private Team champion;
	private ArrayList<Team> nextRound;
	private String type;
	private final String matchesFile = "tournaments/matches.txt";
	
	/**
	 * This initialization constructor is used to set up the initial
	 * round of the tournament based on the type.
	 * 
	 * @param t The tournament
	 * @param seededList The seeded list of teams
	 */
	public Bracket(Tournament t, ArrayList<Team> seededList)
	{
		matches = new LinkedList<Match>();
		nextRound = new ArrayList<Team>();

		// determine number of rounds to be played
		int rounds = (int) ((int) Math.log(t.getNumTeams()/2) / Math.log(2)) + 1;
		
		// get tournament type
		type = t.getType();
		
		// get tournament dates
		startDate = t.getStartDate();
		endDate = t.getEndDate();
		
		// set the initial round
		round = 1;
		
		int days = endDate.getDayOfYear() - startDate.getDayOfYear(); // determine the num of days in tournament
		step = days / rounds;										  // determine sets of days per round
		LocalDate roundDate = startDate.plusDays(step);				  // create a date for the initial round
		
		// depending on the tournament type create respective bracket
		if(type.equals("Single Elimination"))
			createSEBracket(seededList, roundDate);
		else
			createDivBracket(seededList, roundDate);
	}
	
	/**
	 * This method creates the initial round for a
	 * single elimination tournament.
	 * 
	 * @param seededList The seeded list of teams
	 */
	public void createSEBracket(ArrayList<Team> seededList, LocalDate roundDate)
	{
		// get number of teams
		int num = seededList.size();
		
		// create matches based on seedings
		for(int i = 0; i < num/2; i++)
		{
			Team t1 = seededList.get(i);
			Team t2 = seededList.get((num-1)-i);
			
			Match m = new Match(t1,t2,startDate);
			saveMatch(m);
			matches.add(m);
			
			if(i % 2 == 0) // set two matches on same date until you are past the round date
			{
				continue;	// do nothing
			}
			else if(startDate.isBefore(roundDate))
			{
				// increment the date by one day
				startDate.plusDays(1);
			}
			else // if past or reached the round date then all remaining matches are to be set on this date
			{
				startDate = roundDate;
			}
		}
	}
	
	/**
	 * This method sets up the matches for the next round
	 * of the single elimination tournament.
	 * 
	 * @param teamList The list of teams to be matched up
	 */
	public void createSERound(ArrayList<Team> teamList)
	{	
		LocalDate roundDate = startDate.plusDays(step);
		round++;
		
		// if the round date is past the tournament end date then set it to the end date
		if(roundDate.isAfter(endDate))
			roundDate = endDate;
		
		// create matches from consecutive teams in the list
		for(int i = 0; i < teamList.size(); i+=2)
		{
			Team t1 = teamList.get(i);
			Team t2 = teamList.get(i+1);
			
			Match m = new Match(t1,t2,startDate);
			m.setRound(round);
			matches.add(m);
			
			if(i % 2 == 0) // set two matches on same date until you are past the round date
			{
				// do nothing
			}
			else if(startDate.isBefore(roundDate))
			{
				// increment the date by one day
				startDate.plusDays(1);
			}
			else // if past or reached the round date then all remaining matches are to be set on this date
			{
				startDate = roundDate;
			}
		}
		nextRound = new ArrayList<Team>();
	}
	
	/**
	 * This method creates a tournament bracket for 
	 * divisions.
	 * 
	 * @param seededList The seeded list of teams
	 */
	public void createDivBracket(ArrayList<Team> seededList, LocalDate rd)
	{
		for(Team t1 : seededList)
		{
			for(int i=0; i<seededList.size(); i++)
			{
				Team t2 = seededList.get(i);
				if(t1.equals(t2)) 						    continue;
				else if(alreadySet(new Match(t1, t2, rd)))  continue;
				else 									    matches.add(new Match(t1, t2, rd));
			}	
		}
	}
	
	/**
	 * 
	 */
	private void createDivRound(ArrayList<Team> teamList) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param  m 	the match to look up
	 * @return true if the match has previously been set between two teams
	 */
	public boolean alreadySet(Match m)
	{
		for(Match temp : matches)
			if(m.equals(temp))	return true;
		
		return false;
	} 
	
	/**
	 * This method returns the matches to be played.
	 * 
	 * @return the scheduled matches
	 */
	public Queue<Match> getMatches()
	{
		return matches;
	}
	
	/**
	 * This method is used to retrieve the next match
	 * to be played.
	 * 
	 * @return The next scheduled match
	 */
	public Match getNextMatch()
	{
		Match m = null;
		if(!matches.isEmpty())
			m = matches.remove();
		else
			return m;
		
		return m;
	}
	
	/**
	 * This method is called when a match is over,
	 * and the final score has been entered.
	 * 
	 * @param m The match that has ended
	 */
	public void endMatch(Match m)
	{
		// save match first
		saveMatch(m);
		
		Team t = m.getWinner();
		nextRound.add(t);
		
		if(matches.isEmpty() && nextRound.size() > 1)
		{
			if(type.equals("Single Elimination"))
					createSERound(nextRound);
			else	createDivRound(nextRound);
		}
		else
			champion = nextRound.get(0);
	}

	/**
	 * This method returns the winner of the tournament.
	 * 
	 * @return The tournament champion
	 */
	public Team getChampion()
	{
		return champion;
	}
	
	public void saveMatch(Match m)
	{
		Document dom;
		Element e = null;
		Element team1 = null;
		Element team2 = null;
		Element name1 = null;
		Element name2 = null;
		Element score1 = null;
		Element score2 = null;
		
		String fileName = m.getTeam1().getName() + "-" + m.getTeam2().getName();

		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			
			// create root node
			Element root = dom.createElement("match");
			
			// create and append child nodes to the root node
			e = dom.createElement("teamList");
			
			team1 = dom.createElement("team");
			
			// get name of team 1
			name1 = dom.createElement("name");
			name1.appendChild(dom.createTextNode(m.getTeam1().getName()));
			team1.appendChild(name1);
			
			// get score of team 1
			score1 = dom.createElement("score");
			score1.appendChild(dom.createTextNode(m.getScore1() + ""));
			team1.appendChild(score1);
			
			// append team 1 to team list
			e.appendChild(team1);
			
			team2 = dom.createElement("team");
			
			// get name of team 2
			name2 = dom.createElement("name");
			name2.appendChild(dom.createTextNode(m.getTeam2().getName()));
			team2.appendChild(name2);
						
			// get score of team 2
			score2 = dom.createElement("score");
			score2.appendChild(dom.createTextNode(m.getScore2() + ""));
			team2.appendChild(score2);
						
			// append team 2 to team list
			e.appendChild(team2);
			root.appendChild(e);
			
			// get location
			e = dom.createElement("location");
			e.appendChild(dom.createTextNode(m.getLocation()));
			root.appendChild(e);
			
			// get schedule
			e = dom.createElement("schedule");
			e.appendChild(dom.createTextNode(m.getSchedule().toString()));
			root.appendChild(e);
			
			// get finished
			e = dom.createElement("finished");
			if(m.isFinished())
			{
				e.appendChild(dom.createTextNode("true"));
				root.appendChild(e);
				
				// get winner
				e = dom.createElement("winner");
				e.appendChild(dom.createTextNode(m.getWinner().getName()));
				root.appendChild(e);
			}
			else
			{
				e.appendChild(dom.createTextNode("false"));
				root.appendChild(e);
				
				// no winner yet
				e = dom.createElement("winner");
				e.appendChild(dom.createTextNode("N/A"));
				root.appendChild(e);
			}
			
			// get round
			e = dom.createElement("round");
			e.appendChild(dom.createTextNode(m.getRound() + ""));
			root.appendChild(e);
			
			// append root node to document
			dom.appendChild(root);
			
			try
			{
				// add properties to file and format it
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "tournament.dtd");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				
				// configure save path of current file
				fileName = "tournaments/" + fileName + ".xml";
				
				// save name of each file created in a certain text file for reading later
				BufferedWriter bw = null;
				try
				{
					// set writer to append names to file
					bw = new BufferedWriter(new FileWriter(matchesFile, true));
					bw.write(fileName);	// append current file name
					bw.newLine();		// create new line for next entry
					bw.flush();			// write to file
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				finally		// close file after done writing
				{
					if(bw != null)
					{
						try
						{
							bw.close();
						}
						catch(IOException ioe2)
						{
							ioe2.printStackTrace();
						}
					}
				}
				
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
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
	}
}
