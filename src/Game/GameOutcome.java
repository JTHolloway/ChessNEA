package Game;

public enum GameOutcome {

    WIN,
    LOSS,
    DRAW;

    public static double valueOf(GameOutcome outcome){
        if (outcome == WIN)
        {
            return 1;
        }
        else if (outcome == LOSS)
        {
            return 0;
        }
        else return 0.5;
    }
}
