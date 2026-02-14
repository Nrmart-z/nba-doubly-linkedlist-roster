package app;

import java.util.Scanner;

import model.Division;
import model.League;
import model.Player;
import model.Team;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        League nba = new League("NBA");
        Division atlantic = new Division("Atlantic");
        nba.addDivision(atlantic);

        // Teams
        Team celtics = new Team("Boston", "Celtics", "BOS");
        Team nets    = new Team("Brooklyn", "Nets", "BKN");
        Team knicks  = new Team("New York", "Knicks", "NYK");
        Team sixers  = new Team("Philadelphia", "76ers", "PHI");
        Team raptors = new Team("Toronto", "Raptors", "TOR");

        atlantic.addTeam(celtics);
        atlantic.addTeam(nets);
        atlantic.addTeam(knicks);
        atlantic.addTeam(sixers);
        atlantic.addTeam(raptors);

        // Seed a few players to test instantly
        seedPlayers(celtics, nets, knicks, sixers, raptors);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> atlantic.printTeams();
                case 2 -> teamMenu(atlantic);
                case 3 -> tradeMenu(atlantic);
                case 4 -> atlantic.printAllRosters();
                case 5 -> {
                    System.out.println("Goodbye.");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
            System.out.println();
        }

        input.close();
    }

    /*----------------------------------------
     *----------------- MENUS ----------------
     *----------------------------------------*/

    private static void printMainMenu() {
        System.out.println("=== NBA ATLANTIC DIVISION ROSTER SIM (2024-25 Season) ===");
        System.out.println("1) List Teams");
        System.out.println("2) Team Menu");
        System.out.println("3) Trade Players");
        System.out.println("4) Print All Rosters");
        System.out.println("5) Exit");
    }

    private static void teamMenu(Division atlantic) {
        Team team = selectTeam(atlantic, "Enter team abbreviation (BOS/BKN/NYK/PHI/TOR): ");
        if (team == null) {
            System.out.println("Team not found.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\n=== " + team.getFullName() + " MENU ===");
            System.out.println("1) Print Roster");
            System.out.println("2) Add Player");
            System.out.println("3) Remove Player");
            System.out.println("4) Search Player");
            System.out.println("5) Save Roster");
            System.out.println("6) Load Roster");
            System.out.println("7) Back");

            int choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> team.printRoster();
                case 2 -> addPlayerFlow(team);
                case 3 -> removePlayerFlow(team);
                case 4 -> searchPlayerFlow(team);

                case 5 -> {
                    boolean ok = FileManager.saveTeam(team, team.getAbbreviation() + ".txt");
                    System.out.println(ok ? "Roster saved." : "Save failed.");
                }

                case 6 -> {
                    boolean ok = FileManager.loadTeam(team, team.getAbbreviation() + ".txt");
                    System.out.println(ok ? "Roster loaded." : "Load failed (file missing?).");
                }

                case 7 -> back = true;

                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void tradeMenu(Division atlantic) {
        System.out.println("=== TRADE MENU ===");

        Team from = selectTeam(atlantic, "Trade FROM - Enter team abbreviation (BOS/BKN/NYK/PHI/TOR): ");
        if (from == null) { 
            System.out.println("Team not found."); 
            return; 
        }

        Team to = selectTeam(atlantic, "Trade TO   - Enter team abbreviation (BOS/BKN/NYK/PHI/TOR): ");
        if (to == null) { 
            System.out.println("Team not found."); 
            return; 
        }

        System.out.print("Player name to trade: ");
        String playerName = input.nextLine().trim();

        boolean success = from.tradeTo(to, playerName);
        System.out.println(success ? "Trade success!" : "Trade failed (player missing or roster full).");
    }

    /*----------------------------------------
     *----------------- FLOWS ----------------
     *----------------------------------------*/

    private static void addPlayerFlow(Team team) {
        System.out.print("Name: ");
        String name = input.nextLine().trim();

        System.out.print("Position (PG/SG/SF/PF/C): ");
        String pos = input.nextLine().trim().toUpperCase();

        int age = readInt("Age: ");
        double height = readDouble("Height (in inches, ex 79 for 6'7): ");

        double ppg = readDouble("PPG: ");
        double rpg = readDouble("RPG: ");
        double apg = readDouble("APG: ");
        double stl = readDouble("STL: ");
        double bpg = readDouble("BLK: ");
        double to  = readDouble("TO: ");
        double salaryM = readDouble("Salary (millions): ");

        Player p = new Player(name, pos, age, height, ppg, rpg, apg, stl, bpg, to, salaryM);

        boolean added = team.addPlayer(p);
        System.out.println(added ? "Player added." : "Add failed (roster full or invalid).");
    }

    private static void removePlayerFlow(Team team) {
        System.out.print("Player name to remove: ");
        String name = input.nextLine().trim();

        Player removed = team.removePlayer(name);
        System.out.println(removed != null ? "Removed: " + removed.getName() : "Player not found.");
    }

    private static void searchPlayerFlow(Team team) {
        System.out.print("Player name to search: ");
        String name = input.nextLine().trim();

        Player found = team.findPlayer(name);
        System.out.println(found != null ? found : "Player not found.");
    }

    /*----------------------------------------
     *---------------- HELPERS ---------------
     *----------------------------------------*/

    private static Team selectTeam(Division division, String prompt) {
        System.out.print(prompt);
        String abbr = input.nextLine().trim().toUpperCase();
        return division.getTeamByAbbreviation(abbr);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!input.hasNextInt()) {
            input.next(); 
            System.out.print("Enter an integer: ");
        }
        int val = input.nextInt();
        input.nextLine(); 
        return val;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!input.hasNextDouble()) {
            input.next(); // trash invalid token
            System.out.print("Enter a number: ");
        }
        double val = input.nextDouble();
        input.nextLine(); // consume leftover newline
        return val;
    }

    private static void seedPlayers(Team celtics, Team nets, Team knicks, Team sixers, Team raptors) {

        // Celtics
    	
 //										Name 			POS  AGE  HT  PPG   RPG  APG  STL  BPG  TO    SAL
        celtics.addPlayer(new Player("Jayson Tatum", 	"SF", 26, 80, 26.8, 8.7, 6.0, 1.1, 0.5, 2.9, 34.8));
        celtics.addPlayer(new Player("Jaylen Brown", 	"SG", 28, 78, 22.2, 5.8, 4.5, 1.2, 0.3, 2.6, 49.2));
        celtics.addPlayer(new Player("Derrick White", 	"PG", 30, 76, 16.4, 4.5, 4.8, 0.9, 1.1, 1.7, 20.0));

        // Nets
        //								Name 			POS  AGE  HT  PPG   RPG  APG  STL  BPG  TO    SAL
        nets.addPlayer(new Player("Cam Thomas", 		"SG", 24, 75, 15.6, 1.8, 3.1, 0.2, 0.1, 2.0, 4.1));
        nets.addPlayer(new Player("D'Angelo Russel", 	"PG", 28, 75, 12.9, 2.8, 5.6, 1.1, 0.7, 2.0, 18.7));

        // Knicks
        //								Name 			POS  AGE  HT  PPG   RPG  APG  STL  BPG  TO    SAL
        knicks.addPlayer(new Player("Jalen Brunson", 	"PG", 28, 74, 26.0, 2.9, 7.3, 0.9, 0.1, 2.5, 24.9));
        knicks.addPlayer(new Player("Mikal Bridges", 	"SF", 28, 78, 17.1, 3.1, 3.6, 0.9, 0.5, 1.6, 23.3));

        // 76ers
        //								Name 			POS  AGE  HT  PPG   RPG  APG  STL  BPG  TO    SAL
        sixers.addPlayer(new Player("Tyrese Maxey", 	"PG", 24, 74, 26.3, 3.3, 6.1, 1.8, 0.4, 2.4, 35.1));
        sixers.addPlayer(new Player("Joel Embiid", 		"C", 30, 84, 23.8, 8.2, 4.5, 0.7, 0.9, 3.3, 51.4));

        // Raptors
        //								Name 			POS  AGE  HT  PPG   RPG  APG  STL  BPG  TO    SAL
        raptors.addPlayer(new Player("Scottie Barnes", 	"PF", 23, 80, 19.3, 7.7, 5.8, 1.4, 1.0, 2.8, 10.1));
        raptors.addPlayer(new Player("Brandon Ingram", 	"SF", 27, 80, 22.2, 5.6, 5.2, 0.9, 0.6, 3.8, 36.0));
    }
}
