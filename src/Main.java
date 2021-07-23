import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Pieces.Knight;
import Game.Piece.Pieces.Pawn;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.Queen;

import java.util.List;

public class Main
{

    public static void main(String[] args) {
//        Square e4 = new Square.EmptySquare(5,4);
//        e4 = new Square.OccupiedSquare(5,4,new Pawn(new Coordinate(5,4),Colour.WHITE,PieceType.PAWN));
//
//        //Square empty = new Square.EmptySquare(5,4);
//
//        Piece pawn = new Pawn(new Coordinate(2,2), Colour.WHITE, PieceType.PAWN);
//        pawn.getCoordinate();
    
        Board hi = new Board();
        //hi.ReturnSquare(new Coordinate(3,5)).ReturnPiece().CalculateValidMoves(hi);
        List<Move> moves = hi.ReturnSquare(new Coordinate(4,5)).ReturnPiece().CalculateValidMoves(hi);
        //moves.addAll(hi.ReturnSquare(new Coordinate(5,5)).ReturnPiece().CalculateValidMoves(hi));
        
        for (Move m : moves) {
            if (m.wasCapture()){
                System.out.println(m.getMovedPiece().getColour() +" " +m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + m.getCapturedPiece().getType() + ", " + m.getCapturedPiece().getColour() + ", " +
                        m.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            }else{
                System.out.println(m.getMovedPiece().getColour() +" " +m.getMovedPiece().getType() + ", " + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
            
        }
    }

}
