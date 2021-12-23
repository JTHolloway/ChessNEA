package Game;

import Game.Board.Board;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.King;
import Game.Piece.Pieces.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Minimax {

    private final Game game;
    private Move currentBestMove;

    private int temp = 0; // TODO remove

    public Minimax(Game game) {
        this.game = game;
    }

    public double minimaxTraversal(Board currentPosition, int searchDepth, double alpha, double beta, boolean maximizer, Colour maximizingColour) {
        temp++;
        //System.out.println("");
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
                //game.getBoard().PrintBoard(); //todo Remove
                //if (currentNode.wasCapture()) System.out.println("Capture"); //todo remove
                double nodeEvaluation = minimaxTraversal(currentPosition, searchDepth - 1, alpha, beta, false, maximizingColour);
                //System.out.println("Node Evaluation: " + nodeEvaluation);
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
                //game.getBoard().PrintBoard(); //todo remove
                //if (currentNode.wasCapture()) System.out.println("Capture"); //todo remove
                double nodeEvaluation = minimaxTraversal(currentPosition, searchDepth - 1, alpha, beta, true, maximizingColour);
                //System.out.println("min Node evaluation: " + nodeEvaluation);
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

    private double evaluateBranch(Board board, Colour maximizingColour) {
        double branchValue = 0;
        int[] pieceValues = {200, 9, 5, 3, 3, 1}; //todo can be edited later depending on difficulty

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

            //Position Score
            branchValue += calculatePositionScore(board);

        }

        //Make evaluation positive or negative based on the maximisingColour
        if (maximizingColour == Colour.BLACK) {
            return ((100 * branchValue) * -1);
        } else return (100 * branchValue);
    }

    private double calculatePositionScore(Board board) {
        return 0;
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
//            System.out.println("No. of black " + pieceValueReference[index] + "'s = " + noOfBlackPiecesOfType);
//            System.out.println("No. of white " + pieceValueReference[index] + "'s = " + noOfWhitePiecesOfType);

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

        //System.out.println("material score: " + materialScore);

        //todo Decrease score based on whether doubled, blocked or isolated pawns are present.

        return materialScore;
    }

    private double calculateMobilityScore(Board board) {
        int whiteMobility = getChildren(board, true, Colour.WHITE).size();
        int blackMobility = getChildren(board, true, Colour.BLACK).size();

        //System.out.println("Black Mobility: " + blackMobility + ", White mobility: " + whiteMobility + ", Mobility Score = " + (0.05 * (whiteMobility - blackMobility)));
        //todo maybe decrease score based on trapped pieces / forks / skewers

        return 0.05 * (whiteMobility - blackMobility);
    }

    //todo remove
    public Move getCurrentBestMove() {
        System.out.println("Temp = " + temp);
        return currentBestMove;
    }
}
