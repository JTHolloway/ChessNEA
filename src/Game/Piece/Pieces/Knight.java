package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece
{
    /**
     * Constructor for a knight piece
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Knight(final Coordinate coordinate, final Colour colour, final PieceType type)
    {
        super(coordinate, colour, type);
        //TODO colour == Colour.WHITE ? (pieceImage = Database.getWhiteImage) : (pieceImage = Database.getBlackImage);
    }
    
    /**
     * Takes a board object and calculates the available Knight moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc..
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board)
    {
        List<Move> LegalMoves = new ArrayList<>();
        Square[][] BoardArray = board.getBoardArray();
        for (Square[] Row : BoardArray) {
            for (Square square : Row) {
                Coordinate Destination = square.ReturnCoordinate();
                Square OriginSquare = PieceCoordinate.GetSquareAt(BoardArray);
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = Math.abs(getPieceCoordinate().getRank() - Destination.getRank());
                
                /*
                The knight always moves two squares in one direction and then 1 square perpendicular, Therefore the
                product of x and y displacements is always 1*2 = 2
                 */
                if(XDisplacement * YDisplacement == 2){
                    if (square.SquareOccupied()){  //If the destination square is not Empty then;
                        if (!(square.ReturnPiece().getColour() == getColour())){ //If the destination square DOES NOT contain a piece of the same colour then;
                            //LegalMoves.add(new Move.CapturingMove(OriginSquare, square, square.ReturnPiece()));
                            System.out.println(Destination.FileToNotation() + "" + Destination.getRank());
                        }
                    }else{
                        LegalMoves.add(new Move(OriginSquare, square));
                        System.out.println(Destination.FileToNotation() + "" + Destination.getRank());
                    }
                }
            }
        }
        //TODO Look for check
        
        return LegalMoves;
    }
    
    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (Knight = N)
     */
    @Override
    public String PieceTypeToNotation()
    {
        return "N";
    }
}
