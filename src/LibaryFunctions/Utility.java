package LibaryFunctions;

import Game.Board.Square;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    /**
     * Converts one row of a board to a 1D array so that it can be manipulated to look for
     * for piece collisions or check pathways.
     *
     * @param board       a 2D square array to represent a board, so that one dimension can be copied to another array,
     * @param PieceOrigin The square that holds the piece involved on the row, such as a pathway of checked squares by a rook.
     *                    All squares returned will be on the same row as this piece.
     * @return an Arraylist of square objects, representing a board row
     */
    public static ArrayList<Square> ArrayToRow(Square[][] board, Square PieceOrigin)
    {
        ArrayList<Square> Row = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            //Row is constant but column is variable, to fetch all squares from each row in that column
            Row.add(board[PieceOrigin.ReturnCoordinate().getRank() - 1][i]); //Add -1 because array coordinates begin at 0 but board coordinates start at 1
        }
        return Row;
    }
    
    /**
     * Converts one Column of a board to a 1D array so that it can be manipulated to look for
     * for piece collisions or check pathways.
     * @param board a 2D square array to represent a board, so that one dimension can be copied to another array,
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

    public static String hashPassword(String Password) {
        try {
            MessageDigest Encrypt = MessageDigest.getInstance("SHA256");

            Encrypt.update(Password.getBytes());
            byte[] Encryption = Encrypt.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : Encryption) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    //TODO
    public static String SQLInjectionPrevent() {
        return null;
    }

    //TODO Parameters
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

    public static boolean CheckValidLogin(String UserID, String Email, String Password) {
        if (isNotBlankOrEmpty(UserID) && isNotBlankOrEmpty(Email) && isNotBlankOrEmpty(Password)) {
            return Repository.UserFound(UserID, Email, Password);
        }
        return false;
    }

    public static boolean isUserIdAvailable(String UserID) {
        for (String ID : Repository.getUserIds()) {
            if (ID.equals(UserID)) return false;
        }
        return true;
    }

    public static boolean isNotBlankOrEmpty(String string) {
        return !string.isBlank() && !string.isEmpty();
    }

    //TODO review
    public static boolean isEmailFormatValid(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

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
}
