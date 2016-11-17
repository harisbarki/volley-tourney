package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * This class stores info for each tournament
 * 
 * ArrayLists???
 * Look at spc rules
 * throw exceptions??
*/
public class Tournament {
	
	ArrayList<Team> teams;
	ArrayList<Team> topTeams;
	Schedule schedule;
	String type;
	LocalDateTime tournamentStart;
	LocalDateTime tournamentEnd;
	LocalDateTime registrationStart;
	LocalDateTime registrationEnd;
	int ageRequirement;
	int minimumTeamSize;
	int maximumTeamSize;

	//Constructor creates a tournament.	
	public Tournament(String type, LocalDateTime tournamentStart, LocalDateTime tournamentEnd, LocalDateTime registrationStart, LocalDateTime registrationEnd, int ageRequirement, int minimumTeamSize, int maximumTeamSize) {
		this.type = type;
		this.tournamentStart = tournamentStart;
		this.tournamentEnd = tournamentEnd;
		this.registrationStart = registrationStart;
		this.registrationEnd = registrationEnd;
		this.ageRequirement = ageRequirement;
		this.minimumTeamSize = minimumTeamSize;
		this.maximumTeamSize = maximumTeamSize;
	}
	
	// Returns whether a the registration is still in progress
	public boolean canRegister() {
		if (LocalDateTime.now().isAfter(registrationStart) && LocalDateTime.now().isBefore(registrationEnd)) {
			return true;
		}
		
		return false;
	}
	
	// Returns whether the tournament is in progress
	public boolean inProgress() {
		if (LocalDateTime.now().isAfter(tournamentStart) && LocalDateTime.now().isBefore(tournamentEnd)) {
			return true;
		}
		
		return false;
	}
	
	//Method to add and check valitidy of team to the tournament
	public boolean addTeam(Team team) {
		if (team.size() < minimumTeamSize || team.size() > maximumTeamSize) 
			return false;
		
		for (Player p : team.getPlayers()) 
			if (p.getAge() < ageRequirement) 
				return false;
		
		teams.add(team);
		return true;
	}
	
	//Method to remove team from tournament
	public boolean removeTeam(Team team) {
		if (teams.contains(team)) {
			teams.remove(team);
			return true;
		}
		return false;
	}
	
	//Method to add and check valitidy of team to the tournament
	public void addTopTeam(Team team) {
		if (teams.contains(team)) {
			topTeams.add(team);
		}
	}
	
	//To create the schedule after registration has ended 
	public void createSchedule() {
		schedule = new Schedule();
		schedule.generateSchedule(teams, topTeams, type);
	}
}
