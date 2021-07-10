import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Piece.Pieces.Knight;
import Game.Piece.Pieces.Pawn;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.Queen;

public class main {

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
        hi.ReturnSquare(new Coordinate(6,5)).ReturnPiece().CalculateValidMoves(hi);
        
        
    }

}
