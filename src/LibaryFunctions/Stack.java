package LibaryFunctions;

import Game.Move.Move;

import java.util.ArrayList;
import java.util.List;

public class Stack {

    private List<Move> stackList = new ArrayList<>();
    private int topIndex;

    public Stack(){
        topIndex = -1;
    }

    public void push (Move move){
        stackList.add(move);
        topIndex++;
    }

    public Move pop (){
       if (!isEmpty()){
           Move poppedMove = stackList.get(topIndex);
           stackList.remove(topIndex);
           return poppedMove;

       } else return null; //Stack UnderFLow
    }

    public Move peek (){
        if (!isEmpty()){
            return stackList.get(topIndex);

        } else return null; //Stack UnderFlow
    }

    public boolean isEmpty(){
        return (topIndex < 0);
    }
}
