package Game;

import Game.Board.Board;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Minimax {

    private final Game game;
    private Move currentBestMove;

//    private final CastlingAvailability blacksCastlingAvailability; //todo remove these variables
//    private final CastlingAvailability whitesCastlingAvailability;
//    private final Pawn enPassantPawn;
//    private final ArrayList<Piece> whitePieces;
//    private final ArrayList<Piece> blackPieces;

    private int temp = 0; // TODO remove

    public Minimax(Game game) {
        this.game = game;
        //todo copies reference only?
//        blacksCastlingAvailability = ((King) game.getBoard().getKings()[1]).getCastlingAvailability();
//        whitesCastlingAvailability = ((King) game.getBoard().getKings()[0]).getCastlingAvailability();
//        enPassantPawn = game.getBoard().getEnPassantPawn();
//        whitePieces = new ArrayList<>(game.getBoard().getWhitePieces());
//        blackPieces = new ArrayList<>(game.getBoard().getBlackPieces());
    }

    public double minimaxTraversal(Board currentPosition, int searchDepth, double alpha, double beta, boolean maximizer, Colour maximizingColour) {
        temp++;
        if (searchDepth == 0 || game.isGameOver()) {
            return evaluateBranch(currentPosition, maximizingColour);
        }
        List<Move> moves = getChildren(currentPosition, maximizer, maximizingColour);
        Random r = new Random();
        currentBestMove = moves.get(r.nextInt(moves.size() - 1));

        if (maximizer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Move currentNode : moves) {
                //TODO maybe store board data here so the move can be reversed exactly??
                Game.MakeMove(currentNode, currentPosition);
                double nodeEvaluation = minimaxTraversal(currentPosition, searchDepth - 1, alpha, beta, false, maximizingColour);
                System.out.println("Node Evaluation: " + nodeEvaluation);
                Game.reverseMove(currentNode, currentPosition);
                if (nodeEvaluation > maxEvaluation) {
                    maxEvaluation = nodeEvaluation;
                    System.out.println("Highest");
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
                System.out.println("min Node evaluation: " + nodeEvaluation);
                Game.reverseMove(currentNode, currentPosition);
                if (nodeEvaluation < minEvaluation) {
                    minEvaluation = nodeEvaluation;
                    System.out.println("Lowest");
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
            System.out.println("No. of black " + pieceValueReference[index] + "'s = " + noOfBlackPiecesOfType);
            System.out.println("No. of white " + pieceValueReference[index] + "'s = " + noOfWhitePiecesOfType);

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

        System.out.println("material score: " + materialScore);

        //todo Decrease score based on whether doubled, blocked or isolated pawns are present.

        return materialScore;
    }

    private double calculateMobilityScore(Board board) {
        int whiteMobility = getChildren(board, true, Colour.WHITE).size();
        int blackMobility = getChildren(board, true, Colour.BLACK).size();

        System.out.println("Black Mobility: " + blackMobility + ", White mobility: " + whiteMobility + ", Mobility Score = " + (0.05 * (whiteMobility - blackMobility)));
        //todo maybe decrease score based on trapped pieces / forks / skewers

        return 0.05 * (whiteMobility - blackMobility);
    }

    //todo remove
    public Move getCurrentBestMove() {
        System.out.println("Temp = " + temp);
        return currentBestMove;
    }
}
