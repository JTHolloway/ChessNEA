package Game.Board;

import Game.Coordinate;
import Game.Piece.Piece;

public abstract class Square {

    protected final Coordinate coordinate;

    /**
     * Constructor for a Square object
     * Takes in the x and y coordinates of the square and creates a new coordinate object
     *
     * @param XCoordinate The X-Coordinate of the piece (the file)
     * @param YCoordinate The Y-Coordinate of the piece (the rank)
     */
    public Square(final int XCoordinate, final int YCoordinate) {
        coordinate = new Coordinate(XCoordinate, YCoordinate);
    }


    /**
     * Returns the squares coordinates
     *
     * @return a Coordinate object indicating the location of the square on the board
     */
    public Coordinate ReturnCoordinate() {
        return coordinate;
    }


    /**
     * Identifies whether the square is occupied by a piece
     *
     * @return a boolean value indicating whether the square is occupied
     */
    public abstract boolean SquareOccupied();


    /**
     * Returns the piece on the square
     *
     * @return a piece object for the piece occupying the square
     */
    public abstract Piece ReturnPiece();

//Nested Class...............................................................................................................

    /**
     * Nested Class of Square, uniquely identifies an empty square which is
     * not occupied by a piece.
     */
    public static class EmptySquare extends Square {

        /**
         * Constructor for an empty Square object
         * An empty square does not contain piece object
         *
         * @param XCoordinate The X-Coordinate of the piece (the file)
         * @param YCoordinate The Y-Coordinate of the piece (the rank)
         */
        public EmptySquare(int XCoordinate, int YCoordinate) {
            super(XCoordinate, YCoordinate);
        }

        /**
         * Identifies whether the square is occupied by a piece for an empty square
         *
         * @return a boolean false value because square is empty
         */
        @Override
        public boolean SquareOccupied() {
            return false;
        }

        /**
         * Returns the piece on the square for an empty square
         *
         * @return a null piece object, since the square is empty
         */
        @Override
        public Piece ReturnPiece() {
            return null;
        }
    }

//Nested Class...............................................................................................................

    /**
     * Nested Class of Square, uniquely identifies a square which is
     * Occupied by a piece.
     */
    public static class OccupiedSquare extends Square {

        private final Piece OccupyingPiece;

        /**
         * Constructor for a Square Occupied by a piece
         * This type of square contains a piece of any colour
         *
         * @param XCoordinate    The X-Coordinate of the piece (the file)
         * @param YCoordinate    The Y-Coordinate of the piece (the rank)
         * @param occupyingPiece The piece occupying the square with given coordinates
         */
        public OccupiedSquare(int XCoordinate, int YCoordinate, Piece occupyingPiece) {
            super(XCoordinate, YCoordinate);
            OccupyingPiece = occupyingPiece;
        }

        /**
         * Identifies whether the square is occupied by a piece for an occupied square
         *
         * @return a boolean true value because square is occupied
         */
        @Override
        public boolean SquareOccupied() {
            return true;
        }

        /**
         * Returns the piece on the square
         *
         * @return the Piece object occupying the square with given coordinates
         */
        @Override
        public Piece ReturnPiece() {
            return OccupyingPiece;
        }
    }
}
