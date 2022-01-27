package GUIs;

import LibaryFunctions.Repository;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

public class GUI_ProfilePanel extends JPanel{

    private JLabel label_userName;
    private JLabel label_country;
    private JLabel label_ELO;
    private JLabel label_rank;
    private JLabel label_wins;
    private JLabel label_losses;
    private JLabel label_draws;
    private JLabel label_joinDate;
    private JLabel label_lastDateOnline;
    private JLabel label_winPercentage;
    private JTextPane friendsList;

    public GUI_ProfilePanel() {

        if (Repository.getCurrentUser() != null){
            InitComponents();
            this.setVisible(true);
        }
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
    private void InitComponents() {
        Format dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat doubleFormat = new DecimalFormat("###.##");

        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        //Set text
        label_userName = new JLabel(Repository.getCurrentUser().getUserName(), SwingConstants.RIGHT);
        label_country = new JLabel(Repository.getCurrentUser().getCountry(), SwingConstants.RIGHT);
        label_ELO = new JLabel("ELO: " + Repository.getCurrentUser().getStatistics().getELO(), SwingConstants.RIGHT);
        label_rank = new JLabel("Rank: " + Repository.getCurrentUser().getStatistics().getRank(), SwingConstants.RIGHT);
        label_wins = new JLabel("Wins: " + Repository.getCurrentUser().getStatistics().getWins(), SwingConstants.RIGHT);
        label_losses = new JLabel("Losses: " + Repository.getCurrentUser().getStatistics().getLosses(), SwingConstants.RIGHT);
        label_draws = new JLabel("Draws: " + Repository.getCurrentUser().getStatistics().getDraws(), SwingConstants.RIGHT);
        label_joinDate = new JLabel("Joined: " + dateFormat.format(Repository.getCurrentUser().getStatistics().getJoinDate()), SwingConstants.RIGHT);
        label_lastDateOnline = new JLabel("Last Online: " + dateFormat.format(Repository.getCurrentUser().getStatistics().getLastOnline()), SwingConstants.RIGHT);
        label_winPercentage = new JLabel("Win %: " + doubleFormat.format((double)(Repository.getCurrentUser().getStatistics().getWins() / Repository.getCurrentUser().getStatistics().getGames()) * 100), SwingConstants.RIGHT);

        //Set Borders
        label_userName.setBorder(new LineBorder(Color.black, 2));
        label_country.setBorder(new LineBorder(Color.black, 2));
        label_ELO.setBorder(new LineBorder(Color.black, 2));
        label_rank.setBorder(new LineBorder(Color.black, 2));
        label_wins.setBorder(new LineBorder(Color.black, 2));
        label_losses.setBorder(new LineBorder(Color.black, 2));
        label_draws.setBorder(new LineBorder(Color.black, 2));
        label_joinDate.setBorder(new LineBorder(Color.black, 2));
        label_lastDateOnline.setBorder(new LineBorder(Color.black, 2));
        label_winPercentage.setBorder(new LineBorder(Color.black, 2));

        //Set Bounds




        this.setVisible(false);
    }
}
