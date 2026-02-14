package app;

import java.io.*;
import model.Team;


public class FileManager {

    public static boolean saveTeam(Team team, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(team.getRoster().toCSV());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean loadTeam(Team team, String filename) {
        File f = new File(filename);
        if (!f.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            team.getRoster().loadFromCSV(sb.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
