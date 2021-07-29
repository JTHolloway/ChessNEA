package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.Pieces.Pawn;
import User.User;

public class Game
{
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    private Player PlayerToMove;
    
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
        
        PlayerToMove = whitePlayer;
        
    }
    
    //TODO remove. this is a test
    public Game(){
        this.board = new Board();
        whitePlayer=null;
        blackPlayer=null;
    }
    
    //TODO MEGA
    public void mainGame(){
    
    }
    
    public void UpdatePlayerToMove(){
        if (PlayerToMove == whitePlayer){
            PlayerToMove = blackPlayer;
        } else PlayerToMove = whitePlayer;
    }
    
    //TODO
    public void MakeMove(Move move){
        //Get move from player class
        int OriginX = move.getStartPosition().ReturnCoordinate().getFile();
        int OriginY = move.getStartPosition().ReturnCoordinate().getRank();
        int DestinationX = move.getEndPosition().ReturnCoordinate().getFile();
        int DestinationY = move.getEndPosition().ReturnCoordinate().getRank();
    
    
        this.board.getBoardArray()[OriginY-1][OriginX-1] = new Square.EmptySquare(OriginX, OriginY);
        this.board.getBoardArray()[DestinationY-1][DestinationX-1] =
                new Square.OccupiedSquare(DestinationX, DestinationY, move.getMovedPiece());
        move.getMovedPiece().setPieceCoordinate(move.getEndPosition().ReturnCoordinate());
        
        //TODO remove test
        System.out.println("\n");
        board.PrintBoard();
    }
    
    public Board getBoard()
    {
        return board;
    }
}
