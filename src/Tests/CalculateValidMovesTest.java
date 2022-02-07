package Tests;

import Game.Coordinate;
import Game.Game;
import Game.Move.Move;

import java.util.List;

public class CalculateValidMovesTest {

    public static void findMobilityOfPiece(Game g, Coordinate c) {
        System.out.println("\n");

        List<Move> moves = g.getBoard().ReturnSquare(c).ReturnPiece().CalculateValidMoves(g.getBoard());
        System.out.println("Mobility of " + g.getBoard().ReturnSquare(c).ReturnPiece().getColour() + " " +
                g.getBoard().ReturnSquare(c).ReturnPiece().getType() + " at " + g.getBoard().ReturnSquare(c).ReturnCoordinate().CoordinateToNotation() +
                ": " + moves.size());

        for (Move move : moves) {
            if (move.wasCapture()) {
                System.out.println(move.getMovedPiece().getColour() + " " + move.getMovedPiece().getType() + " At " + move.getStartPosition().ReturnCoordinate().CoordinateToNotation() + " To " +
                        move.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", Captures: " + move.getCapturedPiece().getColour() + " " + move.getCapturedPiece().getType() + " At " +
                        move.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(move.getMovedPiece().getColour() + " " + move.getMovedPiece().getType() + " At " + move.getStartPosition().ReturnCoordinate().CoordinateToNotation() + " To " +
                        move.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", Captures: NULL");
            }
        }
    }
}
