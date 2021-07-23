package Game;

import Game.Board.Board;
import Game.Piece.Piece;
import Game.Piece.Pieces.Pawn;
import User.User;

public class Game
{
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    
    public Game(final GameType Gametype, final Colour SelectedColour, final User user)
    {
        this.board = new Board();
        
        if (Gametype == GameType.LOCAL_MULTIPLAYER)
        {
            if (SelectedColour == Colour.WHITE){
                this.whitePlayer = new Player.Human(Colour.WHITE, user);
                this.blackPlayer = new Player.Human(Colour.BLACK, null);
            }else {
                this.whitePlayer = new Player.Human(Colour.WHITE, null);
                this.blackPlayer = new Player.Human(Colour.BLACK, user);
            }
        }
        else
        {
            if (SelectedColour == Colour.WHITE){
                this.whitePlayer = new Player.Human(Colour.WHITE, user);
                this.blackPlayer = new Player.Computer(Colour.BLACK);
            }else {
                this.whitePlayer = new Player.Computer(Colour.WHITE);
                this.blackPlayer = new Player.Human(Colour.BLACK, user);
            }
        }
    }
}
