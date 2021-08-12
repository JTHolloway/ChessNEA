package User;

import javax.swing.*;

public class User {

    private final String UserID;
    private String UserName;
    private String Email;
    private String Name;
    private String Surname;
    private String Country;
    private final UserStats Statistics;
    private ImageIcon ProfilePicture;

    /**
     * Constructor for a User
     * @param userID A Unique User Defined 4-digit Identification key
     * @param userName A non-unique User Defined User name
     * @param email The Users unique email address
     * @param name The users first name
     * @param surname The users last name
     * @param country The users country/location
     * @param statistics An object of UserStats class, Containing further player details (such as join date)
     * @param profilePicture An Image of a user chosen profile picture
     */
    public User(String userID, String userName, String email, String name, String surname, String country, UserStats statistics, ImageIcon profilePicture) {
        UserID = userID;
        UserName = userName;
        Email = email;
        Name = name;
        Surname = surname;
        Country = country;
        Statistics = statistics;
        ProfilePicture = profilePicture;
    }

    public User(String userID, String userName, String email, String name, String surname, String country, UserStats statistics) {
        UserID = userID;
        UserName = userName;
        Email = email;
        Name = name;
        Surname = surname;
        Country = country;
        Statistics = statistics;
        //get default profile picture from database
    }

    /*Setter Methods for each Variable (Except UserID or Statistics)*/
    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }
    public void setSurname(String surname) {
        Surname = surname;
    }
    public void setCountry(String country) {
        Country = country;
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
    public String getName() {
        return Name;
    }
    public String getSurname() {
        return Surname;
    }
    public String getCountry() {
        return Country;
    }
    public UserStats getStatistics() {
        return Statistics;
    }
}
