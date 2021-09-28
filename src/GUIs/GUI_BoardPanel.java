package GUIs;

import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI_BoardPanel extends JPanel {

    private static List<Tile> Tiles;

    /**
     * Constructor for the board JPanel
     */
    public GUI_BoardPanel() {
        int Size = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));

        this.setSize(Size, Size);
        this.setLayout(null);

        InitialiseTiles();
    }

    /**
     * Initializes the board tiles to be displayed. Each tile is a JPanel which is displayed on the board JPanel
     */
    private void InitialiseTiles() {

        Colour colour = Colour.BLACK;
        int TileSize = (this.getSize().height) / 8;
        Tile tile;
        Tiles = new ArrayList<>();
        Game game = GUI_GamePanel.getGame();

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

                tile.setBounds((j - 1) * (TileSize), this.getSize().height - (i * TileSize), TileSize, TileSize);
                tile.setLayout(null);

                //TODO add pieces
//                JLabel PieceIcon = new JLabel();
//                PieceIcon.setSize(TileSize, TileSize);
//                PieceIcon.setIcon(tile.returnPiece().getPieceImage());

//                tile.add(PieceIcon);

                this.add(tile);
                Tiles.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }
    }

    //TODO init Pieces and comment
//   private void InitialisePieces() {
//
//    }
}
