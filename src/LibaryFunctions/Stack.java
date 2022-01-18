package LibaryFunctions;

import GUIs.Tile;
import Game.Move.Move;

import java.util.ArrayList;
import java.util.List;

public class Stack {

    private List<Tile> stackList = new ArrayList<>();
    private int topIndex;

    public Stack(){
        topIndex = -1;
    }

    public void push (Tile tile){
        stackList.add(tile);
        topIndex++;
    }

    public Tile pop (){
       if (!isEmpty()){
           Tile poppedTile = stackList.get(topIndex);
           stackList.remove(topIndex);
           topIndex--;
           return poppedTile;

       } else return null; //Stack UnderFLow
    }

    public Tile peek (){
        if (!isEmpty()){
            return stackList.get(topIndex);

        } else return null; //Stack UnderFlow
    }

    public boolean isEmpty(){
        return (topIndex < 0);
    }
}
