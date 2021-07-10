package Game.Board;

import Game.Colour;
import Game.Coordinate;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.*;

public class Board {
    
    private final Square[][] BoardArray = new Square[8][8];
    
    /**
     * Board constructor
     * creates a new board instance which calls the InitialiseBoard() method
     * to setup the board
     */
    public Board()
    {
        this.InitialiseBoard();
    }
    
    /**
     * Sets up a new board array and puts all pieces into their start locations
     * Creates all piece objects
     */
    public void InitialiseBoard(){
        //Initialise White Special Pieces
        BoardArray[0][0] = new Square.OccupiedSquare(1,1,
                new Rook(new Coordinate(1,1), Colour.WHITE, PieceType.ROOK));
        BoardArray[0][1] = new Square.OccupiedSquare(2,1,
                new Knight(new Coordinate(2,1), Colour.WHITE, PieceType.KNIGHT));
        BoardArray[0][2] = new Square.OccupiedSquare(3,1,
                new Bishop(new Coordinate(3,1), Colour.WHITE, PieceType.BISHOP));
        BoardArray[0][3] = new Square.OccupiedSquare(4,1,
                new Queen(new Coordinate(4,1), Colour.WHITE, PieceType.QUEEN));
        BoardArray[0][4] = new Square.OccupiedSquare(5,1,
                new King(new Coordinate(5,1), Colour.WHITE, PieceType.KING));
        BoardArray[0][5] = new Square.OccupiedSquare(6,1,
                new Bishop(new Coordinate(6,1), Colour.WHITE, PieceType.BISHOP));
        BoardArray[0][6] = new Square.OccupiedSquare(7,1,
                new Knight(new Coordinate(7,1), Colour.WHITE, PieceType.KNIGHT));
        BoardArray[0][7] = new Square.OccupiedSquare(8,1,
                new Rook(new Coordinate(8,1), Colour.WHITE, PieceType.ROOK));
        
        //Initialise Black Special Pieces
        BoardArray[7][0] = new Square.OccupiedSquare(1,8,
                new Rook(new Coordinate(1,8), Colour.BLACK, PieceType.ROOK));
        BoardArray[7][1] = new Square.OccupiedSquare(2,8,
                new Knight(new Coordinate(2,8), Colour.BLACK, PieceType.KNIGHT));
        BoardArray[7][2] = new Square.OccupiedSquare(3,8,
                new Bishop(new Coordinate(3,8), Colour.BLACK, PieceType.BISHOP));
        BoardArray[7][3] = new Square.OccupiedSquare(4,8,
                new Queen(new Coordinate(4,8), Colour.BLACK, PieceType.QUEEN));
        BoardArray[7][4] = new Square.OccupiedSquare(5,8,
                new King(new Coordinate(5,8), Colour.BLACK, PieceType.KING));
        BoardArray[7][5] = new Square.OccupiedSquare(6,8,
                new Bishop(new Coordinate(6,8), Colour.BLACK, PieceType.BISHOP));
        BoardArray[7][6] = new Square.OccupiedSquare(7,8,
                new Knight(new Coordinate(7,8), Colour.BLACK, PieceType.KNIGHT));
        BoardArray[7][7] = new Square.OccupiedSquare(8,8,
                new Rook(new Coordinate(8,8), Colour.BLACK, PieceType.ROOK));
        
        //Initialise Pawn Pieces
        for (int i = 0; i < 8; i++) {
            
            //White Pawns
            BoardArray[1][i] = new Square.OccupiedSquare(i+1,2,
                    new Pawn(new Coordinate(i+1,2), Colour.WHITE, PieceType.PAWN));
            
            //Black Pawns
            BoardArray[6][i] = new Square.OccupiedSquare(i+1,7,
                    new Pawn(new Coordinate(i+1,7), Colour.BLACK, PieceType.PAWN));
        }
        
        //Initialise Empty Squares
        for (int i = 2; i < 6 ; i++) {       //Y-Coordinates
            for (int j = 0; j < 8; j++) {    //X-Coordinates
                BoardArray[i][j] = new Square.EmptySquare(j+1, i+1);
            }
        }
        
        //TODO: Test, remove when no longer needed
        BoardArray[4][5] = new Square.OccupiedSquare(6,5,
                new Queen(new Coordinate(6,5), Colour.WHITE, PieceType.QUEEN));
    }
    
    // TODO: 10/07/2021: complete and comment
    public void UpdateBoard(final Move move)
    {
        Piece MovedPiece = move.getMovedPiece();
        //Todo look for captures and remove captured pieces from board
        
    }
    
    /**
     * Returns the square which matches the given coordinates
     * @param coordinate A coordinate object
     * @return a Square object in the board array
     */
    public Square ReturnSquare(final Coordinate coordinate){
        int X_coordinate = coordinate.getFile();
        int Y_coordinate = coordinate.getRank();
        
        return BoardArray[Y_coordinate-1][X_coordinate-1];
    }
    
    //Getter variable which returns the board array of squares
    public Square[][] getBoardArray()
    {
        return BoardArray;
    }
}
