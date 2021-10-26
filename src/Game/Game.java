package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import User.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Player whitePlayer, blackPlayer;
    private final Board board;
    private Player PlayerToMove;

    /**
     * Game Constructor
     *
     * @param Gametype       GameType Object for the type of game, Eg - VersesComputer
     * @param SelectedColour The starting colour chosen by the player
     * @param user           The user playing the game
     */
    public Game(final GameType Gametype, final Colour SelectedColour, final User user) {
        this.board = new Board();

        if (Gametype == GameType.LOCAL_MULTIPLAYER) {
            if (SelectedColour == Colour.WHITE) {
                this.whitePlayer = new Player.Human(Colour.WHITE, user);
                this.blackPlayer = new Player.Human(Colour.BLACK, null);
            } else {
                this.whitePlayer = new Player.Human(Colour.WHITE, null);
                this.blackPlayer = new Player.Human(Colour.BLACK, user);
            }
        } else {
            if (SelectedColour == Colour.WHITE) {
                this.whitePlayer = new Player.Human(Colour.WHITE, user);
                this.blackPlayer = new Player.Computer(Colour.BLACK);
            } else {
                this.whitePlayer = new Player.Computer(Colour.WHITE);
                this.blackPlayer = new Player.Human(Colour.BLACK, user);
            }
        }

        PlayerToMove = whitePlayer;

    }

    //TODO remove. this is a test
    public Game() {
        this.board = new Board();
        whitePlayer = null;
        blackPlayer = null;
    }

    //TODO IMPORTANT.... and comment
    public void mainGame() {

    }

    /**
     * Changes the player to move
     */
    public void UpdatePlayerToMove() {
        if (PlayerToMove == whitePlayer) {
            PlayerToMove = blackPlayer;
        } else PlayerToMove = whitePlayer;
    }

    /**
     * Updates the board by making a move.
     *
     * @param move a move object identifying the move to be made
     * @// TODO: 08/09/2021 make move in game class
     */
    public void MakeMove(Move move) {
        //todo move comes from player class
        int OriginX = move.getStartPosition().ReturnCoordinate().getFile();
        int OriginY = move.getStartPosition().ReturnCoordinate().getRank();
        int DestinationX = move.getEndPosition().ReturnCoordinate().getFile();
        int DestinationY = move.getEndPosition().ReturnCoordinate().getRank();


        this.board.getBoardArray()[OriginY - 1][OriginX - 1] = new Square.EmptySquare(OriginX, OriginY);
        this.board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                new Square.OccupiedSquare(DestinationX, DestinationY, move.getMovedPiece());
        move.getMovedPiece().setPieceCoordinate(move.getEndPosition().ReturnCoordinate());

        //todo if move was a capture then remove captured piece from square

        //TODO remove test
        System.out.println("\n");
        board.PrintBoard();
    }

    /**
     * Finds all squares being checked by pieces of a given colour.
     * @param CheckingPieceColour The colour of the pieces that you want to find the checked square of. Meaning if you want to check whether the black king is in check, this value
     *                            would be Colour.WHITE because only white pieces can check a black king.
     * @return A list of squares in check by the given pieces
     * @todo finish method
     */
    public List<Square> LocateCheckedSquares (Colour CheckingPieceColour){
        List<Square> CheckedSquares = new ArrayList<>();
        return null;
    }


    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    //todo call UpdateuserStats method in repository class after each game and update a players ELO
}
