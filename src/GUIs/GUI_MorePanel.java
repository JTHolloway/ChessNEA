package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI_MorePanel extends JPanel {

    private JLabel FriendsPanelButton;
    private JLabel ProfilePanelButton;
    private JLabel LeaderBoardsPanelButton;
    private JLabel SettingsPanelButton;

    /**
     * Constructor for the more JPanel
     */
    public GUI_MorePanel() {
        InitComponents();
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
    private void InitComponents() {
        JPanel MorePanel = this;

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (screenWidth * 0.2d), (int) (screenHeight * 0.18d), (int) (screenWidth * 0.15d), (int) (screenHeight * 0.38d));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        this.setVisible(false);

        //region Init Components
        FriendsPanelButton = new JLabel("\uD83D\uDC65 Friends  ", SwingConstants.RIGHT);
        ProfilePanelButton = new JLabel("\uD83D\uDC64 Profile  ", SwingConstants.RIGHT);
        LeaderBoardsPanelButton = new JLabel("\uD83C\uDFC6 Podium  ", SwingConstants.RIGHT);
        SettingsPanelButton = new JLabel("⚙ Settings  ", SwingConstants.RIGHT);
        JSeparator separatorLine = new JSeparator();
        //endregion

        //region Setup Friends Panel Button
        FriendsPanelButton.setSize((int) (screenWidth * 0.15d), screenHeight / 24);
        FriendsPanelButton.setFont(new Font("Friends", Font.PLAIN, screenHeight / 26));
        FriendsPanelButton.setForeground(Color.WHITE);
        FriendsPanelButton.setLocation(0, (int) (this.getHeight() * 0.06d));
        FriendsPanelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        FriendsPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_MainJFrame.FriendsPanelClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (FriendsPanelButton.getForeground() == Color.WHITE) {
                    FriendsPanelButton.setForeground(Color.gray);
                }
                MorePanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (FriendsPanelButton.getForeground() == Color.gray) {
                    FriendsPanelButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Profile Panel Button
        ProfilePanelButton.setSize((int) (screenWidth * 0.15d), screenHeight / 24);
        ProfilePanelButton.setFont(new Font("Profile", Font.PLAIN, screenHeight / 26));
        ProfilePanelButton.setForeground(Color.WHITE);
        ProfilePanelButton.setLocation(0, (int) (this.getHeight() * 0.27d));
        ProfilePanelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ProfilePanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO profile panel
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (ProfilePanelButton.getForeground() == Color.WHITE) {
                    ProfilePanelButton.setForeground(Color.gray);
                }
                MorePanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (ProfilePanelButton.getForeground() == Color.gray) {
                    ProfilePanelButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup LeaderBoards Panel Button
        LeaderBoardsPanelButton.setSize((int) (screenWidth * 0.15d), screenHeight / 24);
        LeaderBoardsPanelButton.setFont(new Font("Podium", Font.PLAIN, screenHeight / 26));
        LeaderBoardsPanelButton.setForeground(Color.WHITE);
        LeaderBoardsPanelButton.setLocation(0, (int) (this.getHeight() * 0.47d));
        LeaderBoardsPanelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        LeaderBoardsPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO leaderboard panel
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (LeaderBoardsPanelButton.getForeground() == Color.WHITE) {
                    LeaderBoardsPanelButton.setForeground(Color.gray);
                }
                MorePanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (LeaderBoardsPanelButton.getForeground() == Color.gray) {
                    LeaderBoardsPanelButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Settings Panel Button
        SettingsPanelButton.setSize((int) (screenWidth * 0.15d), screenHeight / 22);
        SettingsPanelButton.setFont(new Font("Settings", Font.PLAIN, screenHeight / 26));
        SettingsPanelButton.setForeground(Color.WHITE);
        SettingsPanelButton.setLocation(0, (int) (this.getHeight() * 0.67d));
        SettingsPanelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        SettingsPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO settings panel
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (SettingsPanelButton.getForeground() == Color.WHITE) {
                    SettingsPanelButton.setForeground(Color.gray);
                }
                MorePanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (SettingsPanelButton.getForeground() == Color.gray) {
                    SettingsPanelButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Separator
        separatorLine.setBounds(0, (int) (this.getHeight() * 0.1), 5, (int) (this.getHeight() * 0.8));
        separatorLine.setOrientation(SwingConstants.VERTICAL);
        //endregion

        //Add Components to Panel
        this.add(FriendsPanelButton);
        this.add(ProfilePanelButton);
        this.add(LeaderBoardsPanelButton);
        this.add(SettingsPanelButton);
        this.add(separatorLine);
    }
}
