package GUIs;

import Game.Board.Square;

import javax.swing.*;

/**
 * Similar to the square method however this is a JPanel and not used for any of the program functionality
 */
public final class Tile extends JPanel {
    private final Square square;
    private final JLabel icon;

    /**
     * Constructor for a displayable board tile.
     *
     * @param square The square which the tile refers to
     */
    public Tile(final Square square, int tileSize) {
        this.square = square;
        this.icon = new JLabel("");
        this.icon.setSize(tileSize, tileSize);
        this.add(icon);
    }

    public Square getSquare() {
        return square;
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setIcon(JLabel icon) {
        this.add(icon);
    }
}
