package Game;

import User.User;

public abstract class Player {
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

        public User getUser() {
            return user;
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
    }
}
