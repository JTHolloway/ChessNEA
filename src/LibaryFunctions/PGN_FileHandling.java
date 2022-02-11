package LibaryFunctions;

import GUIs.GUI_GamePanel;
import Game.GameOutcome;
import Game.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class PGN_FileHandling {

    private static File PGN_File;

    /**
     * Creates the PGN text file
     */
    public static void createPGN() {
        String FileName = Repository.getCurrentUser().getUserID() + "_" + Repository.getCurrentUser().getStatistics().getGames() + ".txt";
        File directory = new File(System.getProperty("user.dir") + "/Game Files");
        directory.mkdir();
        PGN_File = new File(directory, FileName);
        try {
            PGN_File.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Player winner = GUI_GamePanel.getGame().gameOver();
        GameOutcome gameOutcome;
        if (winner instanceof Player.Human) {
            if (((Player.Human) winner).getUser() != null && ((Player.Human) winner).getUser().getUserID().equals(Repository.getCurrentUser().getUserID())) {
                gameOutcome = GameOutcome.WIN;
            } else {
                gameOutcome = GameOutcome.LOSS;
            }
        } else if (winner instanceof Player.Computer) {
            gameOutcome = GameOutcome.LOSS;
        } else {
            gameOutcome = GameOutcome.DRAW;
        }

        writeToPGN("[Event \"" + GUI_GamePanel.getGame().getGameType() + "\"]\n");
        writeToPGN("[Date \"" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "\"]\n");
        writeToPGN("[User Elo \"" + Repository.getCurrentUser().getStatistics().getELO() + "\"]\n");
        writeToPGN("[Colour \"" + GUI_GamePanel.getGame().getSelectedColour() + "\"]\n");
        writeToPGN("[Outcome \"" + gameOutcome + "\"]\n");
        writeToPGN("[User \"" + Repository.getCurrentUser().getUserID() + "\"]\n\n\n");
    }

    /**
     * Appends a String to the text file
     *
     * @param StringToWrite The string to be appended
     */
    public static void writeToPGN(String StringToWrite) {
        try {
            FileWriter fileWriter = new FileWriter(PGN_File.getAbsoluteFile(), true);
            fileWriter.write(StringToWrite);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
