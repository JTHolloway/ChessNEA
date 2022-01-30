package GUIs;

import LibaryFunctions.Repository;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

public class GUI_ProfilePanel extends JPanel{

    private static JLabel Title;
    private static JLabel label_userName;
    private static JLabel label_country;
    private static JLabel label_ELO;
    private static JLabel label_rank;
    private static JLabel label_wins;
    private static JLabel label_losses;
    private static JLabel label_draws;
    private static JLabel label_joinDate;
    private static JLabel label_lastDateOnline;
    private static JLabel label_winPercentage;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public GUI_ProfilePanel() {

        if (!Repository.getCurrentUser().getUserID().equals("Guest")){
            InitComponents();
            this.setVisible(true);
        }
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
    private void InitComponents() {
        Format dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        Title = new JLabel("\uD83D\uDC64 Profile", SwingConstants.LEADING);

        Title.setSize((int) (this.getWidth() * 0.45d), this.getHeight() / 22);
        Title.setFont(new Font("", Font.PLAIN, this.getHeight() / 22));
        Title.setForeground(Color.WHITE);
        Title.setLocation((int) (this.getWidth() * 0.01d), (int) (this.getHeight() * 0.01d));

        double winPercentage;
        try {
            winPercentage = (((double) Repository.getCurrentUser().getStatistics().getWins() / (double) Repository.getCurrentUser().getStatistics().getGames()) * 100);

        } catch (Exception exception){
            System.out.println(exception);
            winPercentage = 0.00;
        }

        //Set text
        label_userName = new JLabel(" " + Repository.getCurrentUser().getUserName(), SwingConstants.LEFT);
        label_country = new JLabel(" " + Repository.getCurrentUser().getCountry(), SwingConstants.LEFT);
        label_ELO = new JLabel(" ELO: " + Repository.getCurrentUser().getStatistics().getELO(), SwingConstants.LEFT);
        label_rank = new JLabel(" Rank: " + Repository.getCurrentUser().getStatistics().getRank(), SwingConstants.LEFT);
        label_wins = new JLabel(" Wins: " + Repository.getCurrentUser().getStatistics().getWins(), SwingConstants.LEFT);
        label_losses = new JLabel(" Losses: " + Repository.getCurrentUser().getStatistics().getLosses(), SwingConstants.LEFT);
        label_draws = new JLabel(" Draws: " + Repository.getCurrentUser().getStatistics().getDraws(), SwingConstants.LEFT);
        label_joinDate = new JLabel(" Joined: " + dateFormat.format(Repository.getCurrentUser().getStatistics().getJoinDate()), SwingConstants.LEFT);
        label_lastDateOnline = new JLabel(" Last Online: " + dateFormat.format(Repository.getCurrentUser().getStatistics().getLastOnline()), SwingConstants.LEFT);
        label_winPercentage = new JLabel(" " + df.format(winPercentage) + "% Win Percentage", SwingConstants.LEFT);

        JLabel[] Components = {label_userName, label_country, label_ELO, label_rank, label_wins, label_losses, label_draws,
                label_joinDate, label_lastDateOnline, label_winPercentage};

        //Set Properties
        for (JLabel label : Components){
            label.setBorder(new LineBorder(Color.black, 2, true));
            label.setFont(new Font("", Font.PLAIN, ScreenHeight / 25));
            label.setForeground(Color.white);
        }

        //Set Bounds
        label_userName.setBounds((int)(this.getWidth() * 0.291), (int)(this.getHeight() * 0.111),(int)(this.getWidth() * 0.358), (int)(this.getHeight() * 0.07));
        label_country.setBounds((int)(this.getWidth() * 0.291), (int)(this.getHeight() * 0.195),(int)(this.getWidth() * 0.255), (int)(this.getHeight() * 0.07));
        label_ELO.setBounds((int)(this.getWidth() * 0.675), (int)(this.getHeight() * 0.111),(int)(this.getWidth() * 0.175), (int)(this.getHeight() * 0.07));
        label_rank.setBounds((int)(this.getWidth() * 0.675), (int)(this.getHeight() * 0.195),(int)(this.getWidth() * 0.28), (int)(this.getHeight() * 0.07));
        label_wins.setBounds((int)(this.getWidth() * 0.05), (int)(this.getHeight() * 0.365),(int)(this.getWidth() * 0.18), (int)(this.getHeight() * 0.07));
        label_losses.setBounds((int)(this.getWidth() * 0.26), (int)(this.getHeight() * 0.365),(int)(this.getWidth() * 0.18), (int)(this.getHeight() * 0.07));
        label_draws.setBounds((int)(this.getWidth() * 0.47), (int)(this.getHeight() * 0.365),(int)(this.getWidth() * 0.18), (int)(this.getHeight() * 0.07));
        label_joinDate.setBounds((int)(this.getWidth() * 0.05), (int)(this.getHeight() * 0.455),(int)(this.getWidth() * 0.27), (int)(this.getHeight() * 0.07));
        label_lastDateOnline.setBounds((int)(this.getWidth() * 0.33), (int)(this.getHeight() * 0.455),(int)(this.getWidth() * 0.32), (int)(this.getHeight() * 0.07));
        label_winPercentage.setBounds((int)(this.getWidth() * 0.05), (int)(this.getHeight() * 0.545),(int)(this.getWidth() * 0.32), (int)(this.getHeight() * 0.07));

        JLabel profilePicture = new JLabel();
        profilePicture.setBounds((int)(this.getWidth() * 0.05), (int)(this.getHeight() * 0.111), (int)(this.getHeight() * 0.23), (int)(this.getHeight() * 0.23));
        profilePicture.setBorder(new LineBorder(Color.black, 2, true));

        //Add Labels
        for (JLabel label : Components){
            this.add(label);
        }
        this.add(Title);
        this.add(profilePicture);
    }

    /**
     * This method will update the stats on the profile panel if a new game is played.
     */
    public static void updateStats(){

        double winPercentage;
        try {
            winPercentage = (((double) Repository.getCurrentUser().getStatistics().getWins() / (double) Repository.getCurrentUser().getStatistics().getGames()) * 100);

        } catch (Exception exception){
            System.out.println(exception);
            winPercentage = 0.00;
        }

        label_ELO.setText(" ELO: " + Repository.getCurrentUser().getStatistics().getELO());
        label_rank.setText(" Rank: " + Repository.getCurrentUser().getStatistics().getRank());
        label_wins.setText(" Wins: " + Repository.getCurrentUser().getStatistics().getWins());
        label_losses.setText(" Losses: " + Repository.getCurrentUser().getStatistics().getLosses());
        label_draws.setText(" Draws: " + Repository.getCurrentUser().getStatistics().getDraws());
        label_winPercentage.setText(" " + df.format(winPercentage) + "% Win Percentage");
    }
}
