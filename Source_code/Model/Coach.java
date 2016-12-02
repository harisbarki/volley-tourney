package Model;

import java.util.List;

/* 
 * This class can only be accessible once a coach logs into the system. It gives 
 * them the option to create a team and add the players.
 */
public class Coach extends Account {

	private List<Team> teamList;

	/**
	 * 
	 * @param username
	 * @param password
	 * @param name
	 */
	public Coach(String username, String password, String name) {
		super(username, password, name);
	}

	/**
	 * Method to get the list of teams this coach has registered.
	 * 
	 * @return list of teams of this coach
	 */
	public List<Team> getTeams() {
		return teamList;
	}
}
