package Game;

public enum Colour {

    /**
     * Declaration of final colours
     */
    WHITE,
    BLACK;

    /**
     * Gets the opposite colour to what is passed in. Used for allocating player colours.
     *
     * @param colour the Initial colour
     * @return the other colour which was not passed into the method
     */
    public static Colour GetOtherColour(Colour colour) {
        if (colour == WHITE) {
            return BLACK;
        } else return WHITE;
    }

}
