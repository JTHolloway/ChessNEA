package Game;

import Game.Board.Square;

public class Coordinate {

    private final int File;  //X Coordinate
    private final int Rank;  //Y Coordinate

    /**
     * Constructor for a coordinate object
     * @param File The numeric X component of the coordinate
     * @param Rank The Y component of the coordinate
     */
    public Coordinate(int File, int Rank) {
        this.File = File;
        this.Rank = Rank;
    }

    /**
     * Converts X coordinates to the letter equivalent for use in chess notation (eg. 3,5 = c5)
     * @return a String containing a single character of its equivalent numeric part.
     */
    public String FileToNotation(){
        char FileNotation = (char)(File + 96);
        return Character.toString(FileNotation);
    }
    
    /**
     * Converts full Cartigean coordinates into chess coordinates (eg. 3,5 = c5)
     * @return a String for the coordinate notation
     */
    public String CoordinateToNotation(){
        return FileToNotation() + Rank;
    }
    
    /**
     * Locates the square at any given coordinate
     * @param Board An instance of the current board (which contains an array of squares)
     * @return the square at given coordinate
     */
    public Square GetSquareAt (Square[][] Board){
        for (Square[] row : Board) {
            for (Square square : row) {
                if (CompareCoordinates(square)){
                    return square;
                }
            }
        }
        return null;
    }
    
    /**
     * Compares two coordinates to check they are equal
     * @param square The square being compared to the coordinate
     * @return a boolean indicating true if the coordinates the the same
     */
    public boolean CompareCoordinates(Square square){
        int XDisplacement = Math.abs(square.ReturnCoordinate().getFile() - File);
        int YDisplacement = Math.abs(square.ReturnCoordinate().getRank() - Rank);
        
        /*
           If displacement of square is 0 in comparison to coordinates then
           they are in the same location
         */
        // Coordinates are different
        return XDisplacement == 0 && YDisplacement == 0;    //Coordinates are the same
    }

    //Getter methods for file and rank coordinates
    public int getFile() {
        return File;
    }
    public int getRank() {
        return Rank;
    }
}
