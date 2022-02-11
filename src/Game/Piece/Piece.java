package Game.Piece;

import Game.Board.Board;
import Game.Board.Square;
import Game.CastlingAvailability;
import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.Move.Move;
import Game.Piece.Pieces.King;
import Game.Piece.Pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected final Colour colour;
    protected final PieceType type;
    protected Coordinate PieceCoordinate;

    /**
     * Constructor for a piece
     *
     * @param PieceCoordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour          The colour of a piece
     * @param type            The piece type which is inheriting from the piece class (King, Queen Bishop etc...)
     */
    public Piece(Coordinate PieceCoordinate, Colour colour, PieceType type) {
        this.PieceCoordinate = PieceCoordinate;
        this.colour = colour;
        this.type = type;
    }

    /**
     * Takes a board object and calculates the available moves of the current piece
     * Takes into account that check may be present on the board etc...
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    public abstract List<Move> CalculateValidMoves(final Board board);

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (e.g. Queen = Q)
     */
    public abstract String PieceTypeToNotation();

    /**
     * @return The unicode character corresponding to a certain piece type
     */
    public abstract String ReturnPieceIcon();

    /**
     * Checks to make sure all possible destinations can be accessed by checking if other pieces obstruct the path
     * and removes them from the list possible destinations.
     *
     * @param OneDimensionalArray a one-dimensional section or row of the board array which contains the piece
     * @return a list of squares excluding any squares behind any obstructing pieces
     */
    protected List<Square> CheckCollisions(List<Square> OneDimensionalArray) {
        int PiecePositionIndex = 0;

        //Find the index of the moving piece
        for (int i = 0; i < OneDimensionalArray.size(); i++) {
            Square square = OneDimensionalArray.get(i);
            if (getPieceCoordinate().CompareCoordinates(square)) {
                PiecePositionIndex = i;
                break;
            }
        }

        //Loop back to find the nearest path obstruction backwards
        int BackIndex = -1;
        for (int i = PiecePositionIndex; i >= 0; i--) {
            Square square = OneDimensionalArray.get(i);
            if (i == 0 && BackIndex == -1) {
                BackIndex = i;
            } else if (square.SquareOccupied() && i != PiecePositionIndex) {
                BackIndex = i;
                break;
            }
        }

        //Loop through to find the nearest path obstruction Forwards
        int FrontIndex = -1;
        for (int i = PiecePositionIndex; i < OneDimensionalArray.size(); i++) {
            Square square = OneDimensionalArray.get(i);
            if (i == OneDimensionalArray.size() - 1 && FrontIndex == -1) {
                FrontIndex = i;
            } else if (square.SquareOccupied() && i != PiecePositionIndex) {
                FrontIndex = i;
                break;
            }
        }

        /*
        Returns a sublist between the two possible collisions,
        therefore the moving piece can only move in between the other pieces and not past them
         */
        OneDimensionalArray = OneDimensionalArray.subList(BackIndex, FrontIndex + 1);
        return OneDimensionalArray;
    }

    /**
     * Remove Square that moving piece is occupying and squares which
     * cannot be captured (because a piece of equal colour occupies it)
     *
     * @param PossibleDestinations A list of possible destinations which also includes invalid moves such as
     *                             destinations with a piece of the same colour
     * @return a list of squares which contains only valid moves
     */
    protected List<Square> RemoveRemainingInvalidDestinations(List<Square> PossibleDestinations) {
        List<Square> ToBeRemoved = new ArrayList<>();
        for (Square square : PossibleDestinations) {
            if (getPieceCoordinate().CompareCoordinates(square)) {
                ToBeRemoved.add(square);
            } else if (square.SquareOccupied()) {
                if ((square.ReturnPiece().getColour() == getColour()) || (square.ReturnPiece() instanceof King)) {
                    ToBeRemoved.add(square);
                }
            }
        }
        PossibleDestinations.removeAll(ToBeRemoved);
        return PossibleDestinations;
    }

    /**
     * Takes the possible destinations a piece can move to and converts these to move objects
     *
     * @param PossibleDestinations a List of squares which are available for the piece to move to
     * @param Board                a 2-Dimensional square array to represent the board
     * @return a List of valid moves
     */
    protected List<Move> DestinationsToMoves(final List<Square> PossibleDestinations, final Square[][] Board) {
        List<Move> Moves = new ArrayList<>();
        for (Square square : PossibleDestinations) {
            if ((type == PieceType.KING) && (PieceCoordinate.getFile() - square.ReturnCoordinate().getFile() == 2)) {
                //Queen-side Castle
                int rank = colour == Colour.WHITE ? 0 : 7;
                Moves.add(new Move.CastlingMove(PieceCoordinate.GetSquareAt(Board), square, Board[rank][0].ReturnPiece(),
                        CastlingAvailability.QUEEN_SIDE, Board[rank][3].ReturnCoordinate()));
            } else if ((type == PieceType.KING) && (PieceCoordinate.getFile() - square.ReturnCoordinate().getFile() == -2)) {
                //King-side Castle
                int rank = colour == Colour.WHITE ? 0 : 7;
                Moves.add(new Move.CastlingMove(PieceCoordinate.GetSquareAt(Board), square, Board[rank][7].ReturnPiece(),
                        CastlingAvailability.KING_SIDE, Board[rank][5].ReturnCoordinate()));
            } else {
                if (square.SquareOccupied()) {
                    //Capturing move
                    Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(Board), square, square.ReturnPiece()));
                } else {
                    //General move
                    Moves.add(new Move.RegularMove(PieceCoordinate.GetSquareAt(Board), square));
                }
            }
        }
        return Moves;
    }

    /**
     * If a move results in the players king in check then it is not legal because you
     * cannot check your own king
     *
     * @param moves The list of move objects to be checked
     * @return A list of move objects with illegal moves removed
     */
    protected List<Move> removeIllegalMoves(Board board, List<Move> moves) {
        List<Move> illegalMoves = new ArrayList<>();

        if (!moves.isEmpty() && moves.get(0).getMovedPiece() != null) {
            King king = (moves.get(0).getMovedPiece().getColour() == Colour.WHITE) ? (King) board.getKings()[0] : (King) board.getKings()[1];
            final CastlingAvailability castlingAvailability = king.getCastlingAvailability();
            final Pawn enPassantPawn = board.getEnPassantPawn();

            for (Move move : moves) {
                Game.MakeMove(move, board);
                if (Game.isKingChecked(this.colour, board)) {
                    Game.reverseMove(move, board, castlingAvailability, enPassantPawn);
                    illegalMoves.add(move);

                } else {
                    Game.reverseMove(move, board, castlingAvailability, enPassantPawn);
                }
            }
            moves.removeAll(illegalMoves);
        }
        return moves;
    }

    /**
     * @return a coordinate object of the pieces current coordinate
     */
    public Coordinate getPieceCoordinate() {
        return PieceCoordinate;
    }

    /**
     * Setter method for Coordinate (a pieces coordinate can change but its type and colour cannot)
     *
     * @param pieceCoordinate The coordinate being assigned to the piece
     */
    public void setPieceCoordinate(Coordinate pieceCoordinate) {
        this.PieceCoordinate = pieceCoordinate;
    }

    /**
     * @return a colour referring to the colour of the piece. White/Black
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * @return the type of the piece. eg - rook
     */
    public PieceType getType() {
        return type;
    }
}
