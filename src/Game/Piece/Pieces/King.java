package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.CastlingAvailability;
import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private CastlingAvailability castlingAvailability;

    /**
     * Constructor for a king piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public King(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
        this.castlingAvailability = CastlingAvailability.BOTH;
    }

    /**
     * Takes a board object and calculates the available king moves so that
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

        for (Square[] row : BoardArray) {
            for (Square square : row) {
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = Math.abs(getPieceCoordinate().getRank() - Destination.getRank());
    
                /*Check if max displacement = 1 because a king
                can move a maximum of 1 square away from origin in any direction*/
                if (Math.max(XDisplacement, YDisplacement) == 1) {
                    PossibleDestinations.add(square);
                }
            }
        }
    
        /*Remove Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        PossibleDestinations.removeIf(square -> Game.isThreatenedSquare(this.colour, square, board)); //Lambda Expression

        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, BoardArray));

    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (King = K)
     */
    @Override
    public String PieceTypeToNotation() {
        return "K";
    }

    /**
     * @return Returns the Unicode character of the piece type.
     */
    public String ReturnPieceIcon() {
        return "♚";
    }

    public CastlingAvailability getCastlingAvailability() {
        return castlingAvailability;
    }

    public void setCastlingAvailability(CastlingAvailability castlingAvailability) {
        this.castlingAvailability = castlingAvailability;
    }


//TODO Check, CheckMate and StaleMate handling
}
