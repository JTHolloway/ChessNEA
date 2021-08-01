package GUIs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class GUI_FriendsPanel extends JPanel {

    private static List<String> Usernames;

    private JTable Results;
    private JTable FriendsList;

    public GUI_FriendsPanel() {
        //TODO fetch usernames from user table WHERE they are friends in the friendship table. Add them to list
        InitComponents();
    }

    private void InitComponents() {
        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        //init components
        JTextField searchBar = new JTextField();
        JButton confirmSearchButton = new JButton();
        Results = new JTable();
        FriendsList = new JTable();

        //Search Bar
        searchBar.setBackground(Color.WHITE);
        searchBar.setForeground(Color.BLACK);
        searchBar.setToolTipText("Search Users");
        searchBar.setBounds((int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.05d));
        //TODO actionListener

        //Search Button
        confirmSearchButton.setBackground(Color.WHITE);
        confirmSearchButton.setForeground(Color.BLACK);
        confirmSearchButton.setText("Search");
        confirmSearchButton.setBounds((int) (this.getWidth() * 0.5d), (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.05d));
        //TODO actionListener

        //Search Results
        Results.setBorder(new LineBorder(Color.BLACK, 5));
        Results.setBackground(Color.gray);
        Results.setForeground(Color.BLACK);
        Results.setBounds((int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.2d), (int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.6d));
        //TODO actionListener

        //Friend List
        FriendsList.setBorder(new LineBorder(Color.BLACK, 5));
        FriendsList.setBackground(Color.gray);
        FriendsList.setForeground(Color.BLACK);
        FriendsList.setBounds((int) (this.getWidth() * 0.5d), (int) (this.getHeight() * 0.2d), (int) (this.getWidth() * 0.2d), (int) (this.getHeight() * 0.6d));
        //TODO actionListener

        this.add(searchBar);
        this.add(confirmSearchButton);
        this.add(Results);
        this.add(FriendsList);
    }
}
