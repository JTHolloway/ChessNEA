package GUIs;

import Game.Board.Square;

import javax.swing.*;

/**
 * Similar to the square method however this is a JPanel and not used for any of the program functionality
 */
public final class Tile extends JPanel {
    private final Square square;

    /**
     * Constructor for a displayable board tile.
     *
     * @param square The square which the tile refers to
     */
    public Tile(final Square square) {
        this.square = square;


    }

    public Square getSquare() {
        return square;
    }
}
