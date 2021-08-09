package LibaryFunctions;

import Game.Board.Square;

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

    public static String hashPassword(String password) {
        //TODO hash password
        return password;
    }

    public static boolean CompareHashed(String StoredPassword, String EnteredPassword) {
        return hashPassword(EnteredPassword).equals(StoredPassword);
    }

    //TODO
    public static String SQLInjectionPrevent() {
        return null;
    }

    //TODO
    public static boolean CheckValidLogin() {
        return true;
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
