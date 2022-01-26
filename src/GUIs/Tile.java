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
    private JLabel icon;

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

    public Square getSquare() {
        return square;
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setIconText(String text) {
        icon = new JLabel(text);
        this.add(icon);
        this.repaint();
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Color getColour() {
        return colour;
    }
}
