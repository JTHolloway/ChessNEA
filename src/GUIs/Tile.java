package GUIs;

import Game.Board.Square;

import javax.swing.*;
import java.awt.*;

/**
 * Similar to the square method however this is a JPanel and not used for any of the program functionality
 */
public final class Tile extends JPanel {
    private Square square;
    private final Color colour;
    private final JLabel icon;

    /**
     * Constructor for a displayable board tile.
     *
     * @param square The square which the tile refers to
     */
    public Tile(final Square square, int tileSize, Color colour) {
        this.colour = colour;
        this.square = square;
        this.icon = new JLabel("");
        this.icon.setSize(tileSize, tileSize);
        this.add(icon);
    }

    /**
     * @return the square associated with this tile
     */
    public Square getSquare() {
        return square;
    }

    /**
     * @return the JLabel which contains the piece icon. Empty tiles have empty JLabels
     */
    public JLabel getIcon() {
        return icon;
    }

    /**
     * Updates the contents of the tile
     * @param text The text to be written to the tile. Either a unicode piece or an empty string.
     */
    public void setIconText(String text) {
        icon.setText(text);
        this.repaint();
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * @return the colour of this tile
     */
    public Color getColour() {
        return colour;
    }
}
