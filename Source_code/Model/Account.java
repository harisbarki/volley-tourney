package Model;

/*
 * This abstract class is the basis for all accounts which stores basic 
 * properties every account has such as username, and password. It also keeps
 * track of whether the account is signed in or not
 */
public abstract class Account {
	private String username;
	private String password;
	private String name;
	@SuppressWarnings("unused")
	private boolean loggedIn;
	
	public Account(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
		loggedIn = false;
	}
	
	//getter for name of account holder
	public String getName() {
		return name;
	}
	
	//getter for username of account
	public String getUsername() {
		return username;
	}
	
	//method to log into the system.
	public boolean logIn(String password) {
		if(this.password == password) {
			loggedIn=true;
			return true;
		}
		
		return false;
	}
	
	// method to log out of the system.
	public void logOut() {
		loggedIn=false;
	}
}
