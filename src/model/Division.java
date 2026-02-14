package model;
import java.util.ArrayList;

public class Division {
	
	private String divisionName;
	private ArrayList<Team> teams;
	
	public Division(String divisionName) {
		this.divisionName = divisionName;
		this.teams = new ArrayList<>();
	}
	
	public String getDivisionName() {
		return divisionName;
	}
	public ArrayList<Team> getTeams() {
        return teams;
    }

    // Add team 
    public boolean addTeam(Team team) {
        if (team == null) return false;

        if (getTeamByAbbreviation(team.getAbbreviation()) != null) {
            return false; // already exists
        }

        teams.add(team);
        return true;
    }

    // Search by abbreviation
    public Team getTeamByAbbreviation(String abbr) {
        if (abbr == null) return null;

        for (Team t : teams) {
            if (t.getAbbreviation() != null &&
                t.getAbbreviation().equalsIgnoreCase(abbr.trim())) {
                return t;
            }
        }
        return null;
    }

    // Search by name or teamName
    public Team getTeamByName(String name) {
        if (name == null) return null;

        String target = name.trim();

        for (Team t : teams) {
            if (t.getTeamName().equalsIgnoreCase(target) ||
                t.getFullName().equalsIgnoreCase(target)) {
                return t;
            }
        }
        return null;
    }

    public void printTeams() {
        System.out.println("=== " + divisionName + " Division Teams ===");
        for (Team t : teams) {
            System.out.println("- " + t); 
        }
    }

    // print every roster in the division
    public void printAllRosters() {
        System.out.println("=== " + divisionName + " Division Rosters ===");
        for (Team t : teams) {
            t.printRoster();
            System.out.println();
        }
    }
}
