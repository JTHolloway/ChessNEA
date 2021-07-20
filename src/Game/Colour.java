package Game;

public enum Colour {
    
    //Enumeration Used to declare Piece colour which wont change
    WHITE,
    BLACK;
    
    public static Colour GetOtherColour(Colour c){
        if (c == WHITE){
            return BLACK;
        }else return WHITE;
    }

}
