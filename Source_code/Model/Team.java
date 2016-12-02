package Model;

import java.util.ArrayList;

/* 
 * Stores data for each team
*/
public class Team {
	private int seed;
	private int wins;
	private String name;
	private ArrayList<Player> players;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Team(String name) {
		seed = -1; // sets the default seeding position as -1 when not seeded
		this.name = name;
		players = new ArrayList<Player>();
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set seeding position of this team
	 * 
	 * @param s
	 */
	public void setSeedingPos(int s) {
		seed = s;
	}

	/**
	 * Show seeding position of this team
	 * 
	 * @return seed
	 */
	public int showSeedPos() {
		return seed;
	}

	/**
	 * 
	 * @return seeded or not flag
	 */
	public boolean seeded() {
		if (showSeedPos() == -1)
			return false;
		else
			return true;
	}

	/**
	 * Add player to the team
	 * 
	 * @param p
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}

	/**
	 * Remove player from the team
	 * 
	 * @param p
	 * @return
	 */
	public boolean removePlayer(Player p) {
		if (!players.contains(p))
			return false;

		players.remove(p);
		return true;
	}

	/**
	 * Return all players of this team
	 * 
	 * @return list of players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * 
	 * @return number of players
	 */
	public int size() {
		return players.size();
	}

	/**
	 * 
	 * @return wins of this team
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * Checks if this team equals another team
	 * 
	 * @param other
	 * @return flag
	 */
	public boolean equals(Team other) {
		if (this.getPlayers().size() != other.getPlayers().size())
			return false;

		if (this.getName().equals(other.getName())) {
			if (this.showSeedPos() == other.showSeedPos()) {
				for (int i = 0; i < other.getPlayers().size(); i++) {
					if (this.getPlayers().contains(other.getPlayers().get(i)))
						return true;
				}
			}
		}

		return false;
	}

	/**
	 * Beautifies the team into a string
	 */
	public String toString() {
		return "Team Name: " + this.name + ", Players: " + this.players.toString();
	}
}
