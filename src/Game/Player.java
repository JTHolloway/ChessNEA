package Game;

import User.User;

public abstract class Player {
    //TODO this entire class
    protected final Colour playingColour;
    protected boolean isTurn;

    /**
     * Constructor for a new player
     *
     * @param playingColour The starting colour of the player
     */
    public Player(Colour playingColour) {
        this.playingColour = playingColour;

        this.isTurn = playingColour == Colour.WHITE;
    }

    /**
     * @return the colour of the player
     */
    public Colour getPlayingColour() {
        return playingColour;
    }

    /**
     * @return boolean whether it is this players turn
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * @param turn sets turn to this player
     */
    public void setTurn(boolean turn) {
        isTurn = turn;
    }


    /**
     * Class for a Human Player
     * Extends Player class
     */
    public static class Human extends Player {
        private final User user;

        /**
         * Constructor for a human player
         *
         * @param playingColour The colour of the human player
         * @param user          The human user
         */
        public Human(Colour playingColour, User user) {
            super(playingColour);
            this.user = user;
        }
    }

    /**
     * Class for a Computer Player
     * Extends Player class
     */
    public static class Computer extends Player {
        /**
         * Constructor for a computer player
         *
         * @param playingColour The colour of the computer player
         */
        public Computer(Colour playingColour) {
            super(playingColour);
        }

        //todo call minimax algorithm calculate move
    }
}
