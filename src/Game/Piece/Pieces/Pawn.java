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
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc...)
     */
    public Pawn(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
    }

    /**
     * Takes a board object and calculates the available pawn moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc...
     *
     * The Pawn can move forwards and not backwards, It can only move one space at a time
     * unless it is it's first move, where it has the choice to move two spaces.
     * The pawn can only capture diagonally and can promote to a better piece if it reaches the
     * other end of the board. The pawn can do a special move called enPassant if an opposing pawn
     * moves two spaces on its first turn.
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board) {
        Square[][] BoardArray = board.getBoardArray();
        List<Square> PossibleDestinations = new ArrayList<>();

        //For each row in the board
        for (Square[] Row : BoardArray) {
            //For each square in the row
            for (Square square : Row) {
                //Calculate the vector displacement of the square from the piece in X and Y components
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();
                
                /*
                The pawn always moves 1 space forwards, or diagonal (when capturing), Therefore the
                Y displacement is always 1 for white and -1 for black (because forward for white is backwards for black)
                The absolute value of X Displacement is always <= 1,
                because it can move a maximum of 1 square either side when capturing or a minimum of 0 either side
                when not capturing.
                */
                if (getColour() == Colour.WHITE) {
                    if ((YDisplacement == -1) && (XDisplacement == 1)) {
                        PossibleDestinations.add(square);
                    } else if ((YDisplacement == -1) && (XDisplacement == 0)) {
                        if (!square.SquareOccupied()) {
                            PossibleDestinations.add(square);
                        }
                    } else if ((YDisplacement == -2) && (XDisplacement == 0)) {
                        /*
                        A white pawn starts on Y = 2, Therefore if the square two spaces ahead is
                        not occupied and the pawn has not yet moved, they can move 2 spaces.
                         */
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
                        /*
                        A black pawn starts on Y = 7, Therefore if the square two spaces ahead is
                        not occupied and the pawn has not yet moved, they can move 2 spaces.
                         */
                        if (!square.SquareOccupied() && getPieceCoordinate().getRank() == 7) {
                            PossibleDestinations.add(square);
                        }
                    }
                }
            }
        }

        /*
        Don't allow a pawn to jump over another piece if doing a double move. The PossibleDestinations
        array is updated in this method call if invalid moves are found.
         */
        CheckDoubleMoveCollision(BoardArray, PossibleDestinations);
        
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
            //If the move is not an EnPassant move (A special case where a pawn can move diagonally to an empty square)
            if (board.getEnPassantPawn() == null) {
                /*
                If the Square is empty and the X-Coordinate (File) of the piece is different from the X-Coordinate (File) of
                the destination Square (A pawn has moved diagonally if the X-coordinates are different) then
                remove this as a possible destination.
                 */
                if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                    toBeRemoved.add(square);
                }
            }
            /*
            If the destination is the EnPassant destination but the EnPassant pawn and capturing pawn are the same colour
            then remove the destination because you cannot capture your own pieces
             */
            else if (square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination()) && (colour == board.getEnPassantPawn().getColour())) {
                if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                    toBeRemoved.add(square);
                }
            }
            /*
            If the EnPassant pawn is not equal to null but the destination of the capturing pawn is not the EnPassant
            destination then remove the destination
             */
            else {
                if (!square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination())) {
                    if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()) {
                        toBeRemoved.add(square);
                    }
                }
            }
        }

        //Remove the invalid destinations from the list
        PossibleDestinations.removeAll(toBeRemoved);

        //Return the list of legal moves. Illegal moves are removed if they cause check of a players own king
        return removeIllegalMoves(board, DestinationsToMoves(PossibleDestinations, board));
    }

    /**
     * If a pawn can move twice, check that its path is not obstructed, if so, don't allow it to move twice.
     * Pawns can each only move twice on their first move.
     * The PossibleDestinations List is updated to a List of square objects which the
     * pawn can move to after checking whether it is eligible to move two spaces
     *
     * @param BoardArray           a 2-dimensional array of squares to represent the board
     * @param PossibleDestinations a List of squares which the pawn can move to
     */
    private void CheckDoubleMoveCollision(Square[][] BoardArray, List<Square> PossibleDestinations) {
        if ((getPieceCoordinate().getRank() == 2) || (getPieceCoordinate().getRank() == 7)) {
            //If the Pawn is white and in its starting position then:
            if (BoardArray[2][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                //If the square in front of the pawn is occupied, the pawn cannot move forwards so remove destination
                if (!BoardArray[3][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    PossibleDestinations.remove(BoardArray[3][getPieceCoordinate().getFile() - 1]);
                }
            }
            //If the pawn is black and in its starting position then:
            else if (BoardArray[5][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                //If the square in front of the pawn is occupied, the pawn cannot move forwards so remove destination
                if (!BoardArray[4][getPieceCoordinate().getFile() - 1].SquareOccupied()) {
                    PossibleDestinations.remove(BoardArray[4][getPieceCoordinate().getFile() - 1]);
                }
            }
        }
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

        //For each square in the list of possible destination squares
        for (Square square : PossibleDestinations) {
            /*
            If an EnPassant Pawn exists and the destination square is equal to the EnPassant destination square
            and the EnPassant pawn is not the same colour as the moving pawn then create a new EnPassant Move object
             */
            if ((board.getEnPassantPawn() != null) && (board.getEnPassantDestination().ReturnCoordinate().CompareCoordinates(square))
                    && (colour != board.getEnPassantPawn().colour)) {
                //En Passant Move
                Moves.add(new Move.EnPassantMove(PieceCoordinate.GetSquareAt(BoardArray), square, board.getEnPassantPawn(),
                        board.getEnPassantPawn().PieceCoordinate.GetSquareAt(BoardArray)));

            }
            /*
            If the destination square is occupied (a piece is being captured)
             */
            else if (square.SquareOccupied()) {
                /*
                If a white pawn has reached the blacks side of the board or
                if a black pawn has reached the white side of the board then the pawn can promote
                 */
                if ((square.ReturnCoordinate().getRank() == 8 && colour == Colour.WHITE) ||
                        (square.ReturnCoordinate().getRank() == 1 && colour == Colour.BLACK)) {
                    /*
                    Pawn Promotion Capture - The pawn can promote to one of 4 pieces so each of the below
                    moves are valid promotion moves into a Queen, Rook, Knight or Bishop.
                    It is a pawn promotion capture, so the pawn captures a piece and then can also promote
                     */
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Queen(square.ReturnCoordinate(), colour, PieceType.QUEEN), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Rook(square.ReturnCoordinate(), colour, PieceType.ROOK, CastlingAvailability.NEITHER), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Knight(square.ReturnCoordinate(), colour, PieceType.KNIGHT), square.ReturnPiece()));
                    Moves.add(new Move.PawnPromotionCapture(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Bishop(square.ReturnCoordinate(), colour, PieceType.BISHOP), square.ReturnPiece()));

                }
                else {
                    //Capturing move - Just a regular pawn capturing move
                    Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(BoardArray), square, square.ReturnPiece()));
                }
            }
            /*
            If the destination square is empty (no pieces are captured and the pawn does a regular move)
             */
            else {
                /*
                If a white pawn has reached the blacks side of the board or
                if a black pawn has reached the white side of the board then the pawn can promote
                 */
                if ((square.ReturnCoordinate().getRank() == 8 && colour == Colour.WHITE) ||
                        (square.ReturnCoordinate().getRank() == 1 && colour == Colour.BLACK)) {
                    /*
                    Pawn Promotion - The pawn can promote to one of 4 pieces so each of the below
                    moves are valid promotion moves into a Queen, Rook, Knight or Bishop.
                     */
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Queen(square.ReturnCoordinate(), colour, PieceType.QUEEN)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Rook(square.ReturnCoordinate(), colour, PieceType.ROOK, CastlingAvailability.NEITHER)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Knight(square.ReturnCoordinate(), colour, PieceType.KNIGHT)));
                    Moves.add(new Move.PawnPromotion(PieceCoordinate.GetSquareAt(BoardArray), square,
                            new Bishop(square.ReturnCoordinate(), colour, PieceType.BISHOP)));

                } else {
                    //General move - The pawn just moves forwards either on or two spaces without capturing a piece
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
}
