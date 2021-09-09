package LibaryFunctions;

import GUIs.GUI_BoardPanel;
import User.User;
import User.UserStats;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Repository {

    private static final String DatabaseLocation = System.getProperty("user.dir") + "\\ChessDatabase.accdb";
    private static Connection connection;
    private static User currentUser;

    /**
     * gets a connection to the database so that SQL queries can be communicated
     *
     * @return a connection to the database
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            return connection;
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
        return null;
    }

    /**
     * @return the user which is currently logged in.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user which is logged in
     *
     * @param currentUser The User to be allocated as the CurrentUser
     */
    public static void setCurrentUser(User currentUser) {
        Repository.currentUser = currentUser;
    }


    //TODO fix getCurrentUsersFriends() and comment
//    public static ArrayList<User> getCurrentUsersFriends() {
//        User friend = null;
//        ArrayList<User> friends = new ArrayList<User>();
//
//        try {
//
//            String sql = "SELECT * " +
//                    "FROM User, UserStats, Friendship, Country " +
//                    "WHERE User.UserID = Friendship.FriendRequestRecipient " +
//                    "AND UserStats.UserID = Friendship.FriendRequestRecipient" +
//                    "AND Friendship.FriendRequester = '" + currentUser.getUserID() +
//                    " AND Country.CountryID = User.Country " +
//                    "AND Rank.RankID = UserStats.RankID";
//            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);
//
//            while (rs.next()) {
//                UserStats userStats = new UserStats(rs.getString());
//
//                friend = new User(rs.getString("User.UserID"),
//                        rs.getString("User.Username"),
//                        rs.getString("User.Email"),
//                        rs.getString("User.FirstName"),
//                        rs.getString("User.LastName"),
//                        rs.getString("Country.CountryName"));
//                friends.add(friend);
//            }
//            rs.close();
//            connection.close();
//        } catch (Exception e) {
//            System.out.println("Error in the repository class: " + e);
//
//        }
//        return friends;
//    }

    /**
     * Adds a new user to the database after they have created an account
     *
     * @param Password     New users un-hashed password
     * @param CountryIndex The Country Index which uniquely identifies the database record containing the users country
     */
    public static void AddUser(String Password, int CountryIndex) {
        String sql;
        try {
            sql =
                    "INSERT INTO User(UserID, Username, Password, Email, FirstName, LastName, Country) " +
                            "VALUES ('" + currentUser.getUserID() +
                            "' , '" + currentUser.getUserName() +
                            "' , '" + Utility.hashPassword(Password) +
                            "' , '" + currentUser.getEmail().toLowerCase(Locale.ROOT) +
                            "' , '" + currentUser.getName() +
                            "' , '" + currentUser.getSurname() +
                            "' , " + CountryIndex +
                            ")";
            ExecuteSQL.executeUpdateQuery(getConnection(), sql);

            sql =
                    "INSERT INTO UserStats(UserID, JoinDate, LastPlayDate) " +
                            "VALUES ('" + currentUser.getUserID() +
                            "' , '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) +
                            "' , '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) +
                            "')";
            ExecuteSQL.executeUpdateQuery(getConnection(), sql);
            connection.close();

        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
    }

    /**
     * @return a list of countries from the database to display on create account screen
     */
    public static List<String> getCountriesFromDatabase() {
        List<String> Countries = new ArrayList<>();

        try {

            String sql = "SELECT Country.CountryName " +
                    "FROM Country";
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);

            while (rs.next()) {
                Countries.add(rs.getString("CountryName"));
            }
            rs.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
        return Countries;
    }

    //TODO FIX LOGIN: Currently freezes the program

    /**
     * Search for user in the database before logging them in
     *
     * @param UserID   The users unique UserID tag
     * @param Email    The users email
     * @param Password Un-hashed entered password
     * @return a boolean true/false depending on weather that user was found
     */
    public static boolean UserFound(String UserID, String Email, String Password) {
        try {

            String sql = "SELECT User.UserID, User.Email, User.Password " +
                    "FROM User " +
                    "WHERE User.UserID = '" + UserID + "' AND User.Email = '" + Email.toLowerCase(Locale.ROOT) + "' AND User.Password = '" + Utility.hashPassword(Password) + "'";
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);

            if (rs.next()) {
                return true;
            }

            rs.close();
            connection.close();

            return false;

        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
            return false;
        }
    }

    /**
     * Once user has been validated this method is called which creates a new user object
     * by fetching relevant data from the database
     *
     * @param UserID uniquely identifies the record which contains the user in the database
     */
    public static void Login(String UserID) {
        try {
            String sql = "SELECT User.*, Country.CountryName, ProfilePictures.Picture " +
                    "FROM User, Country, ProfilePictures " +
                    "WHERE User.UserID = '" + UserID + "' AND Country.CountryID = User.Country " +
                    "AND ProfilePictures.ID = User.ProfilePicture";
            System.out.println(sql);
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);

            System.out.println("it worked");

            if (rs.next()) {
                byte[] byteArray = rs.getBytes("Picture");
                ImageIcon image = new ImageIcon(byteArray);

                setCurrentUser(new User(
                        rs.getString("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("CountryName"),
                        getUserStats(UserID),
                        image
                ));
            }
            rs.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
    }

    /**
     * Fetches data from the UserStats table within the database
     *
     * @param UserID Foreign Key which uniquely identifies the record which contains the user in the database
     * @return a UserStats object containing information about the user
     */
    private static UserStats getUserStats(String UserID) {
        try {
            Date Today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            String sql = "SELECT UserStats.UserID, UserStats.ELO, Rank.Rank, UserStats.JoinDate, UserStats.GamesPlayed, " +
                    "UserStats.Wins, UserStats.Losses, UserStats.Draws, UserStats.LastPlayDate " +
                    "FROM UserStats, Rank " +
                    "WHERE UserStats.UserID = '" + UserID + "' AND Rank.RankID = UserStats.RankID";
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);

            System.out.println(sql);

            UserStats stats = null;
            if (rs.next()) {
                stats = (new UserStats(
                        rs.getString("Rank.Rank"),
                        rs.getInt("UserStats.ELO"),
                        rs.getInt("UserStats.GamesPlayed"),
                        rs.getInt("UserStats.Wins"),
                        rs.getInt("UserStats.Losses"),
                        rs.getInt("UserStats.Draws"),
                        rs.getDate("UserStats.JoinDate"),
                        Today
                ));
            }
            rs.close();
            connection.close();

            return stats;

        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
            return null;
        }
    }

    //TODO fix getting image from database

    /**
     * @return a List containing all UserIds assigned to each user
     */
    public static List<String> getUserIds() {
        List<String> UserId = new ArrayList<>();

        try {

            String sql = "SELECT User.UserID " +
                    "FROM User";
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), sql);

            while (rs.next()) {
                UserId.add(rs.getString("UserID"));
            }
            rs.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
        return UserId;
    }

    /**
     * This method fetches the png piece image from the database and converts it to a scaled ImageIcon
     *
     * @param SQL_Query The SQL to be executed which is unique to each piece
     * @param Colour    The colour of the piece to identify its name in the database, eg - white
     * @param Type      The type of piece to identify its name in thew database, eg - rook
     * @return an ImageIcon of the piece image
     */
    public static ImageIcon ReturnPieceImage(String SQL_Query, String Colour, String Type) {
        try {
            ResultSet rs = ExecuteSQL.executeQuery(getConnection(), SQL_Query);

            rs.next();

            Type = Type.substring(0, 1).toUpperCase(Locale.ROOT) + Type.substring(1).toLowerCase(Locale.ROOT);

            System.out.println(Colour.charAt(0) + Type + ".png");
            ImageIcon TempImage = new ImageIcon(rs.getClass().getResource(Colour.charAt(0) + Type + ".png"));

            Image image = TempImage.getImage();
            Image ScaledImage = image.getScaledInstance((GUI_BoardPanel.WIDTH) / 8, (GUI_BoardPanel.HEIGHT) / 8, Image.SCALE_SMOOTH);

            rs.close();
            connection.close();

            return new ImageIcon(ScaledImage);

        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }

        return null;
    }
}
