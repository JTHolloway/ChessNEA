package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.*;
import User.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Player whitePlayer, blackPlayer;
    private final Board board;
    private final Colour selectedColour;
    private final GameType gameType;
    private Player PlayerToMove;
    private int movesMade;

    /**
     * Game Constructor
     *
     * @param Gametype       GameType Object for the type of game, Eg - VersesComputer
     * @param SelectedColour The starting colour chosen by the player
     * @param user           The user playing the game
     */
    public Game(final GameType Gametype, final Colour SelectedColour, final User user) {
        this.board = new Board();
        selectedColour = SelectedColour;
        gameType = Gametype;
        movesMade = 0;

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

    /**
     * Updates the board by making a move.
     *
     * @param move a move object identifying the move to be made
     */
    public static void MakeMove(Move move, Board board) {
        int OriginX = move.getStartPosition().ReturnCoordinate().getFile();
        int OriginY = move.getStartPosition().ReturnCoordinate().getRank();
        int DestinationX = move.getEndPosition().ReturnCoordinate().getFile();
        int DestinationY = move.getEndPosition().ReturnCoordinate().getRank();

        board.getBoardArray()[OriginY - 1][OriginX - 1] = new Square.EmptySquare(OriginX, OriginY);

        if (move instanceof Move.PawnPromotion) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                    new Square.OccupiedSquare(DestinationX, DestinationY, ((Move.PawnPromotion) move).getPromotionPiece());
            if (((Move.PawnPromotion) move).getPromotionPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().add(((Move.PawnPromotion) move).getPromotionPiece());
                board.getWhitePieces().remove(move.getMovedPiece());

            } else if (((Move.PawnPromotion) move).getPromotionPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().add(((Move.PawnPromotion) move).getPromotionPiece());
                board.getBlackPieces().remove(move.getMovedPiece());
            }

        } else if (move instanceof Move.PawnPromotionCapture) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                    new Square.OccupiedSquare(DestinationX, DestinationY, ((Move.PawnPromotionCapture) move).getPromotionPiece());
            if (((Move.PawnPromotionCapture) move).getPromotionPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().add(((Move.PawnPromotionCapture) move).getPromotionPiece());
                board.getWhitePieces().remove(move.getMovedPiece());

            } else if (((Move.PawnPromotionCapture) move).getPromotionPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().add(((Move.PawnPromotionCapture) move).getPromotionPiece());
                board.getBlackPieces().remove(move.getMovedPiece());
            }

        } else if (move instanceof Move.EnPassantMove) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                    new Square.OccupiedSquare(DestinationX, DestinationY, move.getMovedPiece());
            move.getMovedPiece().setPieceCoordinate(move.getEndPosition().ReturnCoordinate());

            //Return EnPassant pawn to square
            board.getBoardArray()[((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getRank() - 1]
                    [((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getFile() - 1] =
                    new Square.EmptySquare(((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getRank(),
                            ((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getFile());

        } else if (move instanceof Move.CastlingMove) {
            //Move King
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                    new Square.OccupiedSquare(DestinationX, DestinationY, move.getMovedPiece());
            move.getMovedPiece().setPieceCoordinate(move.getEndPosition().ReturnCoordinate());

            //Remove Rook from its origin
            int rank = move.getMovedPiece().getColour() == Colour.WHITE ? 0 : 7;
            if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.KING_SIDE) {
                board.getBoardArray()[rank][7] = new Square.EmptySquare(8, rank + 1);
            } else if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.QUEEN_SIDE) {
                board.getBoardArray()[rank][0] = new Square.EmptySquare(1, rank + 1);
            }

            //Move Rook to its destination
            board.getBoardArray()[((Move.CastlingMove) move).getRookDestination().getRank() - 1][((Move.CastlingMove) move).getRookDestination().getFile() - 1] =
                    new Square.OccupiedSquare(((Move.CastlingMove) move).getRookDestination().getFile(), ((Move.CastlingMove) move).getRookDestination().getRank(), ((Move.CastlingMove) move).getCastledRook());
            ((Move.CastlingMove) move).getCastledRook().setPieceCoordinate(((Move.CastlingMove) move).getRookDestination());

        } else {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] =
                    new Square.OccupiedSquare(DestinationX, DestinationY, move.getMovedPiece());
            move.getMovedPiece().setPieceCoordinate(move.getEndPosition().ReturnCoordinate());
        }


        if (move.getMovedPiece() instanceof Pawn) {
            //Double Pawn moves
            if (move.getMovedPiece().getColour() == Colour.WHITE && (OriginY - DestinationY == -2) && (OriginX - DestinationX == 0)) {
                board.setEnPassantPawn((Pawn) move.getMovedPiece());
            } else if (move.getMovedPiece().getColour() == Colour.BLACK && (OriginY - DestinationY == 2) && (OriginX - DestinationX == 0)) {
                board.setEnPassantPawn((Pawn) move.getMovedPiece());
            }
            //Regular Pawn move
            else {
                board.setEnPassantPawn(null);
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
        //Unset en-passant pawn if a double pawn move didnt happen
        if (move.getMovedPiece().getType() != PieceType.PAWN) {
            board.setEnPassantPawn(null);
        }

        if (move.wasCapture()) {
            if (move.getCapturedPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().remove(move.getCapturedPiece());
            } else if (move.getCapturedPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().remove(move.getCapturedPiece());
            }
        }
    }

    /**
     * Reverses a move by setting each piece back to where it was originally.
     *
     * @param move                 The move to reverse
     * @param board                The board which the move was played on
     * @param castlingAvailability The previous castling availability before the move was made
     * @param enPassantPawn        The previous EnPassant pawn before the move was made
     */
    public static void reverseMove(Move move, Board board, CastlingAvailability castlingAvailability, Pawn enPassantPawn) {
        int OriginX = move.getStartPosition().ReturnCoordinate().getFile();
        int OriginY = move.getStartPosition().ReturnCoordinate().getRank();
        int DestinationX = move.getEndPosition().ReturnCoordinate().getFile();
        int DestinationY = move.getEndPosition().ReturnCoordinate().getRank();

        //Moves the piece back to where it started
        board.getBoardArray()[OriginY - 1][OriginX - 1] =
                new Square.OccupiedSquare(OriginX, OriginY, move.getMovedPiece());
        move.getMovedPiece().setPieceCoordinate(move.getStartPosition().ReturnCoordinate());

        //Puts any captured piece back to where it started and removes the other piece from that square
        if (move instanceof Move.EnPassantMove) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.EmptySquare(DestinationX, DestinationY);
            board.getBoardArray()[((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getRank() - 1]
                    [((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getFile() - 1] =
                    new Square.OccupiedSquare(((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getRank(),
                            ((Move.EnPassantMove) move).getCapturedPieceLocation().ReturnCoordinate().getFile(), move.getCapturedPiece());

            if (move.getCapturedPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().add(move.getCapturedPiece());
            } else if (move.getCapturedPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().add(move.getCapturedPiece());
            }
            board.setEnPassantPawn((Pawn) move.getCapturedPiece());
        } else if (move instanceof Move.CapturingMove) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.OccupiedSquare(DestinationX, DestinationY, move.getCapturedPiece());
            if (move.getCapturedPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().add(move.getCapturedPiece());
            } else if (move.getCapturedPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().add(move.getCapturedPiece());
            }
        } else if (move instanceof Move.PawnPromotion) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.EmptySquare(DestinationX, DestinationY);
            if (((Move.PawnPromotion) move).getPromotionPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().remove(((Move.PawnPromotion) move).getPromotionPiece());
                board.getWhitePieces().add(move.getMovedPiece());

            } else if (((Move.PawnPromotion) move).getPromotionPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().remove(((Move.PawnPromotion) move).getPromotionPiece());
                board.getBlackPieces().add(move.getMovedPiece());
            }

        } else if (move instanceof Move.PawnPromotionCapture) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.OccupiedSquare(DestinationX, DestinationY, move.getCapturedPiece());
            if (((Move.PawnPromotionCapture) move).getPromotionPiece().getColour() == Colour.WHITE) {
                board.getWhitePieces().remove(((Move.PawnPromotionCapture) move).getPromotionPiece());
                board.getWhitePieces().add(move.getMovedPiece());
                board.getWhitePieces().add(move.getCapturedPiece());

            } else if (((Move.PawnPromotionCapture) move).getPromotionPiece().getColour() == Colour.BLACK) {
                board.getBlackPieces().remove(((Move.PawnPromotionCapture) move).getPromotionPiece());
                board.getBlackPieces().add(move.getMovedPiece());
                board.getBlackPieces().add(move.getCapturedPiece());
            }

        } else if (move instanceof Move.CastlingMove) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.EmptySquare(DestinationX, DestinationY);
            board.getBoardArray()[((Move.CastlingMove) move).getRookDestination().getRank() - 1][((Move.CastlingMove) move).getRookDestination().getFile() - 1]
                    = new Square.EmptySquare(((Move.CastlingMove) move).getRookDestination().getFile(), ((Move.CastlingMove) move).getRookDestination().getRank());

            int rank = move.getMovedPiece().getColour() == Colour.WHITE ? 0 : 7;
            if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.KING_SIDE) {
                board.getBoardArray()[rank][7] = new Square.OccupiedSquare(8, rank + 1, ((Move.CastlingMove) move).getCastledRook());
                ((Move.CastlingMove) move).getCastledRook().setPieceCoordinate(new Coordinate(8, rank + 1));
            } else if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.QUEEN_SIDE) {
                board.getBoardArray()[rank][0] = new Square.OccupiedSquare(1, rank + 1, ((Move.CastlingMove) move).getCastledRook());
                ((Move.CastlingMove) move).getCastledRook().setPieceCoordinate(new Coordinate(1, rank + 1));
            }

        } else {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.EmptySquare(DestinationX, DestinationY);
        }

        //Reset Castling Status
        King king = (King) (move.getMovedPiece().getColour() == Colour.WHITE ? board.getKings()[0] : board.getKings()[1]);
        king.setCastlingAvailability(castlingAvailability);

        //Reset EnPassant Pawn
        board.setEnPassantPawn(enPassantPawn);
    }

    /**
     * Checks whether a square is threatening to a certain colour. A square is threatened if a piece of a given colour
     * can be captured in that square because it is guarded by the opposing colour .
     *
     * @param ThreatenedColour The colour for which the destination square is a threat.
     * @param ThreatenedSquare The square which is being checked
     * @return true if the colour is threatened in that square.
     */
    public static boolean isThreatenedSquare(Colour ThreatenedColour, Square ThreatenedSquare, Board board) {

        final int file = ThreatenedSquare.ReturnCoordinate().getFile() - 1;
        final int rank = ThreatenedSquare.ReturnCoordinate().getRank() - 1;

        //Knight checks
        int[] knightHorizontalDirections = {-1, -2, -2, -1, 1, 2, 2, 1};
        int[] knightVerticalDirections = {-2, -1, 1, 2, -2, -1, 1, 2};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++) {
            int currentFile = file + knightHorizontalDirections[DirectionIndex];
            int currentRank = rank + knightVerticalDirections[DirectionIndex];

            if ((currentFile <= 7 && currentFile >= 0) && (currentRank <= 7 && currentRank >= 0)) {
                if (board.getBoardArray()[currentRank][currentFile].SquareOccupied()) {
                    Piece threateningPiece = board.getBoardArray()[currentRank][currentFile].ReturnPiece();
                    if (threateningPiece instanceof Knight && threateningPiece.getColour() != ThreatenedColour) {
                        return true;
                    }
                }
            }
        }


        //Other Piece Checks
        int[] regularHorizontalDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] regularVerticalDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int DirectionIndex = 0; DirectionIndex < 8; DirectionIndex++) {
            int currentFile = file;
            int currentRank = rank;

            int loop = -1;
            while (loop < 8) {
                loop++;
                currentFile = currentFile + regularHorizontalDirections[DirectionIndex];
                currentRank = currentRank + regularVerticalDirections[DirectionIndex];

                if (currentFile > 7 || currentFile < 0 || currentRank > 7 || currentRank < 0) {
                    break;
                } else if (board.getBoardArray()[currentRank][currentFile].SquareOccupied()) {
                    Piece threateningPiece = board.getBoardArray()[currentRank][currentFile].ReturnPiece();

                    if (threateningPiece.getColour() != ThreatenedColour) {
                        //Queen, Bishop and Rook checks
                        if (threateningPiece instanceof Queen) {
                            return true;

                        } else if (threateningPiece instanceof Bishop) {
                            boolean[] bishopAttackingCapabilities = {true, false, true, false, false, true, false, true};
                            if (bishopAttackingCapabilities[DirectionIndex]) {
                                return true;
                            }

                        } else if (threateningPiece instanceof Rook) {
                            boolean[] rookAttackingCapabilities = {false, true, false, true, true, false, true, false};
                            if (rookAttackingCapabilities[DirectionIndex]) {
                                return true;
                            }
                        } else {

                            /*
                        King and Pawn checks:
                        If loop = 0 then the current square being checked is directly adjacent to the origin (threatened) square
                         */
                            if (loop == 0) {
                                if (threateningPiece instanceof King) {
                                    return true;
                                } else if (threateningPiece instanceof Pawn) {
                                    boolean validDirection = threateningPiece.getColour() == Colour.WHITE;
                                    boolean[] pawnAttackingCapabilities = {validDirection, false, !validDirection, false, false,
                                            validDirection, false, !validDirection};
                                    if (pawnAttackingCapabilities[DirectionIndex]) {
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

    /**
     * If the square which the king is on is a threatened square, then the king must be in check
     *
     * @param colour The colour of the king you want to determine is checked or not.
     * @param board  The board object associated with the current game
     * @return true if the king is checked.
     */
    public static boolean isKingChecked(Colour colour, Board board) {
        Piece king = colour == Colour.WHITE ? board.getKings()[0] : board.getKings()[1];
        return isThreatenedSquare(king.getColour(), king.getPieceCoordinate().GetSquareAt(board.getBoardArray()),
                board);
    }

    /**
     * Converts a move to algebraic chess Notation according to the same order and method as my flowchart
     * This method is called before the move is made
     *
     * @return a String in chess notation
     */
    public String MoveToNotation(Move move) {
        String moveNotation = "";
        Piece movingPiece = move.getMovedPiece();
        boolean isCapture = move.wasCapture();

        //Piece Type and Origin
        if (movingPiece instanceof Pawn) {
            if (isCapture) {
                moveNotation = moveNotation + move.getStartPosition().ReturnCoordinate().FileToNotation();
            }
        } else if (movingPiece instanceof King) {
            //Castling
            if (move instanceof Move.CastlingMove) {
                if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.KING_SIDE) {
                    moveNotation = moveNotation + "O-O";
                } else if (((Move.CastlingMove) move).getCastleType() == CastlingAvailability.QUEEN_SIDE) {
                    moveNotation = moveNotation + "O-O-O";
                }
            } else {
                moveNotation = moveNotation + movingPiece.PieceTypeToNotation();
            }
        } else {
            moveNotation = moveNotation + movingPiece.PieceTypeToNotation();
        }

        //Ambiguous Moves
        if (!(movingPiece instanceof King)) {
            List<Piece> ambiguousPieces = isMoveAmbiguous(move);
            if (!ambiguousPieces.isEmpty()) {
                boolean uniqueFile = true;
                boolean uniqueRank = true;
                for (Piece piece : ambiguousPieces) {
                    if (piece.getPieceCoordinate().getFile() == move.getMovedPiece().getPieceCoordinate().getFile()) {
                        uniqueFile = false;
                    }
                    if (piece.getPieceCoordinate().getRank() == move.getMovedPiece().getPieceCoordinate().getRank()) {
                        uniqueRank = false;
                    }
                }
                if (uniqueFile) {
                    moveNotation = moveNotation + move.getStartPosition().ReturnCoordinate().FileToNotation();
                } else if (uniqueRank) {
                    moveNotation = moveNotation + move.getStartPosition().ReturnCoordinate().getRank();
                } else {
                    moveNotation = moveNotation + move.getStartPosition().ReturnCoordinate().CoordinateToNotation();
                }
            }
        }

        //Capturing Moves
        if (isCapture) {
            moveNotation = moveNotation + "x";
        }
        if (!(move instanceof Move.CastlingMove)) {
            moveNotation = moveNotation + move.getEndPosition().ReturnCoordinate().CoordinateToNotation();
        }

        //Pawn Promotion
        if (move instanceof Move.PawnPromotion) {
            Piece pawnPromotion = ((Move.PawnPromotion) move).getPromotionPiece();
            moveNotation = moveNotation + ("=" + pawnPromotion.PieceTypeToNotation());
        } else if (move instanceof Move.PawnPromotionCapture) {
            Piece pawnPromotion = ((Move.PawnPromotionCapture) move).getPromotionPiece();
            moveNotation = moveNotation + ("=" + pawnPromotion.PieceTypeToNotation());
        }

        //Check or Checkmate
        King[] kings = {(King) board.getKings()[0], (King) board.getKings()[1]};
        final CastlingAvailability current_CastlingAbility =
                move.getMovedPiece().getColour() == Colour.WHITE ? kings[0].getCastlingAvailability() :
                        kings[1].getCastlingAvailability();
        final Pawn current_EnPassantPawn = board.getEnPassantPawn();
        MakeMove(move, board);
        if (isKingCheckmated(Colour.GetOtherColour(move.getMovedPiece().getColour()))) {
            moveNotation = moveNotation + "#";
        } else if (isKingChecked(Colour.GetOtherColour(move.getMovedPiece().getColour()), board)) {
            moveNotation = moveNotation + "+";
        }
        reverseMove(move, board, current_CastlingAbility, current_EnPassantPawn);

        return moveNotation;
    }

    /**
     * An ambiguous move is where the two pieces of the same colour and type
     * can move to the same square in the same move. So different notation needs to be used to
     * distinguish between moves
     *
     * @param move The move to check
     * @return a list of pieces which are of the same colour and type which can all move to the same destination.
     * return an empty list if move is not ambiguous
     */
    public List<Piece> isMoveAmbiguous(Move move) {
        List<Piece> pieces = move.getMovedPiece().getColour() == Colour.WHITE ? board.getWhitePieces() :
                board.getBlackPieces();
        List<Piece> ambiguousPieces = new ArrayList<>();

        for (Piece piece : pieces) {
            if (piece.getType() == move.getMovedPiece().getType() && piece != move.getMovedPiece()) {
                for (Move otherMove : piece.CalculateValidMoves(board)) {
                    if (otherMove.getEndPosition().ReturnCoordinate().CompareCoordinates(move.getEndPosition())) {
                        ambiguousPieces.add(otherMove.getMovedPiece());
                    }
                }
            }
        }
        return ambiguousPieces;
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
     * If the player is currently in check and cannot move out of check then the player is in checkmate.
     *
     * @param colour The colour of the king you want to determine is in checkmate
     * @return true if king is checkmated
     */
    public boolean isKingCheckmated(Colour colour) {
        King king = colour == Colour.WHITE ? (King) board.getKings()[0] : (King) board.getKings()[1];
        List<Piece> pieces = colour == Colour.WHITE ? board.getWhitePieces() : board.getBlackPieces();

        final CastlingAvailability castlingAvailability = king.getCastlingAvailability();
        final Pawn enPassantPawn = board.getEnPassantPawn();

        if (isKingChecked(colour, board) && king.CalculateValidMoves(board).isEmpty()) {
            for (Piece piece : pieces) {
                List<Move> moves = piece.CalculateValidMoves(board);
                for (Move move : moves) {
                    MakeMove(move, board);

                    //If you can make a move which removes the king from check then its not checkmate
                    if (!isKingChecked(colour, board)) {
                        reverseMove(move, board, castlingAvailability, enPassantPawn);
                        return false;
                    } else {
                        reverseMove(move, board, castlingAvailability, enPassantPawn);
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * If the king is not checkmated and the player has no available moves then the player is in stalemate
     *
     * @param colour The colour you want to determine is in Stalemate
     * @return true if the player of that colour is stalemated
     */
    public boolean isStalemate(Colour colour) {
        Piece king = colour == Colour.WHITE ? board.getKings()[0] : board.getKings()[1];
        if (king.CalculateValidMoves(board).isEmpty() && !isKingChecked(colour, board)) {
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

    /**
     * @return true if checkmate or stalemate has occurred, the game is over.
     */
    public boolean isGameOver() {
        return isKingCheckmated(Colour.WHITE) || isKingCheckmated(Colour.BLACK) ||
                isStalemate(Colour.WHITE) || isStalemate(Colour.BLACK);
    }

    /**
     * @return The winning player when the game is over
     */
    public Player gameOver() {
        if (isKingCheckmated(Colour.WHITE)) {
            return blackPlayer;
        } else if (isKingCheckmated(Colour.BLACK)) {
            return whitePlayer;
        } else {
            return null;
        }
    }

    public Colour getSelectedColour() {
        return selectedColour;
    }

    public Player getPlayerToMove() {
        return PlayerToMove;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }
}
