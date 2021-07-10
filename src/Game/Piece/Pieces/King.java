package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import java.util.List;

public class King extends Piece
{
    /**
     * Constructor for a king piece
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public King(Coordinate coordinate, Colour colour, PieceType type)
    {
        super(coordinate, colour, type);
        //TODO colour == Colour.WHITE ? (pieceImage = Database.getWhiteImage) : (pieceImage = Database.getBlackImage);
    }
    
    /**
     * Takes a board object and calculates the available king moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc..
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board)
    {
        //TODO calculate King moves
        //TODO Create legal moves
        return null;
    }
    
    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (King = K)
     */
    @Override
    public String PieceTypeToNotation()
    {
        return "K";
    }
    
    //TODO Check, CheckMate and StaleMate handling
}
