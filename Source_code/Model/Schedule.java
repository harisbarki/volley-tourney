package Model;

import java.util.ArrayList;

/*
 * This class generates and stores the brackets and matches for the tournament.
*/
public class Schedule {
	
	private ArrayList<Bracket> brackets;

	public Schedule() {
	
	}

	//Method to return list of brackets
	public ArrayList<Bracket> getBrackets() {
		return brackets;
	}
	
	//Method to return list of all matches
	public ArrayList<Match> getAllMatches() 	{
		ArrayList<Match> matches = new ArrayList<Match>();
		
		for (Bracket b : brackets) {
			for (Match m : b.getMatches()) {
				matches.add(m);
			}
		}
		
		return matches;
	}

	//Method to generate schedule based on the top teams selected and the type of tournament
	public void generateSchedule(ArrayList<Team> teams, ArrayList<Team> topTeams, String type) {
		/*
		 * Some algorithm dependant on topTeams and type 
		 * which splits teams into certain brackets
		 */
	}
}
