package GUIs;

import Game.Board.Square;
import Game.Colour;
import Game.Game;
import Game.Move.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI_BoardPanel extends JPanel {

    private List<Tile> Tiles;
    private Square selectedSquare;

    /**
     * Constructor for the board JPanel
     */
    public GUI_BoardPanel() {
        int Size = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));

        this.setSize(Size, Size);
        this.setLayout(null);

        InitialiseTiles();
        InitialisePieces();

        Square[][] array = GUI_GamePanel.getGame().getBoard().getBoardArray();

        GUI_GamePanel.getGame().getBoard().PrintBoard();
        Game.MakeMove(new Move.RegularMove(array[1][1], array[2][1]), GUI_GamePanel.getGame().getBoard());
        System.out.println();
        GUI_GamePanel.getGame().getBoard().PrintBoard();

        InitialisePieces();
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
                    tile = new Tile(game.getBoard().getBoardArray()[(7- (i - 1))][(7- (j - 1))]);

                if (i % 2 != j % 2) {
                    tile.setBackground(new Color(234, 182, 118));
                } else {
                    tile.setBackground(new Color(148, 85, 9));
                }

                if (game.getSelectedColour() == Colour.WHITE){
                    tile.setBounds((7 - (j - 1)) * (TileSize), this.getSize().height - ((8 - (i - 1)) * TileSize), TileSize, TileSize);
                } else {
                    tile.setBounds((j - 1) * (TileSize), this.getSize().height - (i * TileSize), TileSize, TileSize);
                }
                tile.setLayout(null);

                Tile finalTile = tile;
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //todo if selected square is not already equal to null then count it as a destination, otherwise count as an origin
                        //todo can only move a piece if selected square is occupied, of the colour of the player and it is the PLAYERS TURN
                        selectedSquare = finalTile.getSquare();

                        if (selectedSquare.SquareOccupied()) {
                            if (selectedSquare.ReturnPiece().getColour() == game.getSelectedColour()) {
                                selectedSquare.ReturnPiece().CalculateValidMoves(game.getBoard());
                            }
                        }
                    }
                });

                Tiles.add(tile);
                this.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }
    }

   private void InitialisePieces() {
       int TileSize = (this.getSize().height) / 8;

       for (Tile tile : Tiles) {
           JLabel PieceIcon = new JLabel();
           PieceIcon.setSize(TileSize, TileSize);
           if (tile.getSquare().SquareOccupied()){
               if (tile.getSquare().ReturnPiece().getColour() == Colour.WHITE){
                   PieceIcon.setForeground(new Color(247, 229, 195));
               } else PieceIcon.setForeground(new Color(59, 40, 4));
               String Icon = tile.getSquare().ReturnPiece().ReturnPieceIcon();
               PieceIcon.setText(Icon);
           } else {
               String Icon = "";
               PieceIcon.setText(Icon);
           }
           PieceIcon.setFont(new Font("", Font.PLAIN, TileSize));
           tile.add(PieceIcon);
       }
    }
}
