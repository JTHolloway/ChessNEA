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
//        //new GUI_LoginScreen();
//
//        Square e4 = new Square.EmptySquare(5, 4);
//        e4 = new Square.OccupiedSquare(5, 4, new Pawn(new Coordinate(5, 4), Colour.WHITE, PieceType.PAWN));
//
//        Square empty = new Square.EmptySquare(5,4);
//
//        Piece pawn = new Pawn(new Coordinate(2, 2), Colour.WHITE, PieceType.PAWN);
//        //pawn.getCoordinate();
        Game g = new Game();
//
        Board hi = g.getBoard();
//        hi.PrintBoard();
//        hi.ReturnSquare(new Coordinate(3,5)).ReturnPiece().CalculateValidMoves(hi);
        List<Move> moves = hi.ReturnSquare(new Coordinate(1, 2)).ReturnPiece().CalculateValidMoves(hi);
        //moves.addAll(hi.ReturnSquare(new Coordinate(5,5)).ReturnPiece().CalculateValidMoves(hi));

        for (Move m : moves) {
            if (m.wasCapture()) {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }

        //((Pawn) hi.ReturnSquare(new Coordinate(1,2)).ReturnPiece()).FindCheckingSquares(hi);

        System.out.println("\n");
        g.getBoard().PrintBoard();
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        moves = g.getBoard().ReturnSquare(new Coordinate(5, 2)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(1), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(4, 1)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(3), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

        moves = g.getBoard().ReturnSquare(new Coordinate(8, 5)).ReturnPiece().CalculateValidMoves(g.getBoard());

        for (Move m : moves) {
            if (m.wasCapture()) {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }

        Game.MakeMove(moves.get(5), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));


        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(5, 4)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(0), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(5, 5)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(0), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(5, 6)).ReturnPiece().CalculateValidMoves(g.getBoard());
        for (Move m : moves) {
            if (m.wasCapture()) {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }
        Game.MakeMove(moves.get(0), g.getBoard());
        System.out.println("IS BLACK CHECKED: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(5, 8)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(8, 2)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(0), g.getBoard());
        System.out.println("IS C4 a threat to black: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(3, 4)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(2, 1)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(0), g.getBoard());
        System.out.println("IS A5 a threat to black: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(1, 5)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(1, 3)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(1), g.getBoard());
        System.out.println("IS A5 a threat to black: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(1, 5)), g.getBoard()));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(2, 2)).ReturnPiece().CalculateValidMoves(g.getBoard());
        Game.MakeMove(moves.get(1), g.getBoard());
        System.out.println("IS A5 a threat to black: " + Game.isThreatenedSquare(Colour.BLACK, g.getBoard().ReturnSquare(new Coordinate(1, 5)), g.getBoard()));
        System.out.println("Checked?: " + Game.isKingChecked(Colour.BLACK, g.getBoard()));
        System.out.println("Check Mate?: " + g.isKingCheckmated(Colour.BLACK));

        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        moves = g.getBoard().ReturnSquare(new Coordinate(6, 7)).ReturnPiece().CalculateValidMoves(g.getBoard());
        for (Move m : moves) {
            if (m.wasCapture()) {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        }
        Game.MakeMove(moves.get(4), g.getBoard());
        System.out.println("Checked?: " + Game.isKingChecked(Colour.BLACK, g.getBoard()));
        System.out.println("Check Mate?: " + g.isKingCheckmated(Colour.BLACK));

//        System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
//        moves = g.getBoard().ReturnSquare(new Coordinate(3, 4)).ReturnPiece().CalculateValidMoves(g.getBoard());
//        for (Move m : moves) {
//            if (m.wasCapture()) {
//                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
//                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
//                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
//            } else {
//                System.out.println(m.getMovedPiece().getColour() + " " + m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
//                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
//            }
//        }
//        Game.MakeMove(moves.get(4));
        System.out.println("Checked?: " + Game.isKingChecked(Colour.BLACK, g.getBoard()));
        System.out.println("Check Mate?: " + g.isKingCheckmated(Colour.BLACK));

        Minimax x = new Minimax(g);
        x.minimaxTraversal(g.getBoard(), 4, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, Colour.BLACK);
        Move m = x.getCurrentBestMove();
        if (m == null) {
            System.out.println("No, moves, Checkmate");
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

        g.getBoard().PrintBoard();
    }

}
