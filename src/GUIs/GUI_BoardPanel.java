package GUIs;

import Game.Board.Square;
import Game.Colour;
import Game.Game;
import Game.Move.Move;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI_BoardPanel extends JPanel {

    private List<Tile> Tiles;
    private Square selectedSquare = null;
    private Square destinationSquare;

    /**
     * Constructor for the board JPanel
     */
    public GUI_BoardPanel() {
        int Size = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));

        this.setSize(Size, Size);
        this.setLayout(null);

        InitialiseTiles();

//
//        Square[][] array = GUI_GamePanel.getGame().getBoard().getBoardArray();
//
//        GUI_GamePanel.getGame().getBoard().PrintBoard();
//        Game.MakeMove(new Move.RegularMove(array[1][1], array[2][1]), GUI_GamePanel.getGame().getBoard());
//        Game.MakeMove(new Move.RegularMove(array[0][2], array[2][0]), GUI_GamePanel.getGame().getBoard());
//        Game.MakeMove(new Move.RegularMove(array[0][1], array[2][2]), GUI_GamePanel.getGame().getBoard());
//        Game.MakeMove(new Move.RegularMove(array[1][4], array[3][4]), GUI_GamePanel.getGame().getBoard());
//        Game.MakeMove(new Move.RegularMove(array[0][3], array[2][5]), GUI_GamePanel.getGame().getBoard());
//        System.out.println();
//        GUI_GamePanel.getGame().getBoard().PrintBoard();
//
//        InitialisePieces();
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
                tile = new Tile(game.getBoard().getBoardArray()[(7 - (i - 1))][(7 - (j - 1))], TileSize);

                if (i % 2 != j % 2) {
                    tile.setBackground(new Color(234, 182, 118));
                } else {
                    tile.setBackground(new Color(148, 85, 9));
                }

                if (game.getSelectedColour() == Colour.WHITE) {
                    tile.setBounds((7 - (j - 1)) * (TileSize), this.getSize().height - ((8 - (i - 1)) * TileSize), TileSize, TileSize);
                } else {
                    tile.setBounds((j - 1) * (TileSize), this.getSize().height - (i * TileSize), TileSize, TileSize);
                }
                tile.setLayout(null);

                if (tile.getSquare().SquareOccupied()) {
                    if (tile.getSquare().ReturnPiece().getColour() == Colour.WHITE) {
                        tile.getIcon().setForeground(new Color(247, 229, 195));
                    } else tile.getIcon().setForeground(new Color(59, 40, 4));
                    String Icon = tile.getSquare().ReturnPiece().ReturnPieceIcon();
                    tile.getIcon().setText(Icon);
                }
                tile.getIcon().setFont(new Font("", Font.PLAIN, TileSize));

                Tile finalTile = tile;
                final Border[] previousBorder = new Border[1];
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //todo can only move a piece if it is the PLAYERS TURN
                        //todo check if a pawn promotion desination occurs

                        if (selectedSquare == null) { //Selecting a square
                            SelectOriginSquare(finalTile, game);

                        } else { //Selecting a destination
                            List<Move> moves = selectedSquare.ReturnPiece().CalculateValidMoves(game.getBoard());
                            boolean valid = SelectDestinationSquare(finalTile, game, moves);

                            if (valid) {
                                //todo Update Board
                                UpdateBoard();

                                previousBorder[0] = null;
                                for (Move move : moves) {
                                    for (Tile tile1 : Tiles) {
                                        if (tile1.getSquare().ReturnCoordinate().CompareCoordinates(move.getEndPosition())) {
                                            tile1.setBorder(null);
                                        }
                                    }
                                }
                            } else {
                                SelectOriginSquare(finalTile, game);
                            }
                            destinationSquare = null;
                            selectedSquare = null;
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        previousBorder[0] = finalTile.getBorder();
                        finalTile.setBorder(new LineBorder(Color.RED, 3));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        finalTile.setBorder(previousBorder[0]);
                    }
                });

                Tiles.add(tile);
                this.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }
    }

    private void SelectOriginSquare(Tile finalTile, Game game) {
        selectedSquare = finalTile.getSquare();
        if (selectedSquare.SquareOccupied()) { //If the square is occupied
            if (selectedSquare.ReturnPiece().getColour() == game.getSelectedColour()) { //then if the piece is the players piece DO..
                List<Move> moves = selectedSquare.ReturnPiece().CalculateValidMoves(game.getBoard());
                for (Tile tile1 : Tiles) {
                    tile1.setBorder(null);
                }
                for (Move move : moves) {
                    for (Tile tile1 : Tiles) {
                        if (tile1.getSquare().ReturnCoordinate().CompareCoordinates(move.getEndPosition())) {
                            tile1.setBorder(new LineBorder(Color.GREEN, 5));
                        }
                    }
                }
            } else selectedSquare = null;
        } else selectedSquare = null;
    }

    private boolean SelectDestinationSquare(Tile finalTile, Game game, List<Move> moveList) {
        destinationSquare = finalTile.getSquare();
        boolean valid = false;

        for (Move move : moveList) {
                if (move.getEndPosition().ReturnCoordinate().CompareCoordinates(destinationSquare)) {
                    valid = true;
                    Game.MakeMove(move, game.getBoard());

                    System.out.println();
                    game.getBoard().PrintBoard();

                    break;
                }
        }
        return valid;
    }

    private void UpdateBoard() {
        int TileSize = (this.getSize().height) / 8;
        for (Tile tile : Tiles) {

            if (tile.getSquare().SquareOccupied()) {
                if (tile.getSquare().ReturnPiece().getColour() == Colour.WHITE) {
                    tile.getIcon().setForeground(new Color(247, 229, 195));
                } else tile.getIcon().setForeground(new Color(59, 40, 4));

                String Icon = tile.getSquare().ReturnPiece().ReturnPieceIcon();
                tile.getIcon().setText(Icon);
            } else {
                tile.getIcon().setText("");
            }
            tile.getIcon().setFont(new Font("", Font.PLAIN, TileSize));
        }
    }
}
