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

    /**
     * Calls the minimax algorithm and makes the computer move.
     * Also increments the number of moves made and updates the board and the player turn.
     */
    @Override
    public void run() {
        Move computerMove = minimax.getComputerMove(Colour.GetOtherColour(GUI_GamePanel.getGame().getSelectedColour()));
        GUI_GamePanel.updateMoveBox(GUI_GamePanel.getGame().MoveToNotation(computerMove));
        GUI_GamePanel.getGame().setMovesMade(GUI_GamePanel.getGame().getMovesMade() + 1);

        Game.MakeMove(computerMove, GUI_GamePanel.getGame().getBoard());
        GUI_BoardPanel.UpdateBoard();

        GUI_GamePanel.getGame().UpdatePlayerToMove();
        GUI_GamePanel.updateTurn();
    }
}
