package Tests;

import Game.Board.Square;
import Game.Game;

public class Stalemate_Test {

    public static void main(String[] args) {
        Game game = new Game();
        for (Square[] row : game.getBoard().getBoardArray()){
            for (Square square : row){
                game.getBoard().getBoardArray()[square.ReturnCoordinate().getRank()-1][square.ReturnCoordinate().getFile()-1].;
            }
        }
    }
}
