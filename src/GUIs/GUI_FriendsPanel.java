package GUIs;

import LibaryFunctions.Repository;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI_FriendsPanel extends JPanel {

    private static List<String> Usernames;

    private static JTable Results;
    private static JTable FriendsList;

    /**
     * Constructor for the Friends JPanel. Calls the initComponents() method
     */
    public GUI_FriendsPanel() {
        if (Repository.getCurrentUser() != null){
            Usernames = Repository.getCurrentUsersFriends();
            InitComponents();
        }
    }

    /**
     * Updates the friends JTable to add their friends to the table
     */
    public static void UpdateFriendsTable() {
        String[] ColumnNames = {"UserID", "User Name"};
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        FriendsList.setModel(model);
        model.addRow(ColumnNames);

        for (String User : Usernames){
            String[] data = (User.split(","));
            model.addRow(data);
        }
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
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
        searchBar.setBounds((int) (this.getWidth() * 0.1d), (int) ((this.getHeight() * 0.1d) - (int) (this.getHeight() * 0.08d)), (int) (this.getWidth() * 0.3d), (int) (this.getHeight() * 0.05d));
        searchBar.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.03d)));

        //Search Button
        confirmSearchButton.setBackground(Color.WHITE);
        confirmSearchButton.setForeground(Color.BLACK);
        confirmSearchButton.setText("Search");
        confirmSearchButton.setBounds((int) (this.getWidth() - (this.getWidth() * 0.6d)), (int) ((this.getHeight() * 0.1d) - (int) (this.getHeight() * 0.08d)),
                (int) (this.getWidth() * 0.1d), (int) (this.getHeight() * 0.05d));

        confirmSearchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO Find and Display Users 'LIKE' name in search bar from database. Else show 'no users found' dialog box
            }
        });

        //Search Results
        Results.getTableHeader().setOpaque(false);
        Results.getTableHeader().setBackground(Color.orange);
        Results.setBorder(new LineBorder(Color.BLACK, 2));
        Results.setBackground(Color.gray);
        Results.setForeground(Color.BLACK);
        Results.setBounds((int) (this.getWidth() * 0.1d), (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.3d),
                (int) (this.getHeight() * 0.8d));
        Results.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.03d)));
        //TODO actionListener

        //Friend List
        FriendsList.getTableHeader().setOpaque(false);
        FriendsList.getTableHeader().setBackground(Color.orange);
        FriendsList.setBorder(new LineBorder(Color.BLACK, 2));
        FriendsList.setBackground(Color.gray);
        FriendsList.setForeground(Color.BLACK);
        FriendsList.setBounds((int) (this.getWidth() - (this.getWidth() * 0.3d) - this.getWidth() * 0.1),
                (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.3d), (int) (this.getHeight() * 0.8d));
        FriendsList.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.03d)));
        FriendsList.setRowHeight((int) (this.getHeight() * 0.03d));
        //TODO actionListener

        this.add(searchBar);
        this.add(confirmSearchButton);
        this.add(Results);
        this.add(FriendsList);
    }
}
