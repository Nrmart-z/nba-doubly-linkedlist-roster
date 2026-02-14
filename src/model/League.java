package model;
import java.util.ArrayList;



public class League {
	
	private String leagueName;
    private ArrayList<Division> divisions;

    public League(String leagueName) {
        this.leagueName = leagueName;
        this.divisions = new ArrayList<>();
    }

    public String getLeagueName() {
        return leagueName;
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }

    public boolean addDivision(Division division) {
        if (division == null) return false;

        if (getDivisionByName(division.getDivisionName()) != null) {
            return false; // already exists
        }

        divisions.add(division);
        return true;
    }

    public Division getDivisionByName(String name) {
        if (name == null) return null;

        String target = name.trim();
        for (Division d : divisions) {
            if (d.getDivisionName().equalsIgnoreCase(target)) {
                return d;
            }
        }
        return null;
    }

    public void printDivisions() {
        System.out.println("=== " + leagueName + " Divisions ===");
        for (Division d : divisions) {
            System.out.println("- " + d.getDivisionName());
        }
    }

    public void printLeagueRosters() {
        System.out.println("=== " + leagueName + " Rosters ===");
        for (Division d : divisions) {
            d.printAllRosters();
        }
    }

}
