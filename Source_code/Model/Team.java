package Model;

import java.util.ArrayList;

/* 
 * Stores data for each team
*/
public class Team
{	
	int id;
	private String name;
	private ArrayList<Player> players;

	public Team(String name) {
		this.name = name;
		players = new ArrayList<Player>();
	}
	
	
	public String getName() {
		return name;
	}

	//Method to add player to team
	public void addPlayer(Player p) {		
		players.add(p);
	}
	
	//Method to remove player from team
	public boolean removePlayer(Player p) {
		if (!players.contains(p))
			return false;
		
		players.remove(p);
		return true;
	}
	
	//Getter for list of players
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	//Getter for number of players
	public int size() {
		return players.size();
	}
	
	public String toString()
	{
		return "Team Name: " + this.name + ", Players: " + this.players.toString();
	}
}
