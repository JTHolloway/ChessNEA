package LibaryFunctions;

import GUIs.GUI_GamePanel;
import Game.Colour;
import Game.Game;
import Game.Minimax;
import Game.Move.Move;

public class MultiThread implements Runnable {

    private final Minimax minimax;

    public MultiThread(Minimax minimax) {
        this.minimax = minimax;
    }

    @Override
    public void run() {
        Move computerMove = minimax.getComputerMove(Colour.GetOtherColour(GUI_GamePanel.getGame().getSelectedColour()));
        Game.MakeMove(computerMove, GUI_GamePanel.getGame().getBoard());

        System.out.println();
        GUI_GamePanel.getGame().getBoard().PrintBoard();

        GUI_GamePanel.getGame().UpdatePlayerToMove();
    }
}
