import Game.Board.Board;
import Game.Colour;
import Game.Coordinate;
import Game.Game;
import Game.Minimax;
import Game.Move.Move;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//        UserStats userStatsTest = new UserStats("pro", 1000, 10, 5, 5, 0, new java.sql.Date(Calendar.getInstance().getTime().getTime()),
//                new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        User me = new User("1", "James123", "get", "james", "Holloway", "UK", userStatsTest);
//
//        new GUI_MainJFrame();
//        new GUI_LoginScreen();

        Game g = new Game();
        Board hi = g.getBoard();

        MinimaxMove(g, Colour.WHITE);
        MinimaxMove(g, Colour.BLACK);
        MinimaxMove(g, Colour.WHITE);
        MinimaxMove(g, Colour.BLACK);
    }


    public static void MinimaxMove(Game g, Colour colour) {
        Minimax x = new Minimax(g);
        x.minimaxTraversal(g.getBoard(), 4, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, colour);
        Move m = x.getCurrentBestMove();
        if (m == null) {
            System.out.println("No moves, Checkmate. Or wrong players turn");
        } else {
            if (m.wasCapture()) {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }
        Game.MakeMove(m, g.getBoard());

        g.getBoard().PrintBoard();
        System.out.println("\n");
    }


    public static void findMobilityOfPiece(Game g) {
        List<Move> moves = g.getBoard().ReturnSquare(new Coordinate(6, 1)).ReturnPiece().CalculateValidMoves(g.getBoard());
        System.out.println("Mobility of " + g.getBoard().ReturnSquare(new Coordinate(6, 1)).ReturnPiece().getColour() + " " +
                g.getBoard().ReturnSquare(new Coordinate(6, 1)).ReturnPiece().getType() + " at " + g.getBoard().ReturnSquare(new Coordinate(6, 1)).ReturnCoordinate().CoordinateToNotation() +
                ": " + moves.size());

        for (Move move : moves) {
            if (move.wasCapture()) {
                System.out.println(move.getMovedPiece().getColour() + " " + move.getMovedPiece().getType() + ", " + move.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        move.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + move.getCapturedPiece().getType() + ", " + move.getCapturedPiece().getColour() + ", " +
                        move.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(move.getMovedPiece().getColour() + " " + move.getMovedPiece().getType() + ", " + move.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        move.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }
    }
}
