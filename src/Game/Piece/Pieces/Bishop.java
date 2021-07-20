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

public class Bishop extends Piece
{
    /**
     * Constructor for a bishop piece
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Bishop(Coordinate coordinate, Colour colour, PieceType type)
    {
        super(coordinate, colour, type);
        //TODO colour == Colour.WHITE ? (pieceImage = Database.getWhiteImage) : (pieceImage = Database.getBlackImage);
    }
    
    /**
     * Takes a board object and calculates the available bishop moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc..
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board)
    {
        //Find Diagonal moves
        List<Move> LegalMoves = new ArrayList<>();
        List<Square> PositiveDiagonal = new ArrayList<>();
        List<Square> NegativeDiagonal = new ArrayList<>();
        Square[][] BoardArray = board.getBoardArray();
        
        for (Square[] Row : BoardArray)
        {
            for (Square square : Row)
            {
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = getPieceCoordinate().getFile() - Destination.getFile();
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();
            
                if ((XDisplacement != 0)  && Math.abs(XDisplacement) == Math.abs(YDisplacement)){
                    if(YDisplacement / XDisplacement == 1){
                        PositiveDiagonal.add(square);
                    }else if (YDisplacement / XDisplacement == -1){
                        NegativeDiagonal.add(square);
                    }
                }
                else if ((XDisplacement == 0) && (YDisplacement == 0)){
                    PositiveDiagonal.add(square);
                    NegativeDiagonal.add(square);
                }
            }
        }
        
        PositiveDiagonal = CheckCollisions(PositiveDiagonal);
        NegativeDiagonal = CheckCollisions(NegativeDiagonal);
        
        List<Square> PossibleDestinations = PositiveDiagonal;
        PossibleDestinations.addAll(NegativeDiagonal);
    
        //TODO Look for check and remove any squares which don't remove check
        
        /*Remove Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        
        return DestinationsToMoves(PossibleDestinations, BoardArray);
    }
    
    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (Bishop = B)
     */
    @Override
    public String PieceTypeToNotation()
    {
        return "B";
    }
    
}
