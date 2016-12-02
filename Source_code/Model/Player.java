package Model;

/*
 * This class is for the players in the teams.
*/
public class Player {

	private int age;
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param age
	 */
	public Player(String name, int age) {
		this.age = age;
		this.name = name;
	}

	/**
	 * 
	 * @return name of player
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return age of player
	 */
	public int getAge() {
		return age;
	}

	/**
	 * converts the data into neat string
	 * 
	 * @return string
	 */
	public String toString() {
		return "[Name = " + this.name + ", Age = " + this.age + "]";
	}
}
