package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * This class is used to create a tournament bracket
 * based on the tournament type.
 * 
 */
public class Bracket 
{
	private Queue<Match> matches;
	private Stack<Match> playedMatches;
	private int step;
	private int round;
	private LocalDate startDate;
	private LocalDate endDate;
	private Team champion;
	
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
		
		// determine number of rounds to be played
		int rounds = (int) ((int) Math.log(t.getNumTeams()/2) / Math.log(2)) + 1;
		System.out.println("rounds : " + rounds);
		
		// get tournament type
		String type = t.getType();
		System.out.println("type : " + type);
		
		// get tournament dates
		startDate = t.getStartDate();
		endDate = t.getEndDate();
		
		// set the initial round
		round = 1;
		
		int days = endDate.getDayOfYear() - startDate.getDayOfYear(); // determine the num of days in tournament
		step = days / rounds;										  // determine sets of days per round
		LocalDate roundDate = endDate.plusDays(step);				  // create a date for the initial round
		
		// depending on the tournament type create respective bracket
		if(type.equals("Single Elimination"))
			createSEBracket(seededList, roundDate);
		else
			createDivBracket(seededList);
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
	}
	
	/**
	 * This method creates a tournament bracket for 
	 * divisions.
	 * 
	 * @param seededList The seeded list of teams
	 */
	public void createDivBracket(ArrayList<Team> seededList)
	{
		
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
	public Match startMatch()
	{
		Match m = matches.remove();
		
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
		// add finished match to stack
		playedMatches.push(m);
		
		if(playedMatches.size() == 2) // if we have two matches on the stack, then create new match from winners of each match
		{
			Match m1 = playedMatches.pop();
			Match m2 = playedMatches.pop();
			
			Team t1 = m1.getWinner();
			Team t2 = m2.getWinner();
			
			ArrayList<Team> nextRound = new ArrayList<Team>();
			nextRound.add(t1);
			nextRound.add(t2);
			
			// if all matches of initial round are over, then create next round matches
			if(matches.isEmpty())
				createSERound(nextRound);
		}
		else if(matches.isEmpty()) // if only one match on stack and no matches on queue then the tournament is over
		{
			// get the tournament champion
			Match finalMatch = playedMatches.pop();
			champion = finalMatch.getWinner();
		}
		else
		{
			// do nothing
		}
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
}
