package Model;

import java.time.LocalDate;

/**
 * Class for each Matches where the scores, time and the
 * teams playing are stored. Once finished the referee
 * can input scores 
 */
public class Match
{
	private Team team1;
	private Team team2;
	private final String LOCATION = "The Works";
	private LocalDate schedule;
	private boolean finished;
	private int score1;
	private int score2;
	private int round;

	public Match(Team team1, Team team2, LocalDate schedule)
	{
		this.team1 = team1;
		this.team2 = team2;
		this.schedule = schedule;
		round = 1;
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
		return score1;
	}
 	
	// Method to get team1's score
 	public int getScore2() throws Exception {
		return score2;
	}
	
	// Method to get the location of the match
	public String getLocation() {
		return LOCATION;
	}
	
	// Method to get the time of the match	
	public LocalDate getSchedule() {
		return schedule;
	}
	
	public int getRound()
	{
		return round;
	}
	
	public void setRound(int r)
	{
		round = r;
	}
	
	public Team getWinner()
	{
		if(score1 > score2)
			return team1;
		else
			return team2;
	}

	public boolean equals(Match other)
	{
		if(this.getTeam1().equals(other.getTeam1()) && this.getTeam2().equals(other.getTeam2())) return true;
		if(this.getTeam1().equals(other.getTeam2()) && this.getTeam2().equals(other.getTeam1())) return true;
		
		return false;
	}
}
