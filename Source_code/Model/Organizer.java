package Model;

import java.util.List;

/*
 * This class can only be accessible once an organizer logs into the system. It gives 
 * them the option to create the tournament.
 */
public class Organizer extends Account {

	private List<Tournament> tList;

	/**
	 * Constructor
	 * 
	 * @param username
	 * @param password
	 * @param name
	 */
	public Organizer(String username, String password, String name) {
		super(username, password, name);
	}

	/**
	 * 
	 * @return list of tournaments this organizer has created
	 */
	public List<Tournament> getTournaments() {
		return tList;
	}
}
