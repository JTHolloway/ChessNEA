package Game;

public enum GameOutcome {

    WIN,
    LOSS,
    DRAW;

    /**
     * @param outcome The outcome of the game
     * @return The value of this outcome to be used when calculating ELO score.
     */
    public static double valueOf(GameOutcome outcome) {
        if (outcome == WIN) {
            return 1;
        } else if (outcome == LOSS) {
            return 0;
        } else return 0.5;
    }
}
