package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.CastlingAvailability;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    /**
     * Constructor for a pawn piece
     *
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour     The colour of a piece
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Pawn(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
    }

    /**
     * Takes a board object and calculates the available pawn moves so that
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
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();
                
                /*
                The pawn always moves 1 in forwards, or diagonal when capturing, Therefore the
                Y displacement is always 1 for white and -1 for black (because forward for white is backwards for black)
                The absolute value of X Displacement is always <= 1,
                because it can move a maximum of 1 square either side when capturing.
                */
                if (getColour() == Colour.WHITE) {
                    if ((YDisplacement == -1) && (XDisplacement == 1)) {
                        PossibleDestinations.add(square);
                    } else if ((YDisplacement == -1) && (XDisplacement == 0)) {
                        if (!square.SquareOccupied()) {
                            PossibleDestinations.add(square);
                        }
                    } else if ((YDisplacement == -2) && (XDisplacement == 0)) {
                        //A white pawn starts on Y = 2. Therefore if the square is not occupied and the pawn has not yet moved, they can move 2 spaces.
                        if ((!square.SquareOccupied()) && (getPieceCoordinate().getRank() == 2)) {
                            PossibleDestinations.add(square);
                        }
                    }
                } else {
                    if ((YDisplacement == 1) && (XDisplacement == 1)) {
                        PossibleDestinations.add(square);
                    } else if ((YDisplacement == 1) && (XDisplacement == 0)) {
                        if (!square.SquareOccupied()) {
                            PossibleDestinations.add(square);
                        }
                    } else if ((YDisplacement == 2) && (XDisplacement == 0)) {
                        //A white pawn starts on Y = 2. Therefore if the square is not occupied and the pawn has not yet moved, they can move 2 spaces.
                        if (!square.SquareOccupied() && getPieceCoordinate().getRank() == 7) {
                            PossibleDestinations.add(square);
                        }
                    }
                }
            }
        }

        PossibleDestinations = CheckDoubleMoveCollision(BoardArray, PossibleDestinations);
        
        /*
        Remove squares which cannot be
        captured (because a piece of equal colour occupies it)
        */
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        
        /*
        Remove diagonals if they are empty because a pawn can only move diagonal if they are capturing another piece
         */
        List<Square> toBeRemoved = new ArrayList<>();
        for (Square square : PossibleDestinations) {
            if (board.getEnPassantPawn() == null) {
                if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                    toBeRemoved.add(square);
                }
            } else if (square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination()) && (colour == board.getEnPassantPawn().getColour())) {
                if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                    toBeRemoved.add(square);
                }
            } else {
                if (!square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination())) {
                    if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                        toBeRemoved.add(square);
                    }
                }
            }
        }

        PossibleDestinations.removeAll(toBeRemoved);

        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, board));
    }

    /**
     * If a pawn can move twice, check that its path is not obstructed, if so, don't allow it to move twice.
     * Pawns can each only move twice on their first move.
     *
     * @param BoardArray           a 2-dimensional array of squares to represent the board
     * @param PossibleDestinations a List of squares which the pawn can move to
     * @return a List of square objects which the pawn can move to after checking whether it is eligible to move two spaces
     */
    private List<Square> CheckDoubleMoveCollision(Square[][] BoardArray, List<Square> PossibleDestinations) {
        if ((getPieceCoordinate().getRank() == 2) || (getPieceCoordinate().getRank() == 7)) {
            if (BoardArray[2][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                if (!BoardArray[3][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    PossibleDestinations.remove(BoardArray[3][getPieceCoordinate().getFile() - 1]);
                }
            } else if (BoardArray[5][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                if (!BoardArray[4][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    PossibleDestinations.remove(BoardArray[4][getPieceCoordinate().getFile() - 1]);
                }
            }
        }

        return PossibleDestinations;
    }

    /**
     * Takes the possible destinations a piece can move to and converts these to move objects
     *
     * @param PossibleDestinations a List of squares which are available for the piece to move to
     * @param board                an instance of the board class to access the enPassant Pawn and boardArray
     * @return a List of valid moves
     */
    private List<Move> DestinationsToMoves(final List<Square> PossibleDestinations, final Board board) {
        List<Move> Moves = new ArrayList<>();
        Square[][] BoardArray = board.getBoardArray();

        for (Square square : PossibleDestinations) {
            if ((board.getEnPassantPawn() != null) && (board.getEnPassantDestination().ReturnCoordinate().CompareCoordinates(square))
                    && (colour != board.getEnPassantPawn().colour)) {
                //En Passant Move
                Moves.add(new Move.EnPassantMove(PieceCoordinate.GetSquareAt(BoardArray), square, board.getEnPassantPawn(),
                        board.getEnPassantPawn().PieceCoordinate.GetSquareAt(BoardArray)));

            } else if (square.SquareOccupied()) {
                if ((square.ReturnCoordinate().getRank() == 8 && colour == Colour.WHITE) || (square.ReturnCoordinate().getRank() == 1 && colour == Colour.BLACK)) {
                    //Pawn Promotion Capture
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Queen(square.ReturnCoordinate(), colour, PieceType.QUEEN), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Rook(square.ReturnCoordinate(), colour, PieceType.ROOK, CastlingAvailability.NEITHER), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Knight(square.ReturnCoordinate(), colour, PieceType.KNIGHT), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Bishop(square.ReturnCoordinate(), colour, PieceType.BISHOP), square.ReturnPiece()));

                } else {
                    //Capturing move
                    Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(BoardArray), square, square.ReturnPiece()));
                }
            } else {
                if ((square.ReturnCoordinate().getRank() == 8 && colour == Colour.WHITE) || (square.ReturnCoordinate().getRank() == 1 && colour == Colour.BLACK)) {
                    //Pawn Promotion
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Queen(square.ReturnCoordinate(), colour, PieceType.QUEEN)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Rook(square.ReturnCoordinate(), colour, PieceType.ROOK, CastlingAvailability.NEITHER)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Knight(square.ReturnCoordinate(), colour, PieceType.KNIGHT)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Bishop(square.ReturnCoordinate(), colour, PieceType.BISHOP)));

                } else {
                    //General move
                    Moves.add(new Move.RegularMove(PieceCoordinate.GetSquareAt(BoardArray), square));
                }
            }
        }
        return Moves;
    }

    /**
     * Converts the type of piece to its Notation equivalent
     *
     * @return a String with the type notation (Pawn = null)
     */
    @Override
    public String PieceTypeToNotation() {
        return "";
    }

    /**
     * @return Returns the Unicode character of the piece type.
     */
    @Override
    public String ReturnPieceIcon() {
        return "♟";
    }

    //TODO pawn promotion handling
}
