package LibaryFunctions;

import Game.Board.Square;
import Game.GameOutcome;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    /**
     * Converts one row of a board to a 1D array so that it can be manipulated to look for
     *  piece collisions or check pathways.
     *
     * @param board       a 2D square array to represent a board, so that one dimension can be copied to another array,
     * @param PieceOrigin The square that holds the piece involved on the row, such as a pathway of checked squares by a rook.
     *                    All squares returned will be on the same row as this piece.
     * @return an Arraylist of square objects, representing a board row
     */
    public static ArrayList<Square> ArrayToRow(Square[][] board, Square PieceOrigin) {
        ArrayList<Square> Row = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            //Row is constant but column is variable, to fetch all squares from each row in that column
            Row.add(board[PieceOrigin.ReturnCoordinate().getRank() - 1][i]); //Add -1 because array coordinates begin at 0 but board coordinates start at 1
        }
        return Row;
    }

    /**
     * Converts one Column of a board to a 1D array so that it can be manipulated to look for
     * piece collisions or check pathways.
     *
     * @param board       a 2D square array to represent a board, so that one dimension can be copied to another array,
     * @param PieceOrigin The square that holds the piece involved on the column, such as a pathway of checked squares by a rook.
     *                    All squares returned will be on the same Column as this piece.
     * @return an Arraylist of square objects, representing a board row
     */
    public static ArrayList<Square> ArrayToColumn(Square[][] board, Square PieceOrigin) {
        ArrayList<Square> Column = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            //Column is constant but row is variable, to fetch all squares from each column in that row
            Column.add(board[i][PieceOrigin.ReturnCoordinate().getFile() - 1]);
        }
        return Column;
    }

    /**
     * Takes a password and hashes it using SHA256 so that it can be safely stored in the database
     *
     * @param Password The un-hashed password string
     * @return a string of the hashed password
     */
    public static String hashPassword(String Password) {
        try {
            MessageDigest Hash = MessageDigest.getInstance("SHA256");

            Hash.update(Password.getBytes());
            byte[] Hashed = Hash.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : Hashed) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Checks that all details entered by a new user are correct, Not-taken, and that all text fields are filled in
     *
     * @param UserID          A Unique User Defined 4-digit Identification key
     * @param Username        A non-unique User Defined User name
     * @param Email           The Users unique email address
     * @param Name            The users first name
     * @param Surname         The users last name
     * @param Password        The users un-hashed password
     * @param ConfirmPassword The users un-hashed confirmation password
     * @return a boolean of whether the new users details are all valid or not.
     */
    public static boolean CheckValidNewUser(String UserID, String Username, String Email, String Name,
                                            String Surname, String Password, String ConfirmPassword) {
        if (isNotBlankOrEmpty(UserID) && isUserIdAvailable(UserID) && UserID.length() == 4) {
            if (isNotBlankOrEmpty(Username) &&
                    isNotBlankOrEmpty(Email) &&
                    isNotBlankOrEmpty(Name) &&
                    isNotBlankOrEmpty(Surname) &&
                    isNotBlankOrEmpty(Password) &&
                    isNotBlankOrEmpty(ConfirmPassword)) {
                if (Password.equals(ConfirmPassword)) {
                    return isEmailFormatValid(Email);
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a users details exist within the database, if they do then they can log in
     *
     * @param UserID   A Unique User Defined 4-digit Identification key
     * @param Email    The Users unique email address
     * @param Password The Users un-hashed Password (this is hashed in the repository class before checking the database)
     * @return true if the user entered a valid login
     */
    public static boolean CheckValidLogin(String UserID, String Email, String Password) {
        if (isNotBlankOrEmpty(UserID) && isNotBlankOrEmpty(Email) && isNotBlankOrEmpty(Password)) {
            return Repository.UserFound(UserID, Email, Password);
        }
        return false;
    }

    /**
     * Searches the database to check if a new users UserID is unique and not taken
     *
     * @param UserID A Unique User Defined 4-digit Identification key
     * @return true if the UserID is available (Not already taken)
     */
    public static boolean isUserIdAvailable(String UserID) {
        for (String ID : Repository.getUserIds()) {
            if (ID.equals(UserID)) return false;
        }
        return ! UserID.contains("//");
    }

    /**
     * Checks whether a String is empty or blank
     *
     * @param string The string to be checked
     * @return true if the String is filled (Not empty or blank)
     */
    public static boolean isNotBlankOrEmpty(String string) {
        return !string.isEmpty() && !string.isBlank();
    }

    /**
     * Checks that a valid email structure has been entered using regular expression. eg - name@domain.com
     *
     * @param email The Users unique email address
     * @return true if the email is in a correct format
     */
    public static boolean isEmailFormatValid(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Converts a list of objects into a string array. Used when converting countries from the database into an array of country names to be displayed in a JComboBox
     *
     * @param Array The List of objects which is to be converted into a String Array
     * @return a String Array containing the same data that was in the List but converted.
     */
    public static String[] ObjectArrayToStringArray(List<String> Array) {
        String[] StringArray = new String[Array.size()];
        Object[] ObjectArray = Array.toArray();

        int iteration = 0;
        for (Object object : ObjectArray) {
            StringArray[iteration] = object.toString();
            iteration++;
        }
        return StringArray;
    }

    /**
     * Updates the ELO of a player based on their current rank, opponents rank, and game outcome
     * @param ELO_of_Player The current ELO rank of the player which rank needs to be updated after a game
     * @param ELO_of_Opponent The ELO score of the opponent the player played against
     * @param gameOutcome The outcome of the game, Such as a win, loss or draw.
     * @return a rounded integer of the players new ELO, this could be less than or more than their original ELO
     * depending on the game outcome
     */
    public static int CalculateNew_ELO(int ELO_of_Player, int ELO_of_Opponent, GameOutcome gameOutcome) {

        double ExpectedScore_Player = 1 / (1 + (Math.pow(10, (double)(ELO_of_Opponent - ELO_of_Player) / 400)));
        int K_Factor;

        if (ELO_of_Player > 2400) K_Factor = 16;
        else if (ELO_of_Player > 2100) K_Factor = 24;
        else K_Factor = 32;

        return (int) Math.round(ELO_of_Player + (K_Factor * (GameOutcome.valueOf(gameOutcome) - ExpectedScore_Player)));
    }
}
