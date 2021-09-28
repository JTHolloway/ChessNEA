package Game.Move;

import Game.Board.Square;
import Game.Piece.Piece;

public abstract class Move {

    protected final Square StartPosition;
    protected final Square EndPosition;
    protected final Piece MovedPiece;

    /**
     * Constructor for a move
     *
     * @param startPosition Origin Square of piece
     * @param endPosition   Destination Square of piece
     */
    public Move(final Square startPosition, final Square endPosition) {
        StartPosition = startPosition;
        EndPosition = endPosition;
        MovedPiece = StartPosition.ReturnPiece();
    }

    /**
     * Converts a move to algebraic chess Notation
     *
     * @return a String in chess notation
     * @// TODO: 13/07/2021 ToMoveNotation() method
     */
    public String ToMoveNotation() {
        boolean Unambiguous;
        boolean Capture;
        return null;
    }

    /**
     * Returns the piece that was captured
     *
     * @return null if no piece was captured
     */
    public abstract Piece getCapturedPiece();

    /**
     * @return a boolean value depending on weather it was a capturing move
     */
    public abstract boolean wasCapture();

    /**
     * @return the starting square of a piece before it has moved
     */
    public Square getStartPosition() {
        return StartPosition;
    }

    /**
     * @return the destination square of the piece that was moved
     */
    public Square getEndPosition() {
        return EndPosition;
    }

    /**
     * @return the piece that was moved
     */
    public Piece getMovedPiece() {
        return MovedPiece;
    }


//...............................................................................................................

    /**
     * Class for a Capturing move: A move in which an opponents piece was captured
     */
    public static class CapturingMove extends Move {

        private final Piece CapturedPiece;

        /**
         * Constructor for a Capturing move. Calls super class constructor
         *
         * @param startPosition Origin Square of piece
         * @param endPosition   Destination Square of piece
         * @param capturedPiece The piece object that was captured
         */
        public CapturingMove(final Square startPosition, final Square endPosition, final Piece capturedPiece) {
            super(startPosition, endPosition);
            CapturedPiece = capturedPiece;
        }

        /**
         * Returns the piece that was captured
         *
         * @return a Piece object of the captured piece
         */
        @Override
        public Piece getCapturedPiece() {
            return CapturedPiece;
        }

        /**
         * @return true for a capturing move
         */
        @Override
        public boolean wasCapture() {
            return true;
        }
    }

//...............................................................................................................

    /**
     * Class for a Regular move: A move in which no pieces are captured, the moving piece just changes position
     */
    public static class RegularMove extends Move {
        /**
         * Constructor for a Regular move. Calls super class constructor
         *
         * @param startPosition Origin Square of piece
         * @param endPosition   Destination Square of piece
         */
        public RegularMove(final Square startPosition, final Square endPosition) {
            super(startPosition, endPosition);
        }

        /**
         * Returns the piece that was captured (If a piece was captured)
         *
         * @return null
         */
        @Override
        public Piece getCapturedPiece() {
            return null;
        }

        /**
         * @return false for a non-capturing move
         */
        @Override
        public boolean wasCapture() {
            return false;
        }
    }


//...............................................................................................................

    /**
     * Class for a EnPassant move: A move in which an opponents piece pawn was captured when the pawn was an EnPassant pawn
     *
     * @TODO Check if necessary
     */
    public static class EnPassantMove extends Move {
        private final Piece CapturedPiece;
        private final Square CapturedPieceLocation;

        /**
         * Constructor for a En Passant move. Calls super class constructor
         *
         * @param startPosition         Origin Square of piece
         * @param endPosition           Destination Square of piece
         * @param capturedPiece         The piece object that was captured
         * @param capturedPieceLocation The location of the captured piece (since, unlike a capturing move, the captured piece and
         *                              destination of the moving piece are not the same square)
         */
        public EnPassantMove(final Square startPosition,
                             final Square endPosition,
                             final Piece capturedPiece,
                             final Square capturedPieceLocation) {
            super(startPosition, endPosition);
            this.CapturedPiece = capturedPiece;
            this.CapturedPieceLocation = capturedPieceLocation;
        }

        /**
         * Returns the piece that was captured
         *
         * @return the captured Pawn
         */
        @Override
        public Piece getCapturedPiece() {
            return CapturedPiece;
        }

        /**
         * @return true for all enPassant captures
         */
        @Override
        public boolean wasCapture() {
            return true;
        }

        /**
         * @return the square which the enPassant Pawn was on since during enPassant the captured piece is not on the destination square of the moving piece
         */
        public Square getCapturedPieceLocation() {
            return CapturedPieceLocation;
        }
    }

}