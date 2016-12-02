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

	public Team(String name) {
		seed = -1; // sets the default seeding position as -1 when not seeded
		this.name = name;
		players = new ArrayList<Player>();
	}

	public String getName() {
		return name;
	}

	public void setSeedingPos(int s) {
		seed = s;
	}

	public int showSeedPos() {
		return seed;
	}

	public boolean seeded() {
		if (showSeedPos() == -1)
			return false;
		else
			return true;
	}

	// Method to add player to team
	public void addPlayer(Player p) {
		players.add(p);
	}

	// Method to remove player from team
	public boolean removePlayer(Player p) {
		if (!players.contains(p))
			return false;

		players.remove(p);
		return true;
	}

	// Getter for list of players
	public ArrayList<Player> getPlayers() {
		return players;
	}

	// Getter for number of players
	public int size() {
		return players.size();
	}

	public int getWins() {
		return wins;
	}

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

	public String toString() {
		return "Team Name: " + this.name + ", Players: " + this.players.toString();
	}
}
