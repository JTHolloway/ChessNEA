package User;

import java.sql.Date;

public class UserStats {

    private String Rank;
    private int ELO;
    private int Games;
    private int Wins;
    private int Losses;
    private final int Draws;
    private final Date JoinDate;
    private Date LastOnline;

    /**
     * Constructor for UserStats object
     * Contains player details about the user
     * @param rank This parameter is the users rank as a string
     * @param ELO This parameter is the numeric ELO rank of the user
     * @param games This parameter is the number of games which a user has played in total
     * @param wins This parameter is the total number of games which a user has won
     * @param losses This parameter is the total number of games which a user has lost
     * @param draws This parameter is the total number of games which a user has drawn
     * @param joinDate This parameter is the Date the user created their account
     * @param lastOnline This parameter is the date that the user last logged on to their account
     */
    public UserStats(String rank, int ELO, int games, int wins, int losses, int draws, Date joinDate, Date lastOnline) {
        Rank = rank;
        this.ELO = ELO;
        Games = games;
        Wins = wins;
        Losses = losses;
        Draws = draws;
        JoinDate = joinDate;
        LastOnline = lastOnline;
    }

    /*Setter Methods for each Variable (Except join date)*/
    public void setRank(String rank) {
        Rank = rank;
    }
    public void setELO(int ELO) {
        this.ELO = ELO;
    }
    public void setGames(int games) {
        Games = games;
    }
    public void setWins(int wins) {
        Wins = wins;
    }
    public void setLosses(int losses) {
        Losses = losses;
    }
    public void setLastOnline(Date lastOnline) {
        LastOnline = lastOnline;
    }

    /*Getter Methods for each Variable*/
    public String getRank() {
        return Rank;
    }
    public int getELO() {
        return ELO;
    }
    public int getGames() {
        return Games;
    }
    public int getWins() {
        return Wins;
    }
    public int getLosses() {
        return Losses;
    }
    public Date getJoinDate() {
        return JoinDate;
    }
    public Date getLastOnline() {
        return LastOnline;
    }
}
