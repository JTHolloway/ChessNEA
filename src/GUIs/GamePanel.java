package GUIs;

import Game.Colour;
import Game.Game;
import Game.GameType;

import javax.swing.*;
import java.awt.*;

public class GamePanel {

    private static JPanel GameTab;
    private static Game game;
    private static GUI_BoardPanel boardPanel;

    public GamePanel(GameType GameType, Colour PlayerColour) {
        game = new Game(GameType, PlayerColour, MainJFrame.getCurrentUser());
        boardPanel = new GUI_BoardPanel();

        int ScreenHeight = (Toolkit.getDefaultToolkit().getScreenSize().height);
        int ScreenWidth = (Toolkit.getDefaultToolkit().getScreenSize().width);

        GameTab = new JPanel();
        GameTab.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        GameTab.setBackground(new Color(160, 160, 160));
        GameTab.setLayout(null);

        JPanel BoardDisplay = boardPanel.getBoardDisplay();
        BoardDisplay.setLocation((int) (BoardDisplay.getWidth() * 0.1), (ScreenHeight - BoardDisplay.getHeight()) / 2);
        GameTab.add(BoardDisplay);

        GameTab.setVisible(false);
    }

    public static Game getGame() {
        return game;
    }

    public static JPanel getGameTab() {
        return GameTab;
    }
}
