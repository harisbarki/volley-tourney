package Model;

import java.time.LocalDateTime;

/*
 * Class for each Matches where the scores, time and the
 * teams playing are stored. Once finished the referee
 * can input scores
 * 
 * The referee inputs scores??? spc
 * 
 * 
 * 
 * Need referee/organizer/coach classes because each have the same account 
 * functionalities yet different functionalities when it comes to teams
 * matches and tournaments
 * 
 * Would implement referee editing scores in referee class
 * the method would do something then call editScores here
 */
public class Match {
	
	private Team team1;
	private Team team2;
	private String location;
	private LocalDateTime time;
	private boolean finished;
	private int score1;
	private int score2;

	public Match(Team team1, Team team2, String location, LocalDateTime time) {
		this.team1 = team1;
		this.team2 = team2;
		this.location = location;
		this.time = time;
		finished = false;
		score1 = 0;
		score2 = 0;
	}

 	// Method to input score after game is finished		
	public void finishGame(int x, int y) {
		score1 = x;
		score2 = y;
		finished = true;
	}
	
	// Method to edit the scores of the match	
	public void editScore(int x, int y) {
		score1 = x;
		score2 = y;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	// Method to get team1's score
 	public Team getTeam1() {
		return team1;
	}
 	
	// Method to get team2's score
 	public Team getTeam2() {
		return team2;
	}
	
	// Method to get team1's score
 	public int getScore1() throws Exception {
		if (!finished) 
			throw new Exception("Game is not yet finished");
		
		return score1;
	}
 	
	// Method to get team1's score
 	public int getScore2() throws Exception {
		if (!finished) 
			throw new Exception("Game is not yet finished");
		
		return score2;
	}
	
	// Method to get the location of the match
	public String getLocation() {
		return location;
	}
	
	// Method to get the time of the match	
	public LocalDateTime getTime() {
		return time;
	}
}
