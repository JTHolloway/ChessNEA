package GUIs;

import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI_BoardPanel {
    private static JPanel BoardDisplay;
    private static List<Tile> Tiles;

    public GUI_BoardPanel() {
        BoardDisplay = new JPanel();

        int Size = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));

        BoardDisplay.setSize(Size, Size);
        BoardDisplay.setLayout(null);

        InitialiseTiles();
    }

    private static void InitialiseTiles() {

        Colour colour = Colour.BLACK;
        int TileSize = (BoardDisplay.getSize().height) / 8;
        Tile tile;
        Tiles = new ArrayList<>();
        Game game = GamePanel.getGame();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (i == 1 || i == 2 || i == 7 || i == 8) {
                    Piece pieceAtLocation = game.getBoard().getBoardArray()[i - 1][j - 1].ReturnPiece();
                    tile = new Tile.OccupiedTile(new Coordinate(j, i), colour, pieceAtLocation);
                } else {
                    tile = new Tile.EmptyTile(new Coordinate(j, i), colour);
                }

                if (i % 2 != j % 2) {
                    tile.setBackground(new Color(234, 182, 118));
                } else {
                    tile.setBackground(new Color(148, 85, 9));
                }

                tile.setBounds((j - 1) * (TileSize), BoardDisplay.getSize().height - (i * TileSize), TileSize, TileSize);
                tile.setLayout(null);

                BoardDisplay.add(tile);
                Tiles.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }
    }

    public JPanel getBoardDisplay() {
        return BoardDisplay;
    }

    //TODO init Pieces
}
