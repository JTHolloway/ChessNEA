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
     * @param type       The piece type which is inheriting from the piece class (King, Queen Bishop etc...)
     */
    public King(Coordinate coordinate, Colour colour, PieceType type) {
        super(coordinate, colour, type);
        this.castlingAvailability = CastlingAvailability.BOTH;
    }

    /**
     * Takes a board object and calculates the available king moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc...
     *
     * The King can move 1 space in all directions but cannot move into a threatened
     * square and can do a special castling move if some conditions are met.
     *
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board) {
        Square[][] BoardArray = board.getBoardArray();
        List<Square> PossibleDestinations = new ArrayList<>();

        //For each row in the board
        for (Square[] row : BoardArray) {
            //For each square in the row
            for (Square square : row) {
                //Calculate the vector displacement of the square from the piece in X and Y components
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
    
        /*Remove the Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        PossibleDestinations.removeIf(square -> Game.isThreatenedSquare(this.colour, square, board)); //Lambda Expression

        //QueenSide Castling - Can also be performed if castling availability == BOTH.
        if (castlingAvailability == CastlingAvailability.QUEEN_SIDE || castlingAvailability == CastlingAvailability.BOTH) {
            Square[][] boardArray = board.getBoardArray();
            /*
            The rank is the Y-Coordinate on the board. Its value depends on of whether
            a white or black king is castling since they start of different sides of the board
            */
            int rank = colour == Colour.WHITE ? 0 : 7;

            /*
            If the correct colour rook is in its correct position, and the squares between the rook and
            king are empty, and the squares the king will move through are not threatened by the opposing pieces,
            Then the castling move is valid
             */
            if (boardArray[rank][0].SquareOccupied()) {
                if (boardArray[rank][0].ReturnPiece() instanceof Rook
                        && boardArray[rank][0].ReturnPiece().getColour() == colour
                        && !boardArray[rank][1].SquareOccupied()
                        && !boardArray[rank][2].SquareOccupied()
                        && !boardArray[rank][3].SquareOccupied()
                        && !Game.isKingChecked(colour, board)
                        && !Game.isThreatenedSquare(colour, boardArray[rank][2], board)
                        && !Game.isThreatenedSquare(colour, boardArray[rank][3], board)) {

                    PossibleDestinations.add(boardArray[rank][2]);
                }
            }
        }

        //KingSide Castling - Can also be performed if castling availability == BOTH.
        if (castlingAvailability == CastlingAvailability.KING_SIDE || castlingAvailability == CastlingAvailability.BOTH) {
            Square[][] boardArray = board.getBoardArray();
            /*
            The rank is the Y-Coordinate on the board. Its value depends on of whether
            a white or black king is castling since they start of different sides of the board
            */
            int rank = colour == Colour.WHITE ? 0 : 7;

            /*
            If the correct colour rook is in its correct position, and the squares between the rook and
            king are empty, and the squares the king will move through are not threatened by the opposing pieces,
            Then the castling move is valid
             */
            if (boardArray[rank][7].SquareOccupied()) {
                if (boardArray[rank][7].ReturnPiece() instanceof Rook
                        && boardArray[rank][7].ReturnPiece().getColour() == colour
                        && !boardArray[rank][6].SquareOccupied()
                        && !boardArray[rank][5].SquareOccupied()
                        && !Game.isKingChecked(colour, board)
                        && !Game.isThreatenedSquare(colour, boardArray[rank][6], board)
                        && !Game.isThreatenedSquare(colour, boardArray[rank][5], board)) {

                    PossibleDestinations.add(boardArray[rank][6]);
                }
            }
        }

        /*
        Return the list of legal moves.
        Illegal moves are removed if they cause check of a players own king,
        meaning that the king cannot move into a checked square.
         */
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
}
