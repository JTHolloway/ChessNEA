package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import LibaryFunctions.Utility;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    /**
     * Constructor for a queen piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc...)
     */
    public Queen(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
    }

    /**
     * Takes a board object and calculates the available queen moves so that illegal moves cannot be made
     * Takes into account that check may be present on the board etc...
     *
     * The Queen can move any number of spaces, in any direction, as long as it is the same direction
     * Eg - the queen can move diagonally 5 spaces but cannot change its direction in the same move
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board) {
        Square[][] BoardArray = board.getBoardArray();

        /*
        Creates a List of all possible destinations which consists of all the moves calculated in both the
        CalculateDiagonals() and CalculateStraights() methods.
         */
        List<Square> PossibleDestinations = CalculateDiagonals(BoardArray);
        PossibleDestinations.addAll(CalculateStraights(BoardArray));
        
        /*Remove the Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);

        //Return the list of legal moves. Illegal moves are removed if they cause check of a players own king
        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, BoardArray));
    }

    /**
     * Calculates the valid diagonal moves of the queen
     *
     * @param BoardArray a 2-Dimensional array of squares so that the diagonal line that the piece occupies can be found
     *                   in the board array
     * @return a list of possible destination squares
     */
    private List<Square> CalculateDiagonals(final Square[][] BoardArray) {
        //Find Diagonal destinations
        List<Square> PositiveDiagonal = new ArrayList<>();
        List<Square> NegativeDiagonal = new ArrayList<>();

        //For each row in the board
        for (Square[] Row : BoardArray) {
            //For each square in the row
            for (Square square : Row) {
                //Calculate the vector displacement of the square from the piece in X and Y components
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = getPieceCoordinate().getFile() - Destination.getFile();
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();

                /* If the displacement is not 0 (hence not the pieces current square) then
                if the magnitude of the X and Y are equal, it must be a diagonal square Eg - 1 up and 1 across. */
                if ((XDisplacement != 0) && Math.abs(XDisplacement) == Math.abs(YDisplacement)) {
                    //If the diagonal has positive gradient then add to the PositiveDiagonal list
                    if (YDisplacement / XDisplacement == 1) {
                        PositiveDiagonal.add(square);
                    }
                    //If the diagonal has negative gradient then add to the NegativeDiagonal list
                    else if (YDisplacement / XDisplacement == -1) {
                        NegativeDiagonal.add(square);
                    }
                } else if ((XDisplacement == 0) && (YDisplacement == 0)) {
                    /* Adds the pieces current square to both array lists. This is done so that it can be used as a
                    pivot when calling the CheckCollisions() Method. Although this square is removed later.*/
                    PositiveDiagonal.add(square);
                    NegativeDiagonal.add(square);
                }
            }
        }

        //Checks and removes any squares which cannot be legally accessed
        PositiveDiagonal = CheckCollisions(PositiveDiagonal);
        NegativeDiagonal = CheckCollisions(NegativeDiagonal);

        //Adds all valid diagonal destinations to one list
        List<Square> PossibleDestinations = PositiveDiagonal;
        PossibleDestinations.addAll(NegativeDiagonal);

        //Return all diagonal destinations
        return PossibleDestinations;
    }

    /**
     * Calculates the valid horizontal and vertical (straight) line moves of the queen
     *
     * @param BoardArray a 2-Dimensional array of squares so that the straight line that the piece occupies can be found
     *                   in the board array
     * @return a list of possible destination squares
     */
    private List<Square> CalculateStraights(final Square[][] BoardArray) {
        List<Square> PossibleDestinations = new ArrayList<>();

        //Uses the methods in the utility class to return arrays for the both row and column which contain the queen
        List<Square> Row = Utility.ArrayToRow(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));
        List<Square> Column = Utility.ArrayToColumn(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));

        //Check For any path obstructions regardless of piece colour
        Column = CheckCollisions(Column);
        Row = CheckCollisions(Row);
        PossibleDestinations.addAll(Column);
        PossibleDestinations.addAll(Row);

        //Return all horizontal and vertical destinations
        return PossibleDestinations;
    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (Queen = Q)
     */
    @Override
    public String PieceTypeToNotation() {
        return "Q";
    }

    /**
     * @return Returns the Unicode character of the piece type.
     */
    @Override
    public String ReturnPieceIcon() {
        return "♛";
    }
}
