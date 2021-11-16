package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.*;
import LibaryFunctions.Utility;
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
     * The King can be checked from any diagonal, horizontal and vertical as well as on each L-Shape originating from the king (For knight checks)
     * @param CheckingPieceColour The colour of the pieces that you want to find the checked square of. Meaning if you want to check whether the black king is in check, this value
     *                            would be Colour.WHITE because only white pieces can check a black king.
     * @return A list of squares in check by the given pieces
     * @todo finish method and redo comment
     */
    public List<Square> LocateCheckedSquares (Colour CheckingPieceColour){
        List<Square> CheckedSquares = new ArrayList<>();
        return null;
    }

    /**
     *
     * @param ThreatenedColour
     * @param ThreatenedSquare
     * @return
     */
    public boolean isThreatenedSquare(Colour ThreatenedColour, Square ThreatenedSquare){

        final int file = ThreatenedSquare.ReturnCoordinate().getFile() - 1;
        final int rank = ThreatenedSquare.ReturnCoordinate().getRank() - 1;

        //Knight checks
        int[] knightHorizontalDirections = {-1, -2, -2, -1, 1, 2, 2, 1};
        int[] knightVerticalDirections = {-2, -1, 1, 2, -2, -1, 1, 2};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++){
            //TODO Knight
        }


        //Other Piece Checks
        int[] regularHorizontalDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] regularVerticalDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++){
            int currentFile = file;
            int currentRank = rank;

            int loop = -1;
            while (currentFile >= 0 && currentFile <= 7 || currentRank >= 0 && currentRank <= 7){
                loop++;
                currentFile = currentFile + regularHorizontalDirections[DirectionIndex];
                currentRank = currentRank + regularVerticalDirections[DirectionIndex];

                if (currentFile > 7 || currentFile < 0 || currentRank > 7 || currentRank < 0){
                    break;
                }
                else if (board.getBoardArray()[currentRank][currentFile].SquareOccupied()){
                    Piece threateningPiece = board.getBoardArray()[currentRank][currentFile].ReturnPiece();

                    if (threateningPiece.getColour() != ThreatenedColour)
                    {
                        //Queen, Bishop and Rook checks
                        if (threateningPiece instanceof Queen){
                            return true;

                        } else if (threateningPiece instanceof Bishop){
                            boolean[] bishopAttackingCapabilities = {true, false, true, false, false, true, false, true};
                            if (bishopAttackingCapabilities[DirectionIndex]){
                                return true;
                            }

                        }else if(threateningPiece instanceof Rook){
                            boolean[] rookAttackingCapabilities = {false, true, false, true, true, false, true, false};
                            if (rookAttackingCapabilities[DirectionIndex]){
                                return true;
                            }
                        } else {

                            /*
                        King and Pawn checks:
                        If loop = 0 then the current square being checked is directly adjacent to the origin (threatened) square
                         */
                            if (loop == 0){
                                if (threateningPiece instanceof King){
                                    return true;
                                }
                                else if (threateningPiece instanceof Pawn) {
                                    boolean validDirection = threateningPiece.getColour() == Colour.BLACK;
                                    boolean[] pawnAttackingCapabilities = {validDirection, false, validDirection, false, false,
                                            !validDirection, false, !validDirection};
                                    if (pawnAttackingCapabilities[DirectionIndex]){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

    public boolean isKingChecked(){
        return false;
    }

    public boolean isKingCheckmated(){
        return false;
    }

    public boolean isStalemate(){
        return false;
    }


    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    //todo call UpdateuserStats method in repository class after each game and update a players ELO


//
//    Square kingLocation = null;
//
//    //Locate Checked King
//    A: for (Square[] row : board.getBoardArray()){
//        for (Square square : row){
//            if (square.SquareOccupied()){
//                if (square.ReturnPiece().getType() == PieceType.KING &&
//                        square.ReturnPiece().getColour() == Colour.GetOtherColour(CheckingPieceColour)){
//                    kingLocation = square;
//                    break A;
//                }
//            }
//        }
//    }
//
//    //Locate opposing pieces on the same row
//    List<Square> row = Utility.ArrayToRow(board.getBoardArray(), kingLocation);
//        for (Square square : row){
//        if (square.SquareOccupied()){
//            if (square.ReturnPiece().getColour() == CheckingPieceColour && square.ReturnPiece().getType() != PieceType.PAWN){
//                for (Move move : square.ReturnPiece().CalculateValidMoves(board)) {
//                    CheckedSquares.add(move.getEndPosition());
//                }
//            }
//        }
//    }
//
//    //Locate opposing pieces on the same Column
//    List<Square> column = Utility.ArrayToColumn(board.getBoardArray(), kingLocation);
//        for (Square square : column){
//        if (square.SquareOccupied()){
//            if (square.ReturnPiece().getColour() == CheckingPieceColour && square.ReturnPiece().getType() != PieceType.PAWN){
//                for (Move move : square.ReturnPiece().CalculateValidMoves(board)) {
//                    CheckedSquares.add(move.getEndPosition());
//                }
//            }
//        }
//    }
//
//    //Locate opposing pieces on L-Shape Knight Paths

}
