package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.Pieces.*;
import User.User;

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

        if (move.getMovedPiece() instanceof Pawn) {
            if (move.getMovedPiece().getColour() == Colour.WHITE && (OriginY - DestinationY == -2) && (OriginX - DestinationX == 0)) {
                board.setEnPassantPawn((Pawn) move.getMovedPiece());
            } else if (move.getMovedPiece().getColour() == Colour.BLACK && (OriginY - DestinationY == 2) && (OriginX - DestinationX == 0)) {
                board.setEnPassantPawn((Pawn) move.getMovedPiece());
            }
        } else if (move.getMovedPiece() instanceof Rook) {
            King king = (King) (move.getMovedPiece().getColour() == Colour.WHITE ? board.getKings()[0] : board.getKings()[1]);
            if (king.getCastlingAvailability() == CastlingAvailability.BOTH) {
                if (((Rook) move.getMovedPiece()).getCastlingAvailability() == CastlingAvailability.KING_SIDE) {
                    king.setCastlingAvailability(CastlingAvailability.QUEEN_SIDE);
                } else if (((Rook) move.getMovedPiece()).getCastlingAvailability() == CastlingAvailability.QUEEN_SIDE) {
                    king.setCastlingAvailability(CastlingAvailability.KING_SIDE);
                }
            } else if (king.getCastlingAvailability() == CastlingAvailability.QUEEN_SIDE) {
                if (((Rook) move.getMovedPiece()).getCastlingAvailability() == CastlingAvailability.QUEEN_SIDE) {
                    king.setCastlingAvailability(CastlingAvailability.NEITHER);
                }
            } else if (king.getCastlingAvailability() == CastlingAvailability.KING_SIDE) {
                if (((Rook) move.getMovedPiece()).getCastlingAvailability() == CastlingAvailability.KING_SIDE) {
                    king.setCastlingAvailability(CastlingAvailability.NEITHER);
                }
            }
        } else if (move.getMovedPiece() instanceof King) {
            if (((King) move.getMovedPiece()).getCastlingAvailability() != CastlingAvailability.NEITHER) {
                ((King) move.getMovedPiece()).setCastlingAvailability(CastlingAvailability.NEITHER);
            }
        }

        if (move.wasCapture()) {
            if (move.getCapturedPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().remove(move.getCapturedPiece());
            } else if (move.getCapturedPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().remove(move.getCapturedPiece());
            }
        }

        //TODO remove test
        System.out.println("\n");
        board.PrintBoard();
    }

    /**
     *
     * @param ThreatenedColour
     * @param ThreatenedSquare
     * @return
     */
    public static boolean isThreatenedSquare(Colour ThreatenedColour, Square ThreatenedSquare, Board board){

        final int file = ThreatenedSquare.ReturnCoordinate().getFile() - 1;
        final int rank = ThreatenedSquare.ReturnCoordinate().getRank() - 1;

        //Knight checks
        int[] knightHorizontalDirections = {-1, -2, -2, -1, 1, 2, 2, 1};
        int[] knightVerticalDirections = {-2, -1, 1, 2, -2, -1, 1, 2};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++){
            int currentFile = file + knightHorizontalDirections[DirectionIndex];
            int currentRank = rank + knightVerticalDirections[DirectionIndex];

            if ((currentFile <= 7 && currentFile >= 0) && (currentRank <= 7 && currentRank >= 0)){
                if (board.getBoardArray()[currentRank][currentFile].SquareOccupied())
                {
                    Piece threateningPiece = board.getBoardArray()[currentRank][currentFile].ReturnPiece();
                    if(threateningPiece instanceof Knight && threateningPiece.getColour() != ThreatenedColour){
                        return true;
                    }
                }
            }
        }


        //Other Piece Checks
        int[] regularHorizontalDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] regularVerticalDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++){
            int currentFile = file;
            int currentRank = rank;

            int loop = -1;
            while (loop < 8){
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

    //If the king is in check then no need to check for stalemate.
    // check checkmate -> check -> stalemate
    public boolean isKingChecked(Colour colour){
        Piece king = colour == Colour.WHITE ? board.getKings()[0] : board.getKings()[1];
        return isThreatenedSquare(king.getColour(), king.getPieceCoordinate().GetSquareAt(board.getBoardArray()), board);
    }

    public boolean isKingCheckmated(Colour colour){
        Piece king = colour == Colour.WHITE ? board.getKings()[0] : board.getKings()[1];
        if (isKingChecked(colour)){
            return king.CalculateValidMoves(board).isEmpty();
        }
        return false;
    }

    public boolean isStalemate(Colour colour){
        Piece king = colour == Colour.WHITE ? board.getKings()[0] : board.getKings()[1];
        if (king.CalculateValidMoves(board).isEmpty() && !isKingChecked(colour)) {
            List<Piece> pieces = colour == Colour.WHITE ? board.getWhitePieces() : board.getBlackPieces();
            for (Piece piece : pieces) {
                if (!piece.CalculateValidMoves(board).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return isKingCheckmated(Colour.WHITE) || isKingCheckmated(Colour.BLACK) || isStalemate(Colour.WHITE) || isStalemate(Colour.BLACK);
    }


    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    //todo call UpdateuserStats method in repository class after each game and update a players ELO
}
