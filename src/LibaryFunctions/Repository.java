package LibaryFunctions;

import User.User;

import java.sql.Connection;
import java.sql.DriverManager;

public class Repository {

    private static final String DatabaseLocation = System.getProperty("user.dir") + "\\ChessDatabase.accdb";
    private static Connection connection;
    private static User currentUser;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            return connection;
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

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


}
