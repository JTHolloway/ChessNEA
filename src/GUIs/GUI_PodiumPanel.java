package GUIs;

import LibaryFunctions.Quicksort;
import LibaryFunctions.Repository;
import User.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUI_PodiumPanel extends JPanel {

    private static List<User> Users;

    private static JTable ELOTable;
    private static JTable CountryTable;
    private static JLabel WorldWide;
    private static JLabel Country;
    private static JLabel Title;

    /**
     * Constructor for the Podium JPanel. Calls the initComponents() method
     */
    public GUI_PodiumPanel() {
        Users = Repository.getUsers();
        InitComponents();
    }

    /**
     * Updates the ELO JTable to add the Users to the table
     */
    public static void UpdateELOTable() {
        String[] ColumnNames = {"ELO", "Username", "Country"};
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        ELOTable.setModel(model);
        model.addRow(ColumnNames);

        Users = Repository.getUsers();
        Quicksort.quicksort(Users, true);
        Users.removeIf(user -> user.getUserID().equals("Guest"));

        List<User> topTenUsers;
        if (Users.size() > 50) {
            topTenUsers = Users.subList(0, 49);
        } else topTenUsers = Users;
        for (User user : topTenUsers) {
            String[] data = new String[3];
            data[0] = String.valueOf(user.getStatistics().getELO());
            data[1] = user.getUserName();
            data[2] = user.getCountry();
            model.addRow(data);
        }
    }

    /**
     * Updates the Country JTable to add the Users to the table
     */
    public static void UpdateCountryTable() {
        String[] ColumnNames = {"ELO", "Username"};
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        CountryTable.setModel(model);
        model.addRow(ColumnNames);

        Users = Repository.getUsers();
        Quicksort.quicksort(Users, true);
        Users.removeIf(user -> !user.getCountry().equals(Repository.getCurrentUser().getCountry()));
        Users.removeIf(user -> user.getUserID().equals("Guest"));

        List<User> topTenUsers;
        if (Users.size() > 50) {
            topTenUsers = Users.subList(0, 49);
        } else topTenUsers = Users;
        for (User user : topTenUsers) {
            String[] data = new String[2];
            data[0] = String.valueOf(user.getStatistics().getELO());
            data[1] = user.getUserName();
            model.addRow(data);
        }
    }

    /**
     * Initializes the components of the JPanel with each component's properties.
     */
    private void InitComponents() {
        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        ELOTable = new JTable();
        CountryTable = new JTable();
        WorldWide = new JLabel("World Wide", SwingConstants.CENTER);
        Country = new JLabel("Country", SwingConstants.CENTER);
        Title = new JLabel("\uD83C\uDFC6 Leaderboards", SwingConstants.LEADING);

        Title.setSize((int) (this.getWidth() * 0.45d), this.getHeight() / 22);
        Title.setFont(new Font("", Font.PLAIN, this.getHeight() / 22));
        Title.setForeground(new Color(197, 90, 17));
        Title.setLocation((int) (this.getWidth() * 0.01d), (int) (this.getHeight() * 0.01d));

        WorldWide.setSize((int) (this.getWidth() * 0.45d), this.getHeight() / 26);
        WorldWide.setFont(new Font("", Font.PLAIN, this.getHeight() / 26));
        WorldWide.setForeground(Color.WHITE);
        WorldWide.setLocation((int) (this.getWidth() * 0.1d), (int) (this.getHeight() * 0.05d));

        Country.setSize((int) (this.getWidth() * 0.3d), this.getHeight() / 26);
        Country.setFont(new Font("", Font.PLAIN, this.getHeight() / 26));
        Country.setForeground(Color.WHITE);
        Country.setLocation((int) (this.getWidth() - (this.getWidth() * 0.3d) - this.getWidth() * 0.1), (int) (this.getHeight() * 0.05d));

        ELOTable.setBorder(new LineBorder(Color.BLACK, 2));
        ELOTable.setBackground(Color.gray);
        ELOTable.setForeground(Color.WHITE);
        ELOTable.setBounds((int) (this.getWidth() * 0.1d), (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.45d),
                (int) (this.getHeight() * 0.8d));
        ELOTable.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.02d)));
        ELOTable.setRowHeight((int) (this.getHeight() * 0.02d));

        CountryTable.setBorder(new LineBorder(Color.BLACK, 2));
        CountryTable.setBackground(Color.gray);
        CountryTable.setForeground(Color.WHITE);
        CountryTable.setBounds((int) (this.getWidth() - (this.getWidth() * 0.3d) - this.getWidth() * 0.1),
                (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.3d), (int) (this.getHeight() * 0.8d));
        CountryTable.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.02d)));
        CountryTable.setRowHeight((int) (this.getHeight() * 0.02d));

        this.add(ELOTable);
        this.add(CountryTable);
        this.add(WorldWide);
        this.add(Country);
        this.add(Title);
    }
}
