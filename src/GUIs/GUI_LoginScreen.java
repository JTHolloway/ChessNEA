package GUIs;

import LibaryFunctions.Repository;
import LibaryFunctions.Utility;
import User.User;
import User.UserStats;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Calendar;

public class GUI_LoginScreen extends JFrame {

    private static JFrame This;

    /**
     * Constructor for a login screen
     */
    public GUI_LoginScreen() {
        InitialiseComponents();
        This.setVisible(true);
    }

    /**
     * Initializes the components of the Login JPanel with each components properties.
     */
    public void InitialiseComponents() {
        This = this;  //Done so that 'this' can be used when doing action listeners

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int FontSize = screenHeight / 50;

        This.setSize(screenWidth, screenHeight);
        This.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        This.setUndecorated(true);
        This.setLayout(null);
        This.setLocationRelativeTo(null);

        //Initialise JPanels
        JPanel MainPanel = new JPanel();
        JPanel CreateAccountPanel = new JPanel();
        JPanel GuestAccountPanel = new JPanel();
        JPanel LoginPanel = new JPanel();
        JPanel SidePanel = new JPanel();

        //Initialise Main Panel
        MainPanel.setBackground(new Color(59, 56, 56));
        MainPanel.setBounds(0, 0, screenWidth, screenHeight);
        MainPanel.setLayout(null);

        //Initialise JComponents
        JLabel Login_LoginLabel = new JLabel("Login", SwingConstants.CENTER);
        JLabel Guest_GuestLabel = new JLabel("Guest Login", SwingConstants.CENTER);
        JLabel Create_CreateAccountLabel = new JLabel("Create Account", SwingConstants.CENTER);
        JLabel Login_UserIDTagLabel = new JLabel("ID Tag: ", SwingConstants.RIGHT);
        JLabel Login_PasswordLabel = new JLabel("Password: ", SwingConstants.RIGHT);
        JLabel Login_EmailLabel = new JLabel("Email: ", SwingConstants.RIGHT);
        JLabel Guest_UsernameLabel = new JLabel("Temporary Username: ", SwingConstants.RIGHT);
        JLabel Guest_CountryLabel = new JLabel("Country: ", SwingConstants.RIGHT);
        JLabel Create_UserIDTagLabel = new JLabel("ID Tag: ", SwingConstants.RIGHT);
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
        JComboBox Guest_Country = new JComboBox<>(Utility.ObjectArrayToStringArray(Repository.getCountriesFromDatabase()));
        JTextField Create_UserIDTag = new JTextField();
        JTextField Create_Username = new JTextField();
        JTextField Create_Name = new JTextField();
        JTextField Create_Surname = new JTextField();
        JTextField Create_Email = new JTextField();
        JPasswordField Create_Password = new JPasswordField();
        JPasswordField Create_ConfirmPassword = new JPasswordField();
        JComboBox Create_CountryBox = new JComboBox<>(Utility.ObjectArrayToStringArray(Repository.getCountriesFromDatabase()));

        JLabel CloseWindowButton = new JLabel("X", SwingConstants.CENTER);
        JLabel MinimiseWindowButton = new JLabel("-", SwingConstants.CENTER);

        //Init Buttons and Mouse Listeners
        JButton LOGIN = new JButton("Login");
        LOGIN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String ErrorMsg = "";

                if (!(Utility.isNotBlankOrEmpty(Login_UserIDTag.getText()) && Utility.isNotBlankOrEmpty(Login_Email.getText()) && Utility.isNotBlankOrEmpty(String.valueOf(Login_Password.getPassword())))) {
                    ErrorMsg += "Fill in all fields\n";
                }
                if (Utility.CheckValidLogin(Login_UserIDTag.getText(), Login_Email.getText(), String.valueOf(Login_Password.getPassword()))) {
                    Repository.Login(Login_UserIDTag.getText());
                    Repository.updateUsersStats();

                    new GUI_MainJFrame();
                    This.dispose();

                } else {
                    ErrorMsg += "Invalid Details\n";
                    JOptionPane.showMessageDialog(This, ErrorMsg);
                }
            }
        });

        JButton LOGIN_AS_GUEST = new JButton("Login as Guest");
        LOGIN_AS_GUEST.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Repository.Login("Guest");
                Repository.getCurrentUser().setCountry(Guest_Country.getSelectedItem().toString());

                new GUI_MainJFrame();
                This.dispose();
            }
        });

        JButton CREATE_ACCOUNT = new JButton("Create Account");
        //region Create Account button Clicked
        CREATE_ACCOUNT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Component[] Fields = {
                        Create_UserIDTag,
                        Create_Username,
                        Create_Name,
                        Create_Surname,
                        Create_Email,
                        Create_Password,
                        Create_ConfirmPassword
                };

                String ErrorMsg = "";
                boolean isEmpty = false;
                boolean isValid = true;
                for (Component component : Fields) {
                    if (component instanceof JPasswordField) {
                        if (String.valueOf(((JPasswordField) component).getPassword()).isEmpty()) {
                            component.setBackground(new Color(220, 20, 60));
                            isEmpty = true;
                        } else {
                            component.setBackground(new Color(109, 245, 50));
                        }
                    } else if (component instanceof JTextField) {
                        if (((JTextField) component).getText().isEmpty()) {
                            component.setBackground(new Color(220, 20, 60));
                            isEmpty = true;
                        } else {
                            component.setBackground(new Color(109, 245, 50));
                        }
                    }
                }


                if (Utility.CheckValidNewUser(Create_UserIDTag.getText(),
                        Create_Username.getText(),
                        Create_Email.getText(),
                        Create_Name.getText(),
                        Create_Surname.getText(),
                        String.valueOf(Create_Password.getPassword()),
                        String.valueOf(Create_ConfirmPassword.getPassword()))) {
                    Date Today = new java.sql.Date(Calendar.getInstance().getTime().getTime());

                    //Create new User and set to current User
                    Repository.setCurrentUser(
                            new User(Create_UserIDTag.getText(),
                                    Create_Username.getText(),
                                    Create_Email.getText(),
                                    Create_Name.getText(),
                                    Create_Surname.getText(),
                                    Create_CountryBox.getSelectedItem().toString(),
                                    new UserStats(
                                            "Novice",
                                            1000,
                                            0,
                                            0,
                                            0,
                                            Today,
                                            Today
                                    )
                            ));

                    Repository.AddUser(String.valueOf(Create_Password.getPassword()), Create_CountryBox.getSelectedIndex() + 1);
                    new GUI_MainJFrame();
                    This.dispose();


                } else {
                    isValid = false;

                    if (isEmpty) {
                        ErrorMsg += "Fill in all fields\n";
                    }

                    if (Utility.isEmailFormatValid(Create_Email.getText())) {
                        Create_Email.setBackground(new Color(109, 245, 50));
                    } else {
                        Create_Email.setBackground(new Color(220, 20, 60));
                        ErrorMsg += "Invalid Email\n";
                    }

                    if ((String.valueOf(Create_Password.getPassword()).equals(String.valueOf(Create_ConfirmPassword.getPassword()))) &&
                            Utility.isNotBlankOrEmpty((String.valueOf(Create_Password.getPassword())))) {
                        Create_Password.setBackground(new Color(109, 245, 50));
                        Create_ConfirmPassword.setBackground(new Color(109, 245, 50));
                    } else {
                        Create_Password.setBackground(new Color(220, 20, 60));
                        Create_ConfirmPassword.setBackground(new Color(220, 20, 60));
                        ErrorMsg += "Passwords dont match\n";
                    }

                    if (Utility.isNotBlankOrEmpty(Create_UserIDTag.getText())
                            && Utility.isUserIdAvailable(Create_UserIDTag.getText())) {
                        if (Create_UserIDTag.getText().length() == 4) {
                            Create_UserIDTag.setBackground(new Color(109, 245, 50));
                        } else {
                            Create_UserIDTag.setBackground(new Color(220, 20, 60));
                            ErrorMsg += "Id Tag must be 4 characters\n";
                        }
                    } else {
                        Create_UserIDTag.setBackground(new Color(220, 20, 60));
                        ErrorMsg += "Id Tag Invalid or Taken\n";
                    }

                }
                if (!isValid) {
                    JOptionPane.showMessageDialog(This, ErrorMsg);
                }
            }
        });
        //endregion

        Component[] Components = {CreateAccountPanel, GuestAccountPanel, LoginPanel, Login_LoginLabel,
                Guest_GuestLabel, Create_CreateAccountLabel, Login_UserIDTagLabel, Login_PasswordLabel,
                Login_EmailLabel, Guest_UsernameLabel, Guest_CountryLabel, Create_UserIDTagLabel, Create_UsernameLabel,
                Create_NameLabel, Create_SurnameLabel, Create_EmailLabel, Create_PasswordLabel,
                Create_ConfirmPasswordLabel, Create_CountryLabel, Login_UserIDTag, Login_Password,
                Login_Email, Guest_Username, Guest_Country, Create_UserIDTag, Create_Username, Create_Name,
                Create_Surname, Create_Email, Create_Password, Create_ConfirmPassword, Create_CountryBox, LOGIN,
                LOGIN_AS_GUEST, CREATE_ACCOUNT};

        //Set Common attributes to each Component
        for (Component component : Components) {
            if (component instanceof JLabel) {
                component.setFont(new Font("Font", Font.PLAIN, FontSize));
                component.setForeground(Color.WHITE);
                component.setSize((int) (screenWidth * 0.12), (int) (FontSize * 1.9));
            } else if (component instanceof JComboBox) {
                component.setSize((int) (screenWidth * 0.17), (int) (FontSize * 1.7));
                component.setBackground(Color.LIGHT_GRAY);
                component.setForeground(Color.BLACK);
            } else if (component instanceof JTextField) {
                component.setSize((int) (screenWidth * 0.17), (int) (FontSize * 1.7));
                component.setBackground(Color.LIGHT_GRAY);
                component.setForeground(Color.BLACK);
            } else if (component instanceof JButton) {
                component.setSize((int) (screenWidth * 0.1), (int) (FontSize * 1.9));
                component.setBackground(Color.LIGHT_GRAY);
                component.setForeground(Color.BLACK);
            } else if (component instanceof JPanel) {
                component.setBackground(new Color(127, 127, 127));
                ((JPanel) component).setLayout(null);
            }
        }

        //JPanels
        CreateAccountPanel.setBounds((int) (screenWidth * 0.08), (int) (screenHeight * 0.1), (int) (screenWidth * 0.35), (int) (screenHeight * 0.8));
        CreateAccountPanel.setBorder(new LineBorder(Color.BLACK, 2));
        LoginPanel.setBounds((int) (screenWidth * 0.53), (int) (screenHeight * 0.1), (int) (screenWidth * 0.35), (int) (screenHeight * 0.35));
        LoginPanel.setBorder(new LineBorder(Color.BLACK, 2));
        GuestAccountPanel.setBounds((int) (screenWidth * 0.53), (int) (screenHeight * 0.55), (int) (screenWidth * 0.35), (int) (screenHeight * 0.35));
        GuestAccountPanel.setBorder(new LineBorder(Color.BLACK, 2));

        SidePanel.setBackground(new Color(197, 90, 17));
        SidePanel.setSize((int) (screenWidth * 0.04), screenHeight);
        SidePanel.setLocation(screenWidth - SidePanel.getWidth(), 0);

        //JLabel Titles
        Login_LoginLabel.setFont(new Font("Font", Font.BOLD, FontSize * 2));
        Guest_GuestLabel.setFont(new Font("Font", Font.BOLD, FontSize * 2));
        Create_CreateAccountLabel.setFont(new Font("Font", Font.BOLD, FontSize * 2));

        Login_LoginLabel.setBounds(0, (int) (LoginPanel.getHeight() * 0.08), LoginPanel.getWidth(), (int) (FontSize * 2.5));
        Guest_GuestLabel.setBounds(0, (int) (GuestAccountPanel.getHeight() * 0.08), GuestAccountPanel.getWidth(), (int) (FontSize * 2.5));
        Create_CreateAccountLabel.setBounds(0, (int) (CreateAccountPanel.getHeight() * 0.08), CreateAccountPanel.getWidth(), (int) (FontSize * 2.5));

        //region Setup Close Button
        CloseWindowButton.setSize(SidePanel.getWidth(), screenHeight / 26);
        CloseWindowButton.setFont(new Font("Font", Font.PLAIN, screenHeight / 26));
        CloseWindowButton.setForeground(Color.WHITE);
        CloseWindowButton.setLocation(0, 0);
        CloseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        CloseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (CloseWindowButton.getForeground() == Color.WHITE) {
                    CloseWindowButton.setForeground(Color.RED);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (CloseWindowButton.getForeground() == Color.RED) {
                    CloseWindowButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //region Setup Minimise Button
        MinimiseWindowButton.setSize(SidePanel.getWidth(), screenHeight / 26);
        MinimiseWindowButton.setFont(new Font("Font", Font.BOLD, screenHeight / 26));
        MinimiseWindowButton.setForeground(Color.WHITE);
        MinimiseWindowButton.setLocation(0, screenHeight / 26);
        MinimiseWindowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        MinimiseWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                This.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (MinimiseWindowButton.getForeground() == Color.WHITE) {
                    MinimiseWindowButton.setForeground(Color.gray);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (MinimiseWindowButton.getForeground() == Color.gray) {
                    MinimiseWindowButton.setForeground(Color.WHITE);
                }
            }
        });
        //endregion

        //Component Locations
        Login_UserIDTagLabel.setLocation(0, (int) (LoginPanel.getHeight() * 0.3));
        Login_EmailLabel.setLocation(0, (int) (LoginPanel.getHeight() * 0.45));
        Login_PasswordLabel.setLocation(0, (int) (LoginPanel.getHeight() * 0.6));
        Guest_UsernameLabel.setLocation(0, (int) (GuestAccountPanel.getHeight() * 0.3));
        Guest_CountryLabel.setLocation(0, (int) (GuestAccountPanel.getHeight() * 0.5));
        Create_UserIDTagLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.2));
        Create_UsernameLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.275));
        Create_NameLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.35));
        Create_SurnameLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.425));
        Create_EmailLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.5));
        Create_PasswordLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.575));
        Create_ConfirmPasswordLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.65));
        Create_CountryLabel.setLocation(0, (int) (CreateAccountPanel.getHeight() * 0.725));

        Login_UserIDTag.setLocation((int) (screenWidth * 0.14), (int) (LoginPanel.getHeight() * 0.3));
        Login_Email.setLocation((int) (screenWidth * 0.14), (int) (LoginPanel.getHeight() * 0.45));
        Login_Password.setLocation((int) (screenWidth * 0.14), (int) (LoginPanel.getHeight() * 0.6));
        Guest_Username.setLocation((int) (screenWidth * 0.14), (int) (GuestAccountPanel.getHeight() * 0.3));
        Guest_Country.setLocation((int) (screenWidth * 0.14), (int) (GuestAccountPanel.getHeight() * 0.5));
        Create_UserIDTag.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.2));
        Create_Username.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.275));
        Create_Name.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.35));
        Create_Surname.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.425));
        Create_Email.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.5));
        Create_Password.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.575));
        Create_ConfirmPassword.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.65));
        Create_CountryBox.setLocation((int) (screenWidth * 0.14), (int) (CreateAccountPanel.getHeight() * 0.725));

        LOGIN.setLocation((int) (LoginPanel.getWidth() * 0.5) - (LOGIN.getWidth() / 2),
                (int) (LoginPanel.getHeight() * 0.75));
        LOGIN_AS_GUEST.setLocation((int) (GuestAccountPanel.getWidth() * 0.5) - (LOGIN.getWidth() / 2),
                (int) (GuestAccountPanel.getHeight() * 0.75));
        CREATE_ACCOUNT.setLocation((int) (CreateAccountPanel.getWidth() * 0.5) - (LOGIN.getWidth() / 2),
                (int) (CreateAccountPanel.getHeight() * 0.85));

        //Add Components to Panels
        SidePanel.add(CloseWindowButton);
        SidePanel.add(MinimiseWindowButton);

        LoginPanel.add(Login_LoginLabel);
        LoginPanel.add(Login_UserIDTagLabel);
        LoginPanel.add(Login_EmailLabel);
        LoginPanel.add(Login_PasswordLabel);
        LoginPanel.add(Login_UserIDTag);
        LoginPanel.add(Login_Password);
        LoginPanel.add(Login_Email);
        LoginPanel.add(LOGIN);

        GuestAccountPanel.add(Guest_GuestLabel);
        GuestAccountPanel.add(Guest_UsernameLabel);
        GuestAccountPanel.add(Guest_CountryLabel);
        GuestAccountPanel.add(Guest_Username);
        GuestAccountPanel.add(Guest_Country);
        GuestAccountPanel.add(LOGIN_AS_GUEST);

        CreateAccountPanel.add(Create_CreateAccountLabel);
        CreateAccountPanel.add(Create_UserIDTagLabel);
        CreateAccountPanel.add(Create_UsernameLabel);
        CreateAccountPanel.add(Create_NameLabel);
        CreateAccountPanel.add(Create_SurnameLabel);
        CreateAccountPanel.add(Create_EmailLabel);
        CreateAccountPanel.add(Create_PasswordLabel);
        CreateAccountPanel.add(Create_ConfirmPasswordLabel);
        CreateAccountPanel.add(Create_CountryLabel);
        CreateAccountPanel.add(Create_UserIDTag);
        CreateAccountPanel.add(Create_Username);
        CreateAccountPanel.add(Create_Name);
        CreateAccountPanel.add(Create_Surname);
        CreateAccountPanel.add(Create_Email);
        CreateAccountPanel.add(Create_Password);
        CreateAccountPanel.add(Create_ConfirmPassword);
        CreateAccountPanel.add(Create_CountryBox);
        CreateAccountPanel.add(CREATE_ACCOUNT);

        //Add Panels to Frame
        MainPanel.add(LoginPanel);
        MainPanel.add(CreateAccountPanel);
        MainPanel.add(GuestAccountPanel);
        MainPanel.add(SidePanel);

        This.getContentPane().add(MainPanel);
    }
}
