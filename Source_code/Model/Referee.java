package Model;

import java.util.List;

/*
 * This class can only be accessible once a referee logs into the system. It gives 
 * them the option to enter the scores of the matches and view the matches they are refereeing.
 */

public class Referee extends Account {

	private List<Match> matchList;

	/**
	 * Constructor
	 * 
	 * @param username
	 * @param password
	 * @param name
	 */
	public Referee(String username, String password, String name) {
		super(username, password, name);
	}

	/**
	 * 
	 * @return list of matches
	 */
	public List<Match> getMatchList() {
		return matchList;
	}
}
