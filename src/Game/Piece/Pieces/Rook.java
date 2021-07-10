package Game.Piece.Pieces;

import ArrayHandling.ArrayManipulation;
import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece
{
    /**
     * Constructor for a rook piece
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Rook(Coordinate coordinate, Colour colour, PieceType type)
    {
        super(coordinate, colour, type);
        //TODO colour == Colour.WHITE ? (pieceImage = Database.getWhiteImage) : (pieceImage = Database.getBlackImage);
    }
    
    /**
     * Takes a board object and calculates the available Rook moves so that
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
        List<Square> PossibleDestinations = new ArrayList<>();
        
        List<Square> Row = ArrayManipulation.ArrayToRow(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));
        List<Square> Column = ArrayManipulation.ArrayToColumn(BoardArray, getPieceCoordinate().GetSquareAt(BoardArray));
        
        //Check For any path obstructions regardless of piece colour
        Column = CheckCollisions(Column);
        Row = CheckCollisions(Row);
        PossibleDestinations.addAll(Column);
        PossibleDestinations.addAll(Row);
    
        //TODO Look for check and remove any squares which don't remove check

        /*Remove Square that moving piece is occupying and squares which
        cannot be captured (because a piece of equal colour occupies it)*/
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        
        //TODO Create legal moves
        
        for (Square square : PossibleDestinations)
        {
            System.out.println(square.ReturnCoordinate().CoordinateToNotation());
        }
        
        return LegalMoves;
    }
    
    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (Rook = R)
     */
    @Override
    public String PieceTypeToNotation()
    {
        return "R";
    }
    
}
