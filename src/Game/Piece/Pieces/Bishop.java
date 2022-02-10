package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    /**
     * Constructor for a bishop piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc...)
     */
    public Bishop(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
    }

    /**
     * Takes a board object and calculates the available bishop moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc...
     *
     * Bishops can move any number of spaces along a diagonal
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(final Board board) {
        //Find Diagonal destinations
        List<Square> PositiveDiagonal = new ArrayList<>();
        List<Square> NegativeDiagonal = new ArrayList<>();
        Square[][] BoardArray = board.getBoardArray();

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

        //Adds all valid destinations to one list
        List<Square> PossibleDestinations = PositiveDiagonal;
        PossibleDestinations.addAll(NegativeDiagonal);
        
        /*Remove the Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);

        //Return the list of legal moves. Illegal moves are removed if they cause check of a players own king
        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, BoardArray));
    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (Bishop = B)
     */
    @Override
    public String PieceTypeToNotation() {
        return "B";
    }

    /**
     * @return Returns the Unicode character of the piece type.
     */
    public String ReturnPieceIcon() {
        return "♝";
    }
}
