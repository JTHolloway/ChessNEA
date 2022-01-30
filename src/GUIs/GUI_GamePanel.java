package GUIs;

import Game.Colour;
import Game.Game;
import Game.GameType;
import LibaryFunctions.Repository;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GUI_GamePanel extends JPanel {

    private static Game game;
    private static JTextPane moveBox;
    private static JLabel playerTurn;
    private GUI_BoardPanel boardPanel;

    /**
     * Constructor for Game JPanel which contains the board panel
     *
     * @param GameType     GameType Object for the type of game, Eg - VersesComputer
     * @param PlayerColour The starting colour chosen by the player
     */
    public GUI_GamePanel(GameType GameType, Colour PlayerColour) {
        game = new Game(GameType, PlayerColour, Repository.getCurrentUser());
        InitComponents();
    }

    /**
     * @return the Game object
     */
    public static Game getGame() {
        return game;
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
    private void InitComponents() {
        boardPanel = new GUI_BoardPanel();

        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        moveBox = new JTextPane();
        moveBox.setBounds((int)(this.getWidth() * 0.71), (int)(this.getHeight() * 0.06),(int)(this.getWidth() * 0.25), (int)(this.getHeight() * 0.8));
        moveBox.setBorder(new LineBorder(Color.BLACK, 2, true));
        moveBox.setForeground(Color.BLACK);
        moveBox.setBackground(new Color(160,160,160));
        moveBox.setFont(new Font("", Font.PLAIN, ScreenHeight/45));

        playerTurn = new JLabel("White's Turn", SwingConstants.CENTER);
        playerTurn.setBounds((int)(this.getWidth() * 0.71), (int)(this.getHeight() * 0.88),(int)(this.getWidth() * 0.25), (int)(this.getHeight() * 0.06));
        playerTurn.setBorder(new LineBorder(Color.BLACK, 2, true));
        playerTurn.setForeground(new Color(247, 229, 195));
        playerTurn.setBackground(new Color(160,160,160));
        playerTurn.setFont(new Font("", Font.PLAIN, ScreenHeight/20));

        JPanel BoardDisplay = boardPanel;
        BoardDisplay.setLocation((int) (BoardDisplay.getWidth() * 0.1), (ScreenHeight - BoardDisplay.getHeight()) / 2);
        this.add(BoardDisplay);
        this.add(moveBox);
        this.add(playerTurn);

        this.setVisible(false);
    }

    public static void updateMoveBox(String moveNotation){
        moveBox.setText(moveBox.getText() +  (game.getMovesMade() + 1)+ "." + moveNotation + " ");
    }

    public static String getPGN(){
        return moveBox.getText();
    }

    public static void updateTurn(){
        Colour colourTurn = GUI_GamePanel.getGame().getPlayerToMove().getPlayingColour();
        if (colourTurn == Colour.WHITE){
            playerTurn.setForeground(new Color(247, 229, 195));
            playerTurn.setText("White's Turn");
        } else {
            playerTurn.setForeground(new Color(59, 40, 4));
            playerTurn.setText("Black's Turn");
        }
    }
}
