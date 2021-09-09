package Game;

public enum Colour {

    /**
     * Declaration of final colours
     */
    WHITE,
    BLACK;

    /**
     * Gets the other colour that the one passed in. Used for allocating player colours.
     *
     * @param c the Initial colour
     * @return the other colour which was not passed into the method
     */
    public static Colour GetOtherColour(Colour c) {
        if (c == WHITE) {
            return BLACK;
        } else return WHITE;
    }

}
