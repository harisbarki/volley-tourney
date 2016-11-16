import java.util.List;

/* 
 * Stores data for each team
*/
public class Team {
	
	private String name;
	private List<Player> players;

	public Team(String name) {
		this.name = name;
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
	public List<Player> getPlayers() {
		return players;
	}
	
	//Getter for number of players
	public int size() {
		return players.size();
	}
}
