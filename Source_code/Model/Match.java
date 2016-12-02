package Model;

import java.time.LocalDate;

/**
 * Class for each Matches where the scores, time and the teams playing are
 * stored. Once finished the referee can input scores
 */
public class Match {
	private Team team1;
	private Team team2;
	private final String LOCATION = "The Works";
	private LocalDate date;
	private boolean finished;
	private int score1;
	private int score2;
	private int round;

	/**
	 * Constructor
	 * 
	 * @param team1
	 * @param team2
	 * @param schedule
	 */
	public Match(Team team1, Team team2, LocalDate schedule) {
		this.team1 = team1;
		this.team2 = team2;
		this.date = schedule;
		round = 1;
		finished = false;
		score1 = 0;
		score2 = 0;
	}

	/**
	 * Finishes the game and inputs final score
	 * @param x
	 * @param y
	 */
	public void finishGame(int x, int y) {
		score1 = x;
		score2 = y;
		
		if(score1 > score2)
			team1.incrementWin();
		else
			team2.incrementWin();
		
		finished = true;
	}

	/**
	 * Edits the score of the match
	 * 
	 * @param x
	 * @param y
	 */
	public void editScore(int x, int y) {
		score1 = x;
		score2 = y;
	}

	/**
	 * 
	 * @return flag for match is finished or not
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * 
	 * @return team 1
	 */
	public Team getTeam1() {
		return team1;
	}

	/**
	 * 
	 * @return team 2
	 */
	public Team getTeam2() {
		return team2;
	}

	/**
	 * 
	 * @return score of team 1
	 */
	public int getScore1() {
		return score1;
	}

	/**
	 * 
	 * @return score of team 2
	 */
	public int getScore2() {
		return score2;
	}

	/**
	 * 
	 * @return location of match
	 */
	public String getLocation() {
		return LOCATION;
	}

	/**
	 * Method to get the time of the match
	 * 
	 * @return time of the match
	 */
	public LocalDate getSchedule() {
		return date;
	}

	/**
	 * gets round
	 * 
	 * @return
	 */
	public int getRound() {
		return round;
	}

	/**
	 * sets round
	 * 
	 * @param r
	 */
	public void setRound(int r) {
		round = r;
	}

	/**
	 * 
	 * @return winner team
	 */
	public Team getWinner() {
		if (score1 > score2)
			return team1;
		else
			return team2;
	}

	/**
	 * checks if the match is equal
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals(Match other) {
		if (this.getTeam1().equals(other.getTeam1()) && this.getTeam2().equals(other.getTeam2())) {
			if (this.getSchedule().compareTo(other.getSchedule()) == 0)
				return true;
		}

		if (this.getTeam1().equals(other.getTeam2()) && this.getTeam2().equals(other.getTeam1())) {
			if (this.getSchedule().compareTo(other.getSchedule()) == 0)
				return true;
		}

		return false;
	}
}
