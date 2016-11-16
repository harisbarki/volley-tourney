import java.util.List;

/*
 * This class stores the properties of a bracket in the tournament
 * 
 * No functionalities??
 */
public class Bracket {

	private String name;
	private List<Match> matches;

	public Bracket(String name) {
		this.name = name;
	}
	
	// getter for name of bracket
	public String getName() {
		return name;
	}
	
	// Method that returns a list of matches within the bracket
	public List<Match> getMatches() {
		return matches;
	}

	// Method to add a match
	public void addMatch(Match m) {
		matches.add(m);
	}
}
