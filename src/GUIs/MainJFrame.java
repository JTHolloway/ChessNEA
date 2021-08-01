package GUIs;

import Game.Colour;
import Game.GameType;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainJFrame extends javax.swing.JFrame {

    private static User CurrentUser;

    private static int ScreenWidth;
    private static int ScreenHeight;

    private static JPanel gamePanel;
    private JPanel SideBar;
    private JLabel CloseWindowButton;
    private JLabel MinimiseWindowButton;
    private JLabel OpenPlayPanel;


    public MainJFrame(GameType GameType, Colour PlayerColour, User user) {
        CurrentUser = user;
        new GamePanel(GameType, PlayerColour);

        Initialise();
        GamePressed();
    }

    //TODO test, add action lister
    private static void GamePressed() {
        gamePanel.setVisible(true);
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }

    private void Initialise() {
        //Init Components
        gamePanel = GamePanel.getGameTab();
        SideBar = new JPanel();
        CloseWindowButton = new JLabel();
        MinimiseWindowButton = new JLabel();
        OpenPlayPanel = new JLabel("Play  ", SwingConstants.RIGHT);

        //Screen Dimensions
        ScreenHeight = (Toolkit.getDefaultToolkit().getScreenSize().height);
        ScreenWidth = (Toolkit.getDefaultToolkit().getScreenSize().width);

        //Setup the Main Window
        this.setSize(ScreenWidth, ScreenHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setUndecorated(true);

        //Setup the Menu Bar Panel (SideBar)
        SideBar.setBounds(0, 0, (int) (ScreenWidth * 0.2d), ScreenHeight);
        SideBar.setLayout(null);
        SideBar.setBackground(new Color(64, 64, 64));

        //Setup Close Button
        CloseWindowButton.setText("X");
        CloseWindowButton.setSize(ScreenHeight / 30, ScreenHeight / 26);
        CloseWindowButton.setFont(new Font("CloseButton", Font.PLAIN, ScreenHeight / 26));
        CloseWindowButton.setForeground(Color.WHITE);
        CloseWindowButton.setLocation((int) (SideBar.getWidth() * 0.05d), (int) (SideBar.getWidth() * 0.05d));
        CloseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        CloseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //Setup Minimise Button
        MinimiseWindowButton.setText("-");
        MinimiseWindowButton.setSize(ScreenHeight / 30, ScreenHeight / 15);
        MinimiseWindowButton.setFont(new Font("MinimiseButton", Font.BOLD, ScreenHeight / 15));
        MinimiseWindowButton.setForeground(Color.WHITE);
        MinimiseWindowButton.setLocation((int) (SideBar.getWidth() * 0.2d), (int) (SideBar.getWidth() * 0.008d));
        MinimiseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        MinimiseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //Setup Play Button
        OpenPlayPanel.setSize((int) (ScreenWidth * 0.2d), ScreenHeight / 26);
        OpenPlayPanel.setFont(new Font("CloseButton", Font.PLAIN, ScreenHeight / 26));
        OpenPlayPanel.setForeground(Color.WHITE);
        OpenPlayPanel.setLocation(0, (int) (SideBar.getHeight() * 0.15d));
        OpenPlayPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        OpenPlayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //Add Components to Panels
        SideBar.add(CloseWindowButton);
        SideBar.add(MinimiseWindowButton);
        SideBar.add(OpenPlayPanel);

        //Add Panels to the JFrame
        this.getContentPane().add(SideBar);
        this.getContentPane().add(gamePanel);


        this.setVisible(true);
    }
}
