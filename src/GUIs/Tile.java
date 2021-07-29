package GUIs;

import Game.Colour;
import Game.Coordinate;
import Game.Piece.Piece;

import javax.swing.*;

public abstract class Tile extends JPanel
{
    protected final Coordinate coordinate;
    protected final Colour colour;
    
    public Tile(final Coordinate coordinate, final Colour colour)
    {
        this.coordinate = coordinate;
        this.colour = colour;
    }
    
    public abstract boolean isOccupied();
    public abstract Piece returnPiece();
    
    public Coordinate getCoordinate()
    {
        return coordinate;
    }
    
    public static class OccupiedTile extends Tile{
        
        private final Piece piece;
    
        public OccupiedTile(final Coordinate coordinate, final Colour colour, final Piece piece)
        {
            super(coordinate, colour);
            this.piece = piece;
        }
    
        @Override
        public boolean isOccupied()
        {
            return true;
        }
    
        @Override
        public Piece returnPiece()
        {
            return piece;
        }
    }
    
    
    public static class EmptyTile extends Tile{
        
        public EmptyTile(final Coordinate coordinate, final Colour colour)
        {
            super(coordinate, colour);
        }
        
        @Override
        public boolean isOccupied()
        {
            return false;
        }
        
        @Override
        public Piece returnPiece()
        {
            return null;
        }
    }
}
