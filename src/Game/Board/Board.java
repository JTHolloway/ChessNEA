package Game.Board;

import Game.CastlingAvailability;
import Game.Colour;
import Game.Coordinate;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Square[][] BoardArray = new Square[8][8];
    private Pawn enPassantPawn;
    private final Piece[] Kings = new Piece[2];
    private List<Piece> WhitePieces = new ArrayList<>();
    private List<Piece> BlackPieces = new ArrayList<>();

    /**
     * Board constructor
     * creates a new board instance which calls the InitialiseBoard() method
     * to set up the board
     */
    public Board() {
        this.InitialiseBoard();
    }

    /**
     * Sets up a new board array and puts all pieces into their start locations
     * Creates all piece objects
     * BoardArray[Y-Coord][X-Coord]
     */
    public void InitialiseBoard() {
        //Initialise White Special Pieces
        BoardArray[0][0] = new Square.OccupiedSquare(1, 1,
                new Rook(new Coordinate(1, 1), Colour.WHITE, PieceType.ROOK, CastlingAvailability.QUEEN_SIDE));
        BoardArray[0][1] = new Square.OccupiedSquare(2, 1,
                new Knight(new Coordinate(2, 1), Colour.WHITE, PieceType.KNIGHT));
        BoardArray[0][2] = new Square.OccupiedSquare(3, 1,
                new Bishop(new Coordinate(3, 1), Colour.WHITE, PieceType.BISHOP));
        BoardArray[0][3] = new Square.OccupiedSquare(4, 1,
                new Queen(new Coordinate(4, 1), Colour.WHITE, PieceType.QUEEN));
        BoardArray[0][4] = new Square.OccupiedSquare(5, 1,
                new King(new Coordinate(5, 1), Colour.WHITE, PieceType.KING));
        BoardArray[0][5] = new Square.OccupiedSquare(6, 1,
                new Bishop(new Coordinate(6, 1), Colour.WHITE, PieceType.BISHOP));
        BoardArray[0][6] = new Square.OccupiedSquare(7, 1,
                new Knight(new Coordinate(7, 1), Colour.WHITE, PieceType.KNIGHT));
        BoardArray[0][7] = new Square.OccupiedSquare(8, 1,
                new Rook(new Coordinate(8, 1), Colour.WHITE, PieceType.ROOK, CastlingAvailability.KING_SIDE));
        Kings[0] = BoardArray[0][4].ReturnPiece();
        for (int i = 0; i < 8; i++) {
            WhitePieces.add(BoardArray[0][i].ReturnPiece());
        }

        //Initialise Black Special Pieces
        BoardArray[7][0] = new Square.OccupiedSquare(1, 8,
                new Rook(new Coordinate(1, 8), Colour.BLACK, PieceType.ROOK, CastlingAvailability.QUEEN_SIDE));
        BoardArray[7][1] = new Square.OccupiedSquare(2, 8,
                new Knight(new Coordinate(2, 8), Colour.BLACK, PieceType.KNIGHT));
        BoardArray[7][2] = new Square.OccupiedSquare(3, 8,
                new Bishop(new Coordinate(3, 8), Colour.BLACK, PieceType.BISHOP));
        BoardArray[7][3] = new Square.OccupiedSquare(4, 8,
                new Queen(new Coordinate(4, 8), Colour.BLACK, PieceType.QUEEN));
        BoardArray[7][4] = new Square.OccupiedSquare(5, 8,
                new King(new Coordinate(5, 8), Colour.BLACK, PieceType.KING));
        BoardArray[7][5] = new Square.OccupiedSquare(6, 8,
                new Bishop(new Coordinate(6, 8), Colour.BLACK, PieceType.BISHOP));
        BoardArray[7][6] = new Square.OccupiedSquare(7, 8,
                new Knight(new Coordinate(7, 8), Colour.BLACK, PieceType.KNIGHT));
        BoardArray[7][7] = new Square.OccupiedSquare(8, 8,
                new Rook(new Coordinate(8, 8), Colour.BLACK, PieceType.ROOK, CastlingAvailability.KING_SIDE));
        Kings[1] = BoardArray[7][4].ReturnPiece();
        for (int i = 0; i < 8; i++) {
            BlackPieces.add(BoardArray[7][i].ReturnPiece());
        }

        //Initialise Pawn Pieces
        for (int i = 0; i < 8; i++) {

            //White Pawns
            BoardArray[1][i] = new Square.OccupiedSquare(i + 1, 2,
                    new Pawn(new Coordinate(i + 1, 2), Colour.WHITE, PieceType.PAWN));
            WhitePieces.add(BoardArray[1][i].ReturnPiece());

            //Black Pawns
            BoardArray[6][i] = new Square.OccupiedSquare(i + 1, 7,
                    new Pawn(new Coordinate(i + 1, 7), Colour.BLACK, PieceType.PAWN));
            BlackPieces.add(BoardArray[6][i].ReturnPiece());
        }

        //Initialise Empty Squares
        for (int i = 2; i < 6; i++) {        //Y-Coordinates
            for (int j = 0; j < 8; j++) {    //X-Coordinates
                BoardArray[i][j] = new Square.EmptySquare(j + 1, i + 1);
            }
        }
    }

    /**
     * @return the destination square of the capturing piece when capturing an enPassant pawn
     */
    public Square getEnPassantDestination() {

        try {
            int File = enPassantPawn.getPieceCoordinate().getFile();
            Coordinate Destination;
            if (enPassantPawn.getColour() == Colour.WHITE) {
                Destination = new Coordinate(File, 3);
            } else {
                Destination = new Coordinate(File, 6);
            }
            return Destination.GetSquareAt(BoardArray);

        } catch (NullPointerException exception) {
            System.out.println("EnPassant Pawn does not exist");
            return null;
        }
    }

    /**
     * @return an Array of square objects (the board)
     */
    public Square[][] getBoardArray() {
        return BoardArray;
    }

    /**
     * This method is a TEST which prints the board
     * TODO remove test method
     */
    public void PrintBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                if (getBoardArray()[i][j].SquareOccupied()) {
                    String s;
                    if (getBoardArray()[i][j].ReturnPiece().getType() != PieceType.PAWN) {
                        s = getBoardArray()[i][j].ReturnPiece().PieceTypeToNotation();
                    } else {
                        s = "P";
                    }
                    if (getBoardArray()[i][j].ReturnPiece().getColour() == Colour.BLACK) {
                        s = s.toLowerCase();
                    }
                    System.out.print("[" + s + "]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    /**
     * @return a pawn object of the current enPassant Pawn
     */
    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }

    /**
     * Updates the current enPassant Pawn. Value can be null when no enPassant pawns are present
     *
     * @param pawn The pawn to be set as the enPassant pawn
     */
    public void setEnPassantPawn(Pawn pawn) {
        this.enPassantPawn = pawn;
    }

    public Piece[] getKings() {
        return Kings;
    }

    public List<Piece> getWhitePieces() {
        return WhitePieces;
    }

    public List<Piece> getBlackPieces() {
        return BlackPieces;
    }
}
