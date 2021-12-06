package Game;

import Game.Board.Board;
import Game.Board.Square;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.Pieces.King;
import Game.Piece.Pieces.Pawn;
import LibaryFunctions.Stack;

import java.util.ArrayList;
import java.util.List;

public final class Minimax {

    private final Game game;
    private final CastlingAvailability blacksCastlingAvailability;
    private final CastlingAvailability whitesCastlingAvailability;
    private final Pawn enPassantPawn;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;
    private Stack branchValueStack;

    public Minimax(Game game) {
        this.game = game;
        //todo copies reference only?
        blacksCastlingAvailability = ((King) game.getBoard().getKings()[1]).getCastlingAvailability();
        whitesCastlingAvailability = ((King) game.getBoard().getKings()[0]).getCastlingAvailability();
        enPassantPawn = game.getBoard().getEnPassantPawn();
        whitePieces = new ArrayList<>(game.getBoard().getWhitePieces());
        blackPieces = new ArrayList<>(game.getBoard().getBlackPieces());
    }

    private int minimaxTraversal(Board board, int searchDepth, int alpha, int beta, boolean maximizer, Colour maximizingColour) {
        if (searchDepth == 0 || game.isGameOver()) {
            return evaluateBranch();
        }
        List<Move> moves = getChildren(board, maximizer);
        Move currentBestMove = moves.get(0);

        if (maximizer) {
            int maxEvaluation = Integer.MIN_VALUE;
            for (Move currentNode : moves) {
                //TODO maybe store board data here so the move can be reversed exactly??
                game.MakeMove(currentNode); //todo make board a parameter??
                int nodeEvaluation = minimaxTraversal(board, searchDepth - 1, alpha, beta, false, maximizingColour);
                reverseMove(currentNode, board);
                if (nodeEvaluation > maxEvaluation) {
                    maxEvaluation = nodeEvaluation;
                    currentBestMove = currentNode;
                }
                alpha = Math.max(alpha, nodeEvaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEvaluation;
        } else {
            int minEvaluation = Integer.MAX_VALUE;
            for (Move currentNode : moves) {
                game.MakeMove(currentNode); //todo make board a parameter??
                int nodeEvaluation = minimaxTraversal(board, searchDepth - 1, alpha, beta, true, maximizingColour);
                reverseMove(currentNode, board);
                if (nodeEvaluation < minEvaluation) {
                    minEvaluation = nodeEvaluation;
                    currentBestMove = currentNode;
                }
                beta = Math.min(beta, nodeEvaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEvaluation;
        }
    }

    private List<Move> getChildren(Board board, boolean maximizer) {
        List<Move> children = new ArrayList<>();
        List<Piece> pieces = maximizer ? board.getWhitePieces() : board.getBlackPieces();
        for (Piece piece : pieces) {
            children.addAll(piece.CalculateValidMoves(board));
        }
        return children;
    }

    private void reverseMove(Move move, Board board) {
        int OriginX = move.getStartPosition().ReturnCoordinate().getFile();
        int OriginY = move.getStartPosition().ReturnCoordinate().getRank();
        int DestinationX = move.getEndPosition().ReturnCoordinate().getFile();
        int DestinationY = move.getEndPosition().ReturnCoordinate().getRank();

        //Moves the piece back to where it started
        board.getBoardArray()[OriginY - 1][OriginX - 1] =
                new Square.OccupiedSquare(OriginX, OriginY, move.getMovedPiece());
        move.getMovedPiece().setPieceCoordinate(move.getStartPosition().ReturnCoordinate());

        //Puts any captured piece back to where it started and removes the other piece from that square
        if (move.wasCapture()) {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.OccupiedSquare(DestinationX, DestinationY, move.getCapturedPiece());
        } else {
            board.getBoardArray()[DestinationY - 1][DestinationX - 1] = new Square.EmptySquare(DestinationX, DestinationY);
        }
    }

    private void resetBoard() {
        Board board = game.getBoard();
        ((King) game.getBoard().getKings()[1]).setCastlingAvailability(blacksCastlingAvailability);
        ((King) game.getBoard().getKings()[0]).setCastlingAvailability(whitesCastlingAvailability);
        board.setEnPassantPawn(enPassantPawn);
        board.setWhitePieces(whitePieces);
        board.setBlackPieces(blackPieces);
    }

    private int evaluateBranch() {
        int branchValue = 0;
        while (!branchValueStack.isEmpty()) {
            branchValue += evaluateNode(branchValueStack.pop());
        }
        return branchValue;
    }

    private int evaluateNode(Move node) {
        //todo evaluate piece value
        //todo apply positive or negative sign depending on piece colour
        //todo check and checkmate have high value
        //todo difficulty levels

        int[] pieceValues = {1, 3, 3, 5, 9, 900};
    }

}
