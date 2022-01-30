package LibaryFunctions;

import GUIs.GUI_GamePanel;
import Game.GameType;
import Game.Colour;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class PGN_FileHandling {

    private static File PGN_File;

    /**
     * Creates the PGN text file
     */
    public static void createPGN(){
        String FileName = Repository.getCurrentUser().getUserID() + "_" + Repository.getCurrentUser().getStatistics().getGames() + ".txt";
        PGN_File = new File(FileName);

        try {
            PGN_File.createNewFile();

        } catch (IOException exception) {
            System.out.println("Error Creating PGN file");
            exception.printStackTrace();
        }

        writeToPGN("[Event \"" + GUI_GamePanel.getGame().getGameType() + "\"]\n");
        writeToPGN("[Date \"" + new java.sql.Date (Calendar.getInstance().getTime().getTime()) + "\"]\n");
        writeToPGN("[User Elo \"" + Repository.getCurrentUser().getStatistics().getELO() + "\"]\n");
        writeToPGN("[User \"" + Repository.getCurrentUser().getUserID() + "\"]\n\n\n");
    }

    /**
     * Appends a String to the text file
     * @param StringToWrite The string to be appended
     */
    public static void writeToPGN(String StringToWrite){
        try {
            FileWriter fileWriter = new FileWriter(PGN_File.getName(), true);
            fileWriter.write(StringToWrite);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
