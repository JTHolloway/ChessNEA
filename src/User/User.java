package User;

import javax.swing.*;

public class User {

    private final String UserID;
    private final UserStats Statistics;
    private final String UserName;
    private String Email;
    private final String Name;
    private final String Surname;
    private String Country;
    private ImageIcon ProfilePicture;

    /**
     * Constructor for a new User with no profile picture
     *
     * @param userID     A Unique User Defined 4-digit Identification key
     * @param userName   A non-unique User Defined User name
     * @param email      The Users unique email address
     * @param name       The users first name
     * @param surname    The users last name
     * @param country    The users country/location
     * @param statistics An object of UserStats class, Containing further player details (such as join date)
     */
    public User(String userID, String userName, String email, String name, String surname, String country, UserStats statistics) {
        UserID = userID;
        UserName = userName;
        Email = email;
        Name = name;
        Surname = surname;
        Country = country;
        Statistics = statistics;
    }

    /**
     * Constructor for an existing User which has a profile picture
     *
     * @param userID     A Unique User Defined 4-digit Identification key
     * @param userName   A non-unique User Defined User name
     * @param email      The Users unique email address
     * @param name       The users first name
     * @param surname    The users last name
     * @param country    The users country/location
     * @param statistics An object of UserStats class, Containing further player details (such as join date)
     */
    public User(String userID, String userName, String email, String name, String surname, String country, UserStats statistics, ImageIcon ProfilePic) {
        UserID = userID;
        UserName = userName;
        Email = email;
        Name = name;
        Surname = surname;
        Country = country;
        Statistics = statistics;
        ProfilePicture = ProfilePic;
    }

    /*Getter Methods for each Variable*/
    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }

    /*Setter Methods for Variables*/
    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public UserStats getStatistics() {
        return Statistics;
    }
}
