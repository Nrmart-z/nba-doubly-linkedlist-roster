package model;

	import structure.RosterLinkedList;
	
public class Team {
	private String city;
	private String teamName;
	private String abberviation;
	private RosterLinkedList roster;
	
	public Team(String city, String teamName, String abberviation) {
		this.city = city;
		this.teamName = teamName;
		this.abberviation = abberviation;
		this.roster = new RosterLinkedList();
	}
	// info
	public String getcity() { return city; }
	public String getTeamName() { return teamName;}
	public String getAbbreviation(){ return abberviation;}
	public String getFullName(){ return city + " " + teamName;}
	
	public RosterLinkedList getRoster() { return roster;}
	
	// Roster operations
	
	public boolean addPlayer(Player p) {
		return roster.addPlayerSorted(p);
	}
	
	public Player removePlayer(String playerName) {
		return roster.removeByName(playerName);
	}
	
	public Player findPlayer(String playerName) {
		return roster.searchByName(playerName);
	}
	
	public boolean hasPlayer(String playerName) {
		return roster.contains(playerName);
	}
	
	public int getRostersize() {
		return roster.getSize();
	}
	
	public void printRoster() {
		System.out.println("===" + getFullName() + " (" + abberviation + ") ===");
		roster.printRosterForward();
	}
	
	// Team Stats
	public double getTotalSalaryM() {
		double total = 0.0;
		//
		//
		//
		return total;
	}
	
	// trade
	public boolean tradeTo(Team otherTeam, String playerName) {
		if (otherTeam == null || playerName == null) return false;
		
		Player removed = roster.removeByName(playerName);
		if (removed == null) return false;
		
		boolean added = otherTeam.addPlayer(removed);
			if (!added) {
				//if other team can't add, puts player back
				roster.addPlayerSorted(removed);
				return false;
			}
			return true;
	}
	@Override 
	public String toString() {
		return getFullName() + " (" + abberviation + ") ";
	}
	
}
