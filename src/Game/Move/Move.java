package Game.Move;

import Game.Board.Square;
import Game.Piece.Piece;

public abstract class Move
{

    protected final Square StartPosition;
    protected final Square EndPosition;
    protected final Piece MovedPiece;

    /**
     * Constructor for a move
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
     * @// TODO: 13/07/2021 ToMoveNotation() method
     */
    public String ToMoveNotation()
    {
        boolean Unambiguous;
        boolean Capture;
        return null;
    }
    
    /**
     * Returns the piece that was captured
     * @return a Piece object of the captured piece
     * @return null if no piece was captured
     */
    public abstract Piece getCapturedPiece();
    
    public abstract boolean wasCapture();

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
    public static class CapturingMove extends Move
    {

        private final Piece CapturedPiece;

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
        @Override
        public Piece getCapturedPiece()
        {
            return CapturedPiece;
        }
    
        @Override
        public boolean wasCapture()
        {
            return true;
        }
    }
    
//...............................................................................................................
    
    /**
     * Regular move Inner class used to uniquely identify
     * properties of a move
     */
    public static class RegularMove extends Move
    {
        /**
         * Constructor for a Regular move. Calls super class constructor
         * @param startPosition Origin Square of piece
         * @param endPosition   Destination Square of piece
         */
        public RegularMove(final Square startPosition, final Square endPosition)
        {
            super(startPosition, endPosition);
        }
    
        /**
         * Returns the piece that was captured (If a piece was captured)
         * @return null
         */
        @Override
        public Piece getCapturedPiece()
        {
            return null;
        }
    
        @Override
        public boolean wasCapture()
        {
            return false;
        }
    }
    
    
//...............................................................................................................
    
    /**
     * En Passant move Inner class used to uniquely identify
     * properties of an En Passant move
     */
    //TODO Check if necessary
    public static class EnPassantMove extends Move
    {
        private final Piece CapturedPiece;
        private final Square CapturedPieceLocation;
        
        /**
         * Constructor for a En Passant move. Calls super class constructor
         * @param startPosition Origin Square of piece
         * @param endPosition   Destination Square of piece
         * @param capturedPiece The piece object that was captured
         * @param capturedPieceLocation The location of the captured piece (since, unlike a capturing move, the captured piece and
         *                              destination of the moving piece are not the same square)
         */
        public EnPassantMove(final Square startPosition,
                             final Square endPosition,
                             final Piece capturedPiece,
                             final Square capturedPieceLocation)
        {
            super(startPosition, endPosition);
            this.CapturedPiece = capturedPiece;
            this.CapturedPieceLocation = capturedPieceLocation;
        }
        
        /**
         * Returns the piece that was captured
         * @return null
         */
        @Override
        public Piece getCapturedPiece()
        {
            return CapturedPiece;
        }
        
        @Override
        public boolean wasCapture()
        {
            return true;
        }
    
        public Square getCapturedPieceLocation()
        {
            return CapturedPieceLocation;
        }
    }
    
}