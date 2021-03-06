package User;

import java.sql.Date;

public class UserStats {

    private final Date JoinDate;
    private final Date LastOnline;
    private String Rank;
    private int ELO;
    private int Games;
    private int Wins;
    private int Losses;
    private int Draws;

    /**
     * Constructor for UserStats object
     * Contains player details about the user
     *
     * @param rank       This parameter is the users rank as a string
     * @param ELO        This parameter is the numeric ELO rank of the user
     * @param wins       This parameter is the total number of games which a user has won
     * @param losses     This parameter is the total number of games which a user has lost
     * @param draws      This parameter is the total number of games which a user has drawn
     * @param joinDate   This parameter is the Date the user created their account
     * @param lastOnline This parameter is the date that the user last logged on to their account
     */
    public UserStats(String rank, int ELO, int wins, int losses, int draws, Date joinDate, Date lastOnline) {
        Rank = rank;
        this.ELO = ELO;
        Games = wins + draws + losses;
        Wins = wins;
        Losses = losses;
        Draws = draws;
        JoinDate = joinDate;
        LastOnline = lastOnline;
    }

    /*Getter Methods for each Variable*/
    public String getRank() {
        return Rank;
    }

    /*Setter Methods for each Variable (Except dates)*/
    public void setRank(String rank) {
        Rank = rank;
    }

    public int getELO() {
        return ELO;
    }

    public void setELO(int ELO) {
        this.ELO = ELO;
    }

    public int getGames() {
        return Games;
    }

    public void setGames(int games) {
        Games = games;
    }

    public int getWins() {
        return Wins;
    }

    public void setWins(int wins) {
        Wins = wins;
    }

    public int getLosses() {
        return Losses;
    }

    public void setLosses(int losses) {
        Losses = losses;
    }

    public Date getJoinDate() {
        return JoinDate;
    }

    public Date getLastOnline() {
        return LastOnline;
    }

    public int getDraws() {
        return Draws;
    }

    public void setDraws(int draws) {
        Draws = draws;
    }
}
