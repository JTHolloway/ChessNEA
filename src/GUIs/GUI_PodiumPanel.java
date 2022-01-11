package GUIs;

import LibaryFunctions.Quicksort;
import LibaryFunctions.Repository;
import User.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI_PodiumPanel extends JPanel{

    private static List<User> Users;

    private static JTable ELOTable;
    private static JTable CountryTable;

    /**
     * Constructor for the Podium JPanel. Calls the initComponents() method
     */
    public GUI_PodiumPanel() {
        Users = Repository.getUsers();
        InitComponents();
    }

    /**
     * Updates the friends JTable to add their friends to the table
     */
    public static void UpdateELOTable() {
        String[] ColumnNames = {"Username", "ELO"};
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        ELOTable.setModel(model);
        model.addRow(ColumnNames);

        Quicksort.quicksort(Users, true);
        for (User user : Users){
            String[] data = new String[2];
            data[0] = String.valueOf(user.getStatistics().getELO());
            data[1] = user.getUserName();
            model.addRow(data);
        }
    }

    /**
     * Updates the friends JTable to add their friends to the table
     */
    public static void UpdateCountryTable() {
        String[] ColumnNames = {"Username", "ELO"};
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        CountryTable.setModel(model);
        model.addRow(ColumnNames);

        Quicksort.quicksort(Users, true);
        Users.removeIf(user -> ! user.getCountry().equals(Repository.getCurrentUser().getCountry()));
        for (User user : Users){
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

        //init components
        ELOTable = new JTable();
        CountryTable = new JTable();

        //Search Results
        ELOTable.setBorder(new LineBorder(Color.BLACK, 2));
        ELOTable.setBackground(Color.gray);
        ELOTable.setForeground(Color.BLACK);
        ELOTable.setBounds((int) (this.getWidth() * 0.1d), (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.3d),
                (int) (this.getHeight() * 0.8d));
        ELOTable.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.03d)));
        ELOTable.setRowHeight((int) (this.getHeight() * 0.03d));
        //TODO actionListener

        //Friend List
        CountryTable.setBorder(new LineBorder(Color.BLACK, 2));
        CountryTable.setBackground(Color.gray);
        CountryTable.setForeground(Color.BLACK);
        CountryTable.setBounds((int) (this.getWidth() - (this.getWidth() * 0.3d) - this.getWidth() * 0.1),
                (int) (this.getHeight() * 0.1d), (int) (this.getWidth() * 0.3d), (int) (this.getHeight() * 0.8d));
        CountryTable.setFont(new Font("", Font.PLAIN, (int) (this.getHeight() * 0.03d)));
        CountryTable.setRowHeight((int) (this.getHeight() * 0.03d));
        //TODO actionListener

        this.add(ELOTable);
        this.add(CountryTable);
    }
}
