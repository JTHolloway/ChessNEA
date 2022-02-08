package Game;

import Game.Board.Board;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.King;
import Game.Piece.Pieces.Pawn;
import LibaryFunctions.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Minimax {

    private final Game game;
    private Move currentBestMove;
    private int[] pieceValues;

    public Minimax(Game game) {
        this.game = game;
    }

    /**
     * This method alters the computer difficulty based on the users ELO rank and calls the minimax
     * algorithm to find the best move.
     * @param computerColour The colour which the computer is playing as
     * @return the best move found by the minimax algorithm
     */
    public Move getComputerMove(Colour computerColour) {
        int depth = 0;
        pieceValues = new int[]{200, 9, 5, 3, 3, 1};

        if (Repository.getCurrentUser().getUserID().equals("Guest")) {
            pieceValues = new int[]{200, 9, 5, 3, 3, 1};
            depth = 3;
        } else if (Repository.getCurrentUser().getStatistics().getELO() <= 800) {
            pieceValues = new int[]{200, 4, 3, 2, 2, 1};
            depth = 2;
        } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1300) {
            depth = 3;
        } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1600) {
            depth = 4;
        } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1900) {
            depth = 4;
            pieceValues = new int[]{200, 11, 6, 3, 3, 1};
        } else if (Repository.getCurrentUser().getStatistics().getELO() <= 2400) {
            depth = 5;
            pieceValues = new int[]{200, 11, 6, 3, 3, 1};
        } else {
            depth = 6;
            pieceValues = new int[]{200, 11, 6, 3, 3, 1};
        }

        depth = 2;
        minimaxTraversal(game.getBoard(), depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, computerColour);

        return currentBestMove;
    }

    /**
     * The method created a move tree to find the highest value move which can be made by the computer
     * @param currentPosition The current state of the board for which the move is to be made
     * @param searchDepth the level of depth to which the move tree will be traversed.
     *                    Higher depths means the computer can see further ahead and therefore play better.
     * @param alpha A value used for alpha-beta pruning to prune any branches which dont contain the best move.
     * @param beta A value used for alpha-beta pruning to prune any branches which dont contain the best move.
     * @param maximizer a boolean value to determine whether or not the maximizing colours moves are being checked
     * @param maximizingColour The colour which the computer is playing as
     * @return a double value for the value of the bottom of a branch, which can be compared to other values until the highest
     * value branch is located
     */
    private double minimaxTraversal(Board currentPosition, int searchDepth, double alpha, double beta, boolean maximizer, Colour maximizingColour) {
        if (searchDepth == 0 || game.isGameOver()) {
            return evaluateBranch(currentPosition, maximizingColour);
        }
        List<Move> moves = getChildren(currentPosition, maximizer, maximizingColour);
        if (moves.get(0).getMovedPiece().getColour() == maximizingColour) {
            Random r = new Random();
            currentBestMove = moves.get(r.nextInt(moves.size()));
        }

        //Get castling availability using XOR operator
        King king = (maximizer ^ maximizingColour == Colour.WHITE) ? (King) currentPosition.getKings()[1] : (King) currentPosition.getKings()[0];
        final CastlingAvailability castlingAvailability = king.getCastlingAvailability();

        //Store Current EnPassant Pawn
        final Pawn enPassantPawn = currentPosition.getEnPassantPawn();

        if (maximizer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Move currentNode : moves) {
                Game.MakeMove(currentNode, currentPosition);

                double nodeEvaluation = minimaxTraversal(currentPosition, searchDepth - 1, alpha, beta, false, maximizingColour);
                Game.reverseMove(currentNode, currentPosition, castlingAvailability, enPassantPawn);

                if (nodeEvaluation > maxEvaluation) {
                    maxEvaluation = nodeEvaluation;
                    if (currentNode.getMovedPiece().getColour() == maximizingColour) {
                        currentBestMove = currentNode;
                    }
                }
                alpha = Math.max(alpha, nodeEvaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEvaluation;
        } else {
            double minEvaluation = Double.POSITIVE_INFINITY;
            for (Move currentNode : moves) {
                Game.MakeMove(currentNode, currentPosition);

                double nodeEvaluation = minimaxTraversal(currentPosition, searchDepth - 1, alpha, beta, true, maximizingColour);
                Game.reverseMove(currentNode, currentPosition, castlingAvailability, enPassantPawn);

                if (nodeEvaluation < minEvaluation) {
                    minEvaluation = nodeEvaluation;
                    if (currentNode.getMovedPiece().getColour() == maximizingColour) {
                        currentBestMove = currentNode;
                    }
                }
                beta = Math.min(beta, nodeEvaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEvaluation;
        }
    }

    /**
     * Gets all child nodes of a move, where a child is any subsequent move which can be made after the previous move.
     * @param maximizer a boolean value to determine whether or not the maximizing colours moves are being checked
     * @param maximizingColour The colour which the computer is playing as
     * @return a list of move objects for each possible move which can be made by a colour.
     */
    private List<Move> getChildren(Board board, boolean maximizer, Colour maximizingColour) {
        List<Move> children = new ArrayList<>();
        List<Piece> pieces;

        if (maximizer) {
            pieces = maximizingColour == Colour.WHITE ? board.getWhitePieces() : board.getBlackPieces();
        } else {
            pieces = maximizingColour == Colour.WHITE ? board.getBlackPieces() : board.getWhitePieces();
        }

        for (Piece piece : pieces) {
            children.addAll(piece.CalculateValidMoves(board));
        }
        return children;
    }

    /**
     * Calculates the total value of a move branch
     * @param maximizingColour The colour which the computer is playing as
     * @return the value of the branch.
     */
    private double evaluateBranch(Board board, Colour maximizingColour) {
        double branchValue = 0;

        if (game.isKingCheckmated(Colour.BLACK)) {
            branchValue = Double.POSITIVE_INFINITY;

        } else if (game.isKingCheckmated(Colour.WHITE)) {
            branchValue = Double.NEGATIVE_INFINITY;

        } else {
            /*
            Material Score (Including check, but not checkmate). This always returns
            whites material score but will be made negative if black is the maximizer
            */
            branchValue += calculateMaterialScore(pieceValues);

            //Mobility Score (Number of moves which can be made by each side * 0.05)
            branchValue += calculateMobilityScore(board);
        }

        //Make evaluation positive or negative based on the maximisingColour
        if (maximizingColour == Colour.BLACK) {
            return ((100 * branchValue) * -1);
        } else return (100 * branchValue);
    }

    /**
     * Calculates a value based on whites current position, higher values map to a stronger position/advantage whereas lower values
     * map to a weaker position/disadvantage for white.
     *
     * @param pieceValues The values to assign each piece in pawns, eg - a queen is worth 9 pawns etc.
     * @return a double value of the material score. Value will be negative if black is winning and positive if white is winning
     */
    private double calculateMaterialScore(int[] pieceValues) {
        double materialScore = 0;
        PieceType[] pieceValueReference = {PieceType.KING, PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT, PieceType.PAWN};

        //Gets material score for all pieces which aren't kings
        for (int index = 1; index <= 5; index++) {
            int noOfWhitePiecesOfType = 0;
            int noOfBlackPiecesOfType = 0;

            //Finds the amount of pieces of that type a colour has. Eg - white may have 5 pawns left.
            for (Piece piece : game.getBoard().getWhitePieces()) {
                if (piece.getType() == pieceValueReference[index]) {
                    noOfWhitePiecesOfType++;
                }
            }
            for (Piece piece : game.getBoard().getBlackPieces()) {
                if (piece.getType() == pieceValueReference[index]) {
                    noOfBlackPiecesOfType++;
                }
            }

            materialScore += pieceValues[index] * (noOfWhitePiecesOfType - noOfBlackPiecesOfType);
        }

        //Material score for kings and check
        int whiteChecked = 0, blackChecked = 0;
        if (Game.isKingChecked(Colour.WHITE, game.getBoard())) {
            whiteChecked++;
        } else if (Game.isKingChecked(Colour.BLACK, game.getBoard())) {
            blackChecked++;
        }
        materialScore += pieceValues[0] * (blackChecked - whiteChecked);

        return materialScore;
    }

    /**
     * @return a score value based on how many moves one player can make in relation to the other player.
     */
    private double calculateMobilityScore(Board board) {
        int whiteMobility = getChildren(board, true, Colour.WHITE).size();
        int blackMobility = getChildren(board, true, Colour.BLACK).size();

        return 0.05 * (whiteMobility - blackMobility);
    }
}
