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
        if (computerMove.wasCapture()) {
                System.out.println(computerMove.getMovedPiece().getColour() + " " + computerMove.getMovedPiece().getType() + ", " + computerMove.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        computerMove.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", " + computerMove.getCapturedPiece().getType() + ", " + computerMove.getCapturedPiece().getColour() + ", " +
                        computerMove.getCapturedPiece().getPieceCoordinate().CoordinateToNotation());
            } else {
                System.out.println(computerMove.getMovedPiece().getColour() + " " + computerMove.getMovedPiece().getType() + ", " + computerMove.getStartPosition().ReturnCoordinate().CoordinateToNotation() + ", " +
                        computerMove.getEndPosition().ReturnCoordinate().CoordinateToNotation() + ", NULL");
            }
        
        Game.MakeMove(computerMove, GUI_GamePanel.getGame().getBoard());
        GUI_BoardPanel.UpdateBoard();

        //todo remove print
        System.out.println();
        //System.out.println(GUI_GamePanel.getGame().MoveToNotation(computerMove));
        GUI_GamePanel.getGame().getBoard().PrintBoard();

        GUI_GamePanel.getGame().UpdatePlayerToMove();
    }
}
