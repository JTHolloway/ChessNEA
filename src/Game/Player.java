package Game;

import Game.Board.Board;
import User.User;

public abstract class Player
{
    //TODO this entire class
    protected final Colour playingColour;
    protected boolean isTurn;
    
    public Player(Colour playingColour)
    {
        this.playingColour = playingColour;
        
        if (playingColour == Colour.WHITE){
            this.isTurn = true;
        } else this.isTurn = false;
    }
    
    //Getters
    public Colour getPlayingColour()
    {
        return playingColour;
    }
    public boolean isTurn()
    {
        return isTurn;
    }
    
    //Setters
    public void setTurn(boolean turn)
    {
        isTurn = turn;
    }
    
    
    
    public static class Human extends Player
    {
        private final User user;
        
        public Human(Colour playingColour, User user)
        {
            super(playingColour);
            this.user = user;
        }
    }
    
    
    public static class Computer extends Player
    {
        public Computer(Colour playingColour)
        {
            super(playingColour);
        }
    }
    
}
