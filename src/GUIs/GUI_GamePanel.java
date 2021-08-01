package GUIs;

import Game.Colour;
import Game.Game;
import Game.GameType;

import javax.swing.*;
import java.awt.*;

public class GUI_GamePanel extends JPanel {

    private static Game game;
    private static GUI_BoardPanel boardPanel;

    public GUI_GamePanel(GameType GameType, Colour PlayerColour) {
        game = new Game(GameType, PlayerColour, GUI_MainJFrame.getCurrentUser());
        InitComponents();
    }

    public static Game getGame() {
        return game;
    }

    private void InitComponents() {
        boardPanel = new GUI_BoardPanel();

        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        JPanel BoardDisplay = boardPanel;
        BoardDisplay.setLocation((int) (BoardDisplay.getWidth() * 0.1), (ScreenHeight - BoardDisplay.getHeight()) / 2);
        this.add(BoardDisplay);

        this.setVisible(false);
    }
}
