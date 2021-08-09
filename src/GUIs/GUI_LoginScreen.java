package GUIs;

import javax.swing.*;
import java.awt.*;

public class GUI_LoginScreen extends JFrame {

    private static JFrame This;

    public GUI_LoginScreen() {
        InitialiseComponents();
    }

    public void InitialiseComponents() {
        This = this;

        //Initialise JPanels
        JPanel CreateAccountPanel = new JPanel();
        JPanel GuestAccountPanel = new JPanel();
        JPanel LoginPanel = new JPanel();

        //Initialise JComponents
        JLabel Login_LoginLabel = new JLabel("Login", SwingConstants.RIGHT);
        JLabel Guest_GuestLabel = new JLabel("Guest Login", SwingConstants.RIGHT);
        JLabel Create_CreateAccountLabel = new JLabel("Create Account", SwingConstants.RIGHT);
        JLabel Login_UserIDTagLabel = new JLabel("ID Tag: ", SwingConstants.RIGHT);
        JLabel Login_PasswordLabel = new JLabel("Password: ", SwingConstants.RIGHT);
        JLabel Login_EmailLabel = new JLabel("Email: ", SwingConstants.RIGHT);
        JLabel Guest_UsernameLabel = new JLabel("Temporary Username: ", SwingConstants.RIGHT);
        JLabel Guest_CountryLabel = new JLabel("Country: ", SwingConstants.RIGHT);
        JLabel Create_UsernameLabel = new JLabel("Username: ", SwingConstants.RIGHT);
        JLabel Create_NameLabel = new JLabel("First Name: ", SwingConstants.RIGHT);
        JLabel Create_SurnameLabel = new JLabel("Last Name: ", SwingConstants.RIGHT);
        JLabel Create_EmailLabel = new JLabel("Email: ", SwingConstants.RIGHT);
        JLabel Create_PasswordLabel = new JLabel("Password: ", SwingConstants.RIGHT);
        JLabel Create_ConfirmPasswordLabel = new JLabel("Confirm Password: ", SwingConstants.RIGHT);
        JLabel Create_CountryLabel = new JLabel("Country: ", SwingConstants.RIGHT);

        JTextField Login_UserIDTag = new JTextField();
        JPasswordField Login_Password = new JPasswordField();
        JTextField Login_Email = new JTextField();
        JTextField Guest_Username = new JTextField();
        JComboBox Guest_Country = new JComboBox();
        JTextField Create_Username = new JTextField();
        JTextField Create_Name = new JTextField();
        JTextField Create_Surname = new JTextField();
        JTextField Create_Email = new JTextField();
        JPasswordField Create_Password = new JPasswordField();
        JPasswordField Create_ConfirmPassword = new JPasswordField();
        JComboBox Create_CountryBox = new JComboBox();

        Component[] Components = {CreateAccountPanel, GuestAccountPanel, LoginPanel, Login_UserIDTagLabel,
                Login_LoginLabel, Guest_GuestLabel, Create_CreateAccountLabel, Login_PasswordLabel, Login_EmailLabel,
                Guest_UsernameLabel, Guest_CountryLabel, Create_UsernameLabel, Create_NameLabel,
                Create_SurnameLabel, Create_EmailLabel, Create_PasswordLabel, Create_ConfirmPasswordLabel,
                Create_CountryLabel, Login_UserIDTag, Login_Password, Login_Email, Guest_Username,
                Guest_Country, Create_Username, Create_Name, Create_Surname, Create_Email, Create_Password,
                Create_ConfirmPassword, Create_CountryBox};

        //Set Common attributes to each JLabel
        for (Component component : Components) {
            if (component instanceof JLabel) {
                component.setFont(new Font("Font", Font.PLAIN, 15));
                component.setForeground(Color.WHITE);
                component.setSize(40, 15);
            } else if (component instanceof JComboBox) {

            } else if (component instanceof JTextField) {

            } else if (component instanceof JButton) {

            } else if (component instanceof JSeparator) {

            } else if (component instanceof JPanel) {

            }
        }

        this.setSize(640, 360);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);


    }
}
