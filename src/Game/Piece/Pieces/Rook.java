package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.CastlingAvailability;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import LibaryFunctions.Utility;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private final CastlingAvailability castlingAvailability;

    /**
     * Constructor for a rook piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Rook(Coordinate coordinate, Colour colour, PieceType type, CastlingAvailability castlingAvailability) {
        super(coordinate, colour, type);
        this.castlingAvailability = castlingAvailability;
    }

    /**
     * Takes a board object and calculates the available Rook moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc..
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board) {
        Square[][] BoardArray = board.getBoardArray();
        List<Square> PossibleDestinations = new ArrayList<>();

        List<Square> Row = Utility.ArrayToRow(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));
        List<Square> Column = Utility.ArrayToColumn(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));

        //Check For any path obstructions regardless of piece colour
        Column = CheckCollisions(Column);
        Row = CheckCollisions(Row);
        PossibleDestinations.addAll(Column);
        PossibleDestinations.addAll(Row);

        /*Remove the Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);

        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, BoardArray));
    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (Rook = R)
     */
    @Override
    public String PieceTypeToNotation() {
        return "R";
    }

    /**
     * @return Returns the Unicode character of the piece type.
     */
    @Override
    public String ReturnPieceIcon() {
        return "♜";
    }

    public CastlingAvailability getCastlingAvailability() {
        return castlingAvailability;
    }
}
