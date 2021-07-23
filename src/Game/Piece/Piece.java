package Game.Piece;

import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected Coordinate PieceCoordinate;
    protected final Colour colour;
    protected final PieceType type;
    protected ImageIcon pieceImage;

    /**
     * Constructor for a piece
     * @param PieceCoordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Piece(Coordinate PieceCoordinate, Colour colour, PieceType type) {
        this.PieceCoordinate = PieceCoordinate;
        this.colour = colour;
        this.type = type;
    }

    /**
     * Takes a board object and calculates the available moves of the current piece
     * Takes into account that check may be present on the board etc..
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    public abstract List<Move> CalculateValidMoves(final Board board);

    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (eg. Queen = Q)
     */
    public abstract String PieceTypeToNotation();
    
    /**
     * Checks to make sure all possible destinations can be accessed by checking if other pieces obstruct the path
     * and removes them from the list possible destinations.
     * @param OneDimensionalArray a one-dimensional section or row of the board array which contains the piece
     * @return a list of squares excluding any squares behind any obstructing pieces
     */
    protected List<Square> CheckCollisions(List<Square> OneDimensionalArray)
    {
        int PiecePositionIndex = 0;
        
        //Find index of moving piece
        for (int i = 0; i < OneDimensionalArray.size(); i++)
        {
            Square square = OneDimensionalArray.get(i);
            if (getPieceCoordinate().CompareCoordinates(square))
            {
                PiecePositionIndex = i;
                break;
            }
        }
        
        //Loop back to find nearest path obstruction backwards
        int BackIndex = -1;
        for (int i = PiecePositionIndex; i >= 0; i--) {
            Square square = OneDimensionalArray.get(i);
            if (i == 0 && BackIndex == -1){
                BackIndex = i;
            } else if (square.SquareOccupied()  &&  i != PiecePositionIndex){
                BackIndex = i;
                break;
            }
        }
        
        //Loop through to find nearest path obstruction Forwards
        int FrontIndex = -1;
        for (int i = PiecePositionIndex; i < OneDimensionalArray.size(); i++) {
            Square square = OneDimensionalArray.get(i);
            if (i == 7 && FrontIndex == -1){
                FrontIndex = i;
            } else if (square.SquareOccupied()  &&  i != PiecePositionIndex){
                FrontIndex = i;
                break;
            }
        }
        
        OneDimensionalArray = OneDimensionalArray.subList(BackIndex, FrontIndex + 1);
        return OneDimensionalArray;
    }
    
    /**
     * Remove Square that moving piece is occupying and squares which
     *      cannot be captured (because a piece of equal colour occupies it)
     * @param PossibleDestinations A list of possible destinations which also includes invalid moves such as
     *                             destinations with a piece of the same colour
     * @return a list of squares which contains only valid moves
     */
    protected List<Square> RemoveRemainingInvalidDestinations(List<Square> PossibleDestinations){
        List<Square> ToBeRemoved = new ArrayList<>();
        for (Square square : PossibleDestinations)
        {
            if (getPieceCoordinate().CompareCoordinates(square))
            {
                ToBeRemoved.add(square);
            }
            else if (square.SquareOccupied())
            {
                if (square.ReturnPiece().getColour() == getColour()){
                    ToBeRemoved.add(square);
                }
            }
        }
        PossibleDestinations.removeAll(ToBeRemoved);
        return PossibleDestinations;
    }
    
    //TODO: Decide weather this class belongs in the piece class
    protected List<Move> DestinationsToMoves(final List<Square> PossibleDestinations, final Square[][] Board){
        List<Move> Moves = new ArrayList<>();
        for (Square square : PossibleDestinations) {
            if (square.SquareOccupied()){
                //Capturing move
                Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(Board), square, square.ReturnPiece()));
            }else{
                //General move
                Moves.add(new Move.RegularMove(PieceCoordinate.GetSquareAt(Board), square));
            }
        }
        return Moves;
    }
    
    //TODO find check, checked squares and checking piece

    /*Getter methods for all member variables*/
    public Coordinate getPieceCoordinate() {
        return PieceCoordinate;
    }
    public Colour getColour() {
        return colour;
    }
    public PieceType getType() {
        return type;
    }
    public ImageIcon getPieceImage() {
        return pieceImage;
    }
    
    /*Setter method for Coordinate (a pieces coordinate can change but its type and colour cannot)*/
    public void setPieceCoordinate(Coordinate pieceCoordinate) {
        this.PieceCoordinate = pieceCoordinate;
    }
}
