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
                The pawn always moves two 1 in forwards, or diagonal when capturing, Therefore the
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
            } else {
                if (!square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination())) {
                    if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                        toBeRemoved.add(square);
                    }
                }
            }
        }

        PossibleDestinations.removeAll(toBeRemoved);

        //TODO Look for check and remove any squares which don't remove check

        return DestinationsToMoves(PossibleDestinations, board);

    }

    /**
     * If a pawn can move twice, check that its path is not obstructed, if so, don't allow it to move twice.
     * Pawns can each only move twice on their first move.
     *
     * @param BoardArray           a 2-dimensional array of squares to represent the board
     * @param PossibleDestinations a List of squares which the pawn can move to
     * @return a List of square objects which the pawn can move to after checking weather it is eligible to move two spaces
     */
    private List<Square> CheckDoubleMoveCollision(Square[][] BoardArray, List<Square> PossibleDestinations) {
        //TODO remove test print statements
        if ((getPieceCoordinate().getRank() == 2) || (getPieceCoordinate().getRank() == 7)) {
            if (BoardArray[2][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                if (!BoardArray[3][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    System.out.println(BoardArray[3][getPieceCoordinate().getFile() - 1].ReturnCoordinate().CoordinateToNotation());
                    PossibleDestinations.remove(BoardArray[3][getPieceCoordinate().getFile() - 1]);
                }
            } else if (BoardArray[5][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                if (!BoardArray[4][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    System.out.println(BoardArray[4][getPieceCoordinate().getFile() - 1].ReturnCoordinate().CoordinateToNotation());
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
            if ((board.getEnPassantPawn() != null) && (board.getEnPassantDestination().ReturnCoordinate().CompareCoordinates(square))) {
                Square EnPassantDestination = board.getEnPassantDestination();

                if (square.ReturnCoordinate().CompareCoordinates(EnPassantDestination)) {
                    //En Passant Move
                    Moves.add(new Move.EnPassantMove(PieceCoordinate.GetSquareAt(BoardArray), square, board.getEnPassantPawn(),
                            board.getEnPassantPawn().PieceCoordinate.GetSquareAt(BoardArray)));
                }
            } else if (square.SquareOccupied()) {
                //Capturing move
                Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(BoardArray), square, square.ReturnPiece()));
            } else {
                //General move
                Moves.add(new Move.RegularMove(PieceCoordinate.GetSquareAt(BoardArray), square));
            }
        }
        return Moves;
    }

    /**
     * If a pawn was moved, check if the pawn moved 2 spaces, if so then it is the new en passant pawn
     *
     * @param board    The board stores data about the En Passant Pawn
     * @param moveMade The move that the player or computer made, used to see if it were a 2 space pawn move
     */
    public void PawnMoved(Board board, Move moveMade) {
        int XDisplacement = Math.abs(moveMade.getStartPosition().ReturnCoordinate().getFile() - moveMade.getEndPosition().ReturnCoordinate().getFile());
        int YDisplacement = moveMade.getStartPosition().ReturnCoordinate().getRank() - moveMade.getEndPosition().ReturnCoordinate().getRank();

        if (XDisplacement == 0) {
            if ((YDisplacement == -2) && moveMade.getMovedPiece().getColour() == Colour.WHITE) {
                board.setEnPassantPawn(this);
            } else if ((YDisplacement == 2) && moveMade.getMovedPiece().getColour() == Colour.BLACK) {
                board.setEnPassantPawn(this);
            }
        }
    }

    /**
     * The Pawn is much different from the other pieces, Unlike other pieces such as the rook where any of its destinations
     * is being checked, The Pawn instead only checks the squares in front-diagonal to itself, So the
     * 'CalculateValidMoves()' method cannot be used to find checked squares, because the forwards straight
     * move is not a checking move.
     *
     * @return A list of squares Forwards and Diagonal to the Pawn
     */
    public List<Square> FindCheckingSquares(Board board) {
        Square[][] BoardArray = board.getBoardArray();
        List<Square> CheckedDestinations = new ArrayList<>();

        for (Square[] Row : BoardArray) {
            for (Square square : Row) {
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();

                if (getColour() == Colour.WHITE) {
                    if ((YDisplacement == -1) && (XDisplacement == 1)) {
                        CheckedDestinations.add(square);
                    }
                } else {
                    if ((YDisplacement == 1) && (XDisplacement == 1)) {
                        CheckedDestinations.add(square);
                    }
                }
            }
        }

        return CheckedDestinations;
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

    //TODO pawn promotion handling
    //TODO double move handling
}
