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
        List<Move> moves = hi.ReturnSquare(new Coordinate(6,5)).ReturnPiece().CalculateValidMoves(hi);
        System.out.println(hi.ReturnSquare(new Coordinate(6,5)).ReturnPiece().PieceTypeToNotation());
    
        System.out.println("\n");
        
        for (Move m : moves) {
            if (m.wasCaptured()){
                System.out.println(m.getMovedPiece().getType() + " ," + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + " ," +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + " ," + m.getCapturedPiece().getType());
            }else{
                System.out.println(m.getMovedPiece().getType() + " ," + m.getStartPosition().ReturnCoordinate().CoordinateToNotation() + " ," +
                        m.getEndPosition().ReturnCoordinate().CoordinateToNotation() + " ,NULL");
            }
            
        }
    }

}
