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

    private JFrame MainWindow;
    private static JPanel gamePanel;

    private JLabel CloseWindowButton;
    private JLabel MinimiseWindowButton;
    private JLabel OpenPlayPanel;
    private JLabel OpenLearnPanel;
    private JLabel OpenAnalysisPanel;
    private JLabel MoreButton;
    private JLabel LogOutButton;


    public MainJFrame(GameType GameType, Colour PlayerColour, User user) {
        CurrentUser = user;
        new GamePanel(GameType, PlayerColour);

        Initialise();
    }

    private void Initialise() {
        MainWindow = this;

        //region Init Components
        JPanel sideBar = new JPanel();
        gamePanel = GamePanel.getGameTab();
        CloseWindowButton = new JLabel();
        MinimiseWindowButton = new JLabel();
        OpenPlayPanel = new JLabel("Play  ", SwingConstants.RIGHT);
        OpenLearnPanel = new JLabel("Learn  ", SwingConstants.RIGHT);
        OpenAnalysisPanel = new JLabel("Analysis  ", SwingConstants.RIGHT);
        MoreButton = new JLabel("More...  ", SwingConstants.RIGHT);
        LogOutButton = new JLabel("Log Out", SwingConstants.CENTER);
        //endregion

        //region Screen Dimensions
        int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize().height);
        int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize().width);
        //endregion

        //region Setup the Main Window
        MainWindow.setSize(screenWidth, screenHeight);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setLocationRelativeTo(null);
        MainWindow.setLayout(null);
        MainWindow.setUndecorated(true);
        //endregion

        //region Setup the Menu Bar Panel (SideBar)
        sideBar.setBounds(0, 0, (int) (screenWidth * 0.2d), screenHeight);
        sideBar.setLayout(null);
        sideBar.setBackground(Color.DARK_GRAY);
        //endregion

        //region Setup Close Button
        CloseWindowButton.setText("X");
        CloseWindowButton.setSize(screenHeight / 30, screenHeight / 26);
        CloseWindowButton.setFont(new Font("CloseButton", Font.PLAIN, screenHeight / 26));
        CloseWindowButton.setForeground(Color.WHITE);
        CloseWindowButton.setLocation((int) (sideBar.getWidth() * 0.1d), (int) (sideBar.getWidth() * 0.05d));
        CloseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        CloseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (CloseWindowButton.getForeground() == Color.WHITE) {
                    CloseWindowButton.setForeground(Color.RED);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (CloseWindowButton.getForeground() == Color.RED) {
                    CloseWindowButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Minimise Button
        MinimiseWindowButton.setText("-");
        MinimiseWindowButton.setSize(screenHeight / 30, screenHeight / 15);
        MinimiseWindowButton.setFont(new Font("MinimiseButton", Font.BOLD, screenHeight / 15));
        MinimiseWindowButton.setForeground(Color.WHITE);
        MinimiseWindowButton.setLocation((int) (sideBar.getWidth() * 0.25d), (int) (sideBar.getWidth() * 0.008d));
        MinimiseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        MinimiseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainWindow.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (MinimiseWindowButton.getForeground() == Color.WHITE) {
                    MinimiseWindowButton.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (MinimiseWindowButton.getForeground() == Color.gray) {
                    MinimiseWindowButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Play Button
        OpenPlayPanel.setSize((int) (screenWidth * 0.2d), screenHeight / 26);
        OpenPlayPanel.setFont(new Font("PlayButton", Font.PLAIN, screenHeight / 26));
        OpenPlayPanel.setForeground(Color.WHITE);
        OpenPlayPanel.setLocation(0, (int) (sideBar.getHeight() * 0.2d));
        OpenPlayPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        OpenPlayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gamePanel.isVisible()) {
                    gamePanel.setVisible(true);
                    //TODO set other panels visibility to false
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (OpenPlayPanel.getForeground() == Color.WHITE) {
                    OpenPlayPanel.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (OpenPlayPanel.getForeground() == Color.gray) {
                    OpenPlayPanel.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Learn Button
        OpenLearnPanel.setSize((int) (screenWidth * 0.2d), screenHeight / 26);
        OpenLearnPanel.setFont(new Font("LearnButton", Font.PLAIN, screenHeight / 26));
        OpenLearnPanel.setForeground(Color.WHITE);
        OpenLearnPanel.setLocation(0, (int) (sideBar.getHeight() * 0.28d));
        OpenLearnPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        OpenLearnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (OpenLearnPanel.getForeground() == Color.WHITE) {
                    OpenLearnPanel.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (OpenLearnPanel.getForeground() == Color.gray) {
                    OpenLearnPanel.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Analysis Button
        OpenAnalysisPanel.setSize((int) (screenWidth * 0.2d), screenHeight / 26);
        OpenAnalysisPanel.setFont(new Font("AnalysisButton", Font.PLAIN, screenHeight / 26));
        OpenAnalysisPanel.setForeground(Color.WHITE);
        OpenAnalysisPanel.setLocation(0, (int) (sideBar.getHeight() * 0.36d));
        OpenAnalysisPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        OpenAnalysisPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (OpenAnalysisPanel.getForeground() == Color.WHITE) {
                    OpenAnalysisPanel.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (OpenAnalysisPanel.getForeground() == Color.gray) {
                    OpenAnalysisPanel.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup More Button
        MoreButton.setSize((int) (screenWidth * 0.2d), screenHeight / 26);
        MoreButton.setFont(new Font("MoreButton", Font.PLAIN, screenHeight / 26));
        MoreButton.setForeground(Color.WHITE);
        MoreButton.setLocation(0, (int) (sideBar.getHeight() * 0.44d));
        MoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        MoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (MoreButton.getForeground() == Color.WHITE) {
                    MoreButton.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (MoreButton.getForeground() == Color.gray) {
                    MoreButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Logout Button
        LogOutButton.setSize((int) (screenWidth * 0.2d), screenHeight / 18);
        LogOutButton.setFont(new Font("LogoutButton", Font.PLAIN, screenHeight / 22));
        LogOutButton.setForeground(Color.WHITE);
        LogOutButton.setLocation(0, (int) (sideBar.getHeight() * 0.92d));
        LogOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        LogOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (LogOutButton.getForeground() == Color.WHITE) {
                    LogOutButton.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (LogOutButton.getForeground() == Color.gray) {
                    LogOutButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Add Components to Panels
        sideBar.add(CloseWindowButton);
        sideBar.add(MinimiseWindowButton);
        sideBar.add(OpenPlayPanel);
        sideBar.add(OpenLearnPanel);
        sideBar.add(OpenAnalysisPanel);
        sideBar.add(MoreButton);
        sideBar.add(LogOutButton);
        //endregion

        //region Add Panels to the JFrame
        this.getContentPane().add(sideBar);
        this.getContentPane().add(gamePanel);
        //endregion

        this.setVisible(true);
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }
}
