package Model;

/*
 * This class is for the players in the teams.
*/
public class Player {
	
	private int age;
	private String name;

	public Player(String name, int age) {
		this.age = age;
		this.name = name;
	}
	
	// getter for name of player
	public String getName() {
		return name;
	}
	
	// getter for age of player
	public int getAge() {
		return age;
	}
}
