package Game.Piece.Pieces;

import Game.Board.Board;
import Game.Board.Square;
import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    
    private boolean hasMoved = false;
    
    /**
     * Constructor for a pawn piece
     * @param coordinate A coordinate object identifying the tile coordinate of the piece
     * @param colour The colour of a piece
     * @param type The piece type which is inheriting from the piece class (King, Queen Bishop etc..)
     */
    public Pawn(Coordinate coordinate, Colour colour, PieceType type)
    {
        super(coordinate, colour, type);
        //TODO colour == Colour.WHITE ? (pieceImage = Database.getWhiteImage) : (pieceImage = Database.getBlackImage);
    }
    
    /**
     * Takes a board object and calculates the available pawn moves so that
     * illegal moves cannot be made
     * Takes into account that check may be present on the board etc..
     * @param board An instance of the current board (which contains an array of squares)
     * @return a list of all available/legal moves (where move is a move object)
     */
    @Override
    public List<Move> CalculateValidMoves(Board board)
    {
        Square[][] BoardArray = board.getBoardArray();
        List<Square> PossibleDestinations = new ArrayList<>();
        boolean EnPassantPerformed = false;
    
        for (Square[] Row : BoardArray) {
            for (Square square : Row) {
                Coordinate Destination = square.ReturnCoordinate();
                int XDisplacement = Math.abs(getPieceCoordinate().getFile() - Destination.getFile());
                int YDisplacement = getPieceCoordinate().getRank() - Destination.getRank();
                
                /*
                The pawn always moves two 1 in forwards, or diagonal when capturing, Therefore the
                Y displacement is always 1 for white and -1 for black (because forward for white is backwards for black)
                The absolute value of X Displacement is always <= 1,
                because it can move a maximum of 1 square either side when capturing.
                */
                if (getColour() == Colour.WHITE)
                {
                    if ((YDisplacement == -1) && (XDisplacement == 1))
                    {
                        PossibleDestinations.add(square);
                    }
                    else if ((YDisplacement == -1) && (XDisplacement == 0))
                    {
                        if (!square.SquareOccupied())
                        {
                            PossibleDestinations.add(square);
                        }
                    }
                    else if ((YDisplacement == -2) && (XDisplacement == 0))
                    {
                        if (!square.SquareOccupied() && !hasMoved)
                        {
                            PossibleDestinations.add(square);
                        }
                    }
                }
                else
                {
                    if ((YDisplacement == 1) && (XDisplacement == 1))
                    {
                        PossibleDestinations.add(square);
                    }
                    else if ((YDisplacement == 1) && (XDisplacement == 0))
                    {
                        if (!square.SquareOccupied())
                        {
                            PossibleDestinations.add(square);
                        }
                    }
                    else if ((YDisplacement == 2) && (XDisplacement == 0))
                    {
                        if (!square.SquareOccupied() && !hasMoved)
                        {
                            PossibleDestinations.add(square);
                        }
                    }
                }
            }
        }
        
        /*
        Remove squares which cannot be
        captured (because a piece of equal colour occupies it)
        */
        PossibleDestinations = RemoveRemainingInvalidDestinations(PossibleDestinations);
        
        /*
        Remove diagonals if they are empty because a pawn can only move diagonal if they are capturing another piece
         */
        List<Square> toBeRemoved = new ArrayList<>();
        for (Square square : PossibleDestinations) {
            if (board.getEnPassantPawn() == null){
                if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()){
                    toBeRemoved.add(square);
                }
            }
            else
            {
              if (!square.ReturnCoordinate().CompareCoordinates(board.getEnPassantDestination())){
                  if ((!square.SquareOccupied()) && getPieceCoordinate().getFile() != square.ReturnCoordinate().getFile()){
                      toBeRemoved.add(square);
                  }
              }
            }
        }
        
        PossibleDestinations.removeAll(toBeRemoved);
    
        //TODO Look for check and remove any squares which don't remove check
    
        return DestinationsToMoves(PossibleDestinations, BoardArray, board, EnPassantPerformed);
        
    }
    
    private List<Move> DestinationsToMoves(final List<Square> PossibleDestinations, final Square[][] BoardArray, final Board board,
                                           final boolean EnPassantPerformed)
    {
        List<Move> Moves = new ArrayList<>();
        
        for (Square square : PossibleDestinations) {
            if (EnPassantPerformed)
            {
                Square EnPassantDestination = board.getEnPassantDestination();
        
                if (square.ReturnCoordinate().CompareCoordinates(EnPassantDestination)){
                    //En Passant Move
                    Moves.add(new Move.EnPassantMove(PieceCoordinate.GetSquareAt(BoardArray), square, board.getEnPassantPawn(),
                            board.getEnPassantPawn().PieceCoordinate.GetSquareAt(BoardArray)));
                }
            }
            else if (square.SquareOccupied()){
                //Capturing move
                Moves.add(new Move.CapturingMove(PieceCoordinate.GetSquareAt(BoardArray), square, square.ReturnPiece()));
            }
            else{
                //General move
                Moves.add(new Move.RegularMove(PieceCoordinate.GetSquareAt(BoardArray), square));
            }
        }
        return Moves;
    }
    
    /**
     * Converts the type of piece to its Notation equivalent
     * @return a String with the type notation (Pawn = null)
     */
    @Override
    public String PieceTypeToNotation()
    {
        return "";
    }
    
    //TODO en passant handling (if a pawn moves 2 squares, then call baord.enpassentpawn)
    public void PawnMoved(Board board){
        hasMoved = true;
        
        //TODO IF PAWN HAS MOVED 2 SPACES THEN PAWN = EN PASSANT PAWN
        board.setEnPassantPawn(this);
    }
    
    //TODO pawn promotion handling
    //TODO double move handling
}
