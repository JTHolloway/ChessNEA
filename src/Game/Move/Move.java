package Game.Move;

import Game.Board.Square;
import Game.Piece.Piece;
import org.jetbrains.annotations.NotNull;

public class Move
{

    @NotNull protected final Square StartPosition;
    @NotNull protected final Square EndPosition;
    @NotNull protected final Piece MovedPiece;

    /**
     * Constructor for a generic move
     * @param startPosition Origin Square of piece
     * @param endPosition Destination Square of piece
     */
    public Move(final Square startPosition, final Square endPosition)
    {
        StartPosition = startPosition;
        EndPosition = endPosition;
        MovedPiece = StartPosition.ReturnPiece();
    }

    /**
     * Converts a move to algebraic chess Notation
     * @return a String in chess notation
     */
    public String ToMoveNotation()
    {
        // TODO ToMoveNotation() method
        boolean Unambiguous;
        boolean Capture;
        return null;
    }

    /*Getter methods for each member variable*/
    public Square getStartPosition() {
        return StartPosition;
    }
    public Square getEndPosition() {
        return EndPosition;
    }
    public Piece getMovedPiece() {
        return MovedPiece;
    }


//...............................................................................................................
    
    /**
     * Capturing move Inner class used to uniquely identify
     * properties of a Capture
     */
    public class CapturingMove extends Move
    {

        @NotNull private final Piece CapturedPiece;

        /**
         * Constructor for a Capturing move. Calls super class constructor
         * @param startPosition Origin Square of piece
         * @param endPosition   Destination Square of piece
         * @param capturedPiece The piece object that was captured
         */
        public CapturingMove(final Square startPosition, final Square endPosition, final Piece capturedPiece)
        {
            super(startPosition, endPosition);
            CapturedPiece = capturedPiece;
        }

        /**
         * Returns the piece that was captured
         * @return a Piece object of the captured piece
         */
        public Piece getCapturedPiece()
        {
            return CapturedPiece;
        }
    }

//...............................................................................................................
    
    /**
     * Castling move Inner class used to uniquely identify
     * properties of a Castling move
     */
    public abstract class CastlingMove extends Move
    {

        private final Piece RookToMove;
        private final Square RookOriginSquare;
        private final Square RookDestinationSquare;

        /**
         * Constructor for a Castling move. Calls super class constructor
         * @param startPosition Origin Square of King
         * @param endPosition   Destination Square of King
         * @param rookOriginSquare Origin Square of Rook
         * @param rookDestinationSquare Destination Square of Rook
         */
        public CastlingMove(final Square startPosition, final Square endPosition, final Square rookOriginSquare, final Square rookDestinationSquare)
        {
            super(startPosition, endPosition);
            RookToMove = rookOriginSquare.ReturnPiece();
            RookOriginSquare = rookOriginSquare;
            RookDestinationSquare = rookDestinationSquare;
        }

        /**
         * Returns the chess notation for the castle
         * @return a String containing notation for a castling move
         */
        public abstract String CastlingNotation();


        /**
         * Queen-side Castle is a subclass of the castling move class
         * to uniquely identify a queen-side castle
         */
        public class QueenSideCastle extends CastlingMove
        {
            /**
             * Constructor for a Castling move. Calls super class constructor
             * @param startPosition Origin Square of King
             * @param endPosition   Destination Square of King
             * @param rookOriginSquare Origin Square of Rook
             * @param rookDestinationSquare Destination Square of Rook
             */
            public QueenSideCastle(final Square startPosition, final Square endPosition, final Square rookOriginSquare, final Square rookDestinationSquare) {
                super(startPosition, endPosition, rookOriginSquare, rookDestinationSquare);
            }

            /**
             * Translates the castle to chess notation
             * @return a String representing a queen-side castle in chess notation
             */
            @Override
            public String CastlingNotation()
            {
                return "O-O-O";
            }
        }
    
    
        /**
         * King-side Castle is a subclass of the castling move class
         * to uniquely identify a king-side castle
         */
        public class KingSideCastle extends CastlingMove
        {
            /**
             * Constructor for a Castling move. Calls super class constructor
             * @param startPosition Origin Square of King
             * @param endPosition   Destination Square of King
             * @param rookOriginSquare Origin Square of Rook
             * @param rookDestinationSquare Destination Square of Rook
             */
            public KingSideCastle(final Square startPosition, final Square endPosition, final Square rookOriginSquare, final Square rookDestinationSquare) {
                super(startPosition, endPosition, rookOriginSquare, rookDestinationSquare);
            }
        
            /**
             * Translates the castle to chess notation
             * @return a String representing a king-side castle in chess notation
             */
            @Override
            public String CastlingNotation()
            {
                return "O-O";
            }
        }
    }
}