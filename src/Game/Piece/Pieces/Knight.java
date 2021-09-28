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

public class Knight extends Piece {
    /**
     * Constructor for a knight piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Knight(final Coordinate coordinate, final Colour colour, final PieceType type) {
        super(coordinate, colour, type);
    }

    /**
     * Takes a board object and calculates the available Knight moves so that
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

        for (Square[] Row : BoardArray) {
            for (Square square : Row) {
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = Math.abs(getPieceCoordinate().getRank() - Destination.getRank());
                
                /*
                The knight always moves two squares in one direction and then 1 square perpendicular, Therefore the
                product of x and y displacements is always 1*2 = 2
                */
                if (XDisplacement * YDisplacement == 2) {
                    PossibleDestinations.add(square);
                }
            }
        }

        //TODO Look for check and remove any squares which don't remove check
        
        /*Remove Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);

        return DestinationsToMoves(PossibleDestinations, BoardArray);
    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (Knight = N)
     */
    @Override
    public String PieceTypeToNotation() {
        return "N";
    }
}
