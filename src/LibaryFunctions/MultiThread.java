package LibaryFunctions;

import GUIs.GUI_BoardPanel;
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
        GUI_GamePanel.updateMoveBox(GUI_GamePanel.getGame().MoveToNotation(computerMove));
        GUI_GamePanel.getGame().setMovesMade(GUI_GamePanel.getGame().getMovesMade() + 1);

        Game.MakeMove(computerMove, GUI_GamePanel.getGame().getBoard());
        GUI_BoardPanel.UpdateBoard();

        //todo remove print
        System.out.println();
        //System.out.println(GUI_GamePanel.getGame().MoveToNotation(computerMove));
        GUI_GamePanel.getGame().getBoard().PrintBoard();

        GUI_GamePanel.getGame().UpdatePlayerToMove();
        GUI_GamePanel.updateTurn();
    }
}
