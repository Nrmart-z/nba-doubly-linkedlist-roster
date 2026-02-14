package model;
// Represents one NBA player

public class Player {
	// Vitals
	private String 	name;
	private String 	pos; // PG, SG, SF, PF, C
	private int 	age;
	private double	height; //inches to convert into feet such as _"_'
	
	// Player statistics
	private double ppg;	// points per game
	private double apg;	// assist per game
	private double rpg;	// rebounds per game
	private double stl;	// steals per game
	private double bpg;	// blocks per game
	private double to;	// turnover per game
	
	// contract (Millions)
	private double salaryM;
	
	// trade rating, determines their importance
	private static final double MAX_RAW = 110.0;
	
	public Player(String name, String pos, int age, double height,
				double ppg, double rpg, double apg, double stl, 
				double bpg, double to, double salaryM) {
		
		this.name 		= name;
		this.pos 		= pos;
		this.age 		= age;
		this.height		= height;
		this.ppg		= ppg;
		this.apg		= apg;
		this.rpg		= rpg;
		this.stl		= stl;
		this.bpg		= bpg;
		this.to			= to;
		this.salaryM	= salaryM;
	}
	
	// Getters
	
	public String getName() { return name;}
	public String getPosition(){ return pos;}
	public int getAge() { return age;}
	public double getHeight() { return height; }
	
	
	public double getPpg() { return ppg;}
	public double getApg() { return apg;}
	public double getRpg() { return rpg;}
	public double getStl() { return stl;}
	public double getBpg() { return bpg;}
	public double getTo()  { return to;}
	
	public double getSalaryM() { return salaryM; }
	
	// Setters
	public void setSalaryM(double salaryM) { this.salaryM = salaryM;}
	
	// Score
	public double getValueScore() {
		return (2.7 * ppg) + (2.5 * rpg) + (2.5 * apg) +
			   (4.0 * stl) + (6.0 * bpg) - (4.0 * to) + ((18 - age) / 2) + (height / 12);
	}
	// inches to feet conversion
	public String getHeightFormatted() {
		int totalInches = (int) height;
		int feet = totalInches / 12;
		int inches = totalInches % 12;
		return feet + "'" + inches + "\"";
	}
		
	// Players Value
	public int getOverallRating() {
		int rating = (int) ((getValueScore() / MAX_RAW) * 99);
		if (rating <1) rating = 1;
		if (rating >99) rating = 99;
		return rating;
	}
	
	@Override
	public String toString() {
		return name + "\t | " + pos +
				" | Height: " + getHeightFormatted() +
				" | PPG: " + ppg +
				" | RPG: " + rpg +
				" | APG: " + apg +
				" | STL: " + stl +
				" | BLK: " + bpg +
				" | TO: " + to +
				" | Value: " + String.format("%.1f", getValueScore()) +
				" | OVR: " + getOverallRating() +
				" | $" + salaryM + "M";
	}
	// Save format: name,pos,age,height,ppg,rpg,apg,stl,bpg,to,salaryM
	public String toCSVLine() {
	    return name + "," + pos + "," + age + "," + height + ","
	         + ppg + "," + rpg + "," + apg + "," + stl + "," + bpg + "," + to + "," + salaryM;
	}

	public static Player fromCSVLine(String line) {
	    String[] parts = line.split(",");
	    if (parts.length != 11) return null;

	    String name = parts[0].trim();
	    String pos = parts[1].trim();
	    int age = Integer.parseInt(parts[2].trim());
	    double height = Double.parseDouble(parts[3].trim());

	    double ppg = Double.parseDouble(parts[4].trim());
	    double rpg = Double.parseDouble(parts[5].trim());
	    double apg = Double.parseDouble(parts[6].trim());
	    double stl = Double.parseDouble(parts[7].trim());
	    double bpg = Double.parseDouble(parts[8].trim());
	    double to  = Double.parseDouble(parts[9].trim());

	    double salaryM = Double.parseDouble(parts[10].trim());

	    return new Player(name, pos, age, height, ppg, rpg, apg, stl, bpg, to, salaryM);
	}
}
