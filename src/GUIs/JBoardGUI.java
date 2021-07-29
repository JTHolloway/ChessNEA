package GUIs;

import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.GameType;
import Game.Piece.Piece;
import User.User;

import javax.swing.*;
import java.awt.*;

public class JBoardGUI
{
    JWindow BoardDisplay;
    Game game;
    
    public JBoardGUI(GameType gameType, Colour PlayerColour, User user)
    {
        BoardDisplay = new JWindow();
        game = new Game(gameType, PlayerColour, user);
        
        int Size = (int)((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));
        
        BoardDisplay.setSize(Size, Size);
        BoardDisplay.setLayout(null);
        BoardDisplay.setLocationRelativeTo(null);
    
        InitialiseSquares();
        BoardDisplay.setVisible(true);
    }
    
    private void InitialiseSquares(){
    
        Colour colour = Colour.BLACK;
        int TileSize = (BoardDisplay.getSize().height) / 8;
        Tile tile;
        
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (i == 1 || i == 2 || i == 7 || i == 8){
                    Piece pieceAtLocation = game.getBoard().getBoardArray()[i-1][j-1].ReturnPiece();
                    tile = new Tile.OccupiedTile(new Coordinate(j, i), colour, pieceAtLocation);
                } else {
                    tile = new Tile.EmptyTile(new Coordinate(j, i), colour);
                }

                if (i % 2 != j % 2){
                    tile.setBackground(new Color(234, 182, 118));
                }else{
                    tile.setBackground(new Color(148, 85, 9));
                }

                tile.setBounds((j-1)*(TileSize), BoardDisplay.getSize().height - (i*TileSize), TileSize, TileSize);

                BoardDisplay.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }
    }
}
