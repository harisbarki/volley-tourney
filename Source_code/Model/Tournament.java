package Model;

import java.time.LocalDate;
import java.util.ArrayList;

/*
 * This class stores info for each tournament
 * 
*/
public class Tournament 
{
	private ArrayList<Team> teams;
	private ArrayList<Team> topTeams;
	private Schedule schedule;
	private String name;
	private String type;
	private LocalDate tournamentStart;
	private LocalDate tournamentEnd;
	private LocalDate registrationStart;
	private LocalDate registrationEnd;
	private int minPlayerAge;
	private int maxPlayerAge;
	private int numTeams;
	private final int minimumTeamSize = 6;
	
	// constructor creates a tournament	
	public Tournament(String name, String type, LocalDate tournamentStart, LocalDate tournamentEnd, LocalDate registrationStart, LocalDate registrationEnd, int minPlayerAge, int maxPlayerAge, int numTeams) 
	{
		this.name = name;
		this.type = type;
		this.tournamentStart = tournamentStart;
		this.tournamentEnd = tournamentEnd;
		this.registrationStart = registrationStart;
		this.registrationEnd = registrationEnd;
		this.minPlayerAge = minPlayerAge;
		this.maxPlayerAge = maxPlayerAge;
		this.numTeams = numTeams;
	}
	
	// accessors
	public String getName()
	{	
		return name;
	}
	
	public String getType()
	{	
		return type;
	}
	
	public LocalDate getStartDate()
	{	
		return tournamentStart;	
	}
	
	public LocalDate getEndDate()
	{
		return tournamentEnd;
	}
	
	public LocalDate getRegStartDate()
	{
		return registrationStart;
	}
	
	public LocalDate getRegEndDate()
	{
		return registrationEnd;
	}
	
	public int getMinAge()
	{
		return minPlayerAge;
	}
	
	public int getMaxAge()
	{
		return maxPlayerAge;
	}
	
	public int getNumTeams()
	{
		return numTeams;
	}
	
	// returns whether a the registration is still in progress
	public boolean canRegister()
	{
		if (LocalDate.now().isAfter(registrationStart) && LocalDate.now().isBefore(registrationEnd))
			return true;
		else
			return false;
	}
	
	// returns whether the tournament is in progress
	public boolean inProgress()
	{
		if (LocalDate.now().isAfter(tournamentStart) && LocalDate.now().isBefore(tournamentEnd))
			return true;
		else 
			return false;
	}
	
	// method to add and check validity of team to the tournament
	public boolean addTeam(Team team)
	{
		if (team.size() < minimumTeamSize) 
			return false;
		
		for (Player p : team.getPlayers()) 
			if (p.getAge() < minPlayerAge && p.getAge() > maxPlayerAge) 
				return false;
		
		teams.add(team);
		return true;
	}
	
	// method to remove team from tournament
	public boolean removeTeam(Team team)
	{
		if (teams.contains(team)) {
			teams.remove(team);
			return true;
		}
		return false;
	}
	
	// method to add and check validity of team to the tournament
	public void addTopTeam(Team team)
	{
		if (teams.contains(team))
			topTeams.add(team);
	}
	
	// to create the schedule after registration has ended 
	public void createSchedule()
	{
		schedule = new Schedule();
		schedule.generateSchedule(teams, topTeams, type);
	}
}
