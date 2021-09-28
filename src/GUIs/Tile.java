package GUIs;

import Game.Colour;
import Game.Coordinate;
import Game.Piece.Piece;

import javax.swing.*;

/**
 * Similar to the square method however this is a JPanel and not used for any of the program functionality
 */
//TODO decide whether this class is relevant or if the square method could just be used instead
public abstract class Tile extends JPanel {
    protected final Coordinate coordinate;
    protected final Colour colour;

    /**
     * Constructor for a displayable board tile.
     *
     * @param coordinate the coordinate of the tile in the board. Variable is final because a tile cannot move, so coordinate is constant.
     * @param colour     the colour of the tile, either white or black. Tile Colour can not change so variable is final.
     */
    public Tile(final Coordinate coordinate, final Colour colour) {
        this.coordinate = coordinate;
        this.colour = colour;
    }

    /**
     * Identifies whether the square is occupied by a piece
     *
     * @return a boolean value indicating whether the square is occupied when the tile is clicked by a mouse
     */
    public abstract boolean isOccupied();

    /**
     * Returns the piece on the square
     *
     * @return a piece object for the piece displayed on the square
     */
    public abstract Piece returnPiece();

    /**
     * @return the coordinate of the tile
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public static class OccupiedTile extends Tile {

        private final Piece piece;

        public OccupiedTile(final Coordinate coordinate, final Colour colour, final Piece piece) {
            super(coordinate, colour);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece returnPiece() {
            return piece;
        }
    }


    public static class EmptyTile extends Tile {

        public EmptyTile(final Coordinate coordinate, final Colour colour) {
            super(coordinate, colour);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece returnPiece() {
            return null;
        }
    }
}
