package guis;

// Import custom database objects and user management classes
import db_objs.MyJDBC;
import db_objs.User;

// Import necessary libraries for GUI creation and event handling
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Class representing the Login GUI for the banking application
public class LoginGui extends BaseFrame
{
    // Constructor: Sets the title of the frame
    public LoginGui()
    {
        super("Banking app login");
    }

    // Method to add components (labels, buttons, etc.) to the GUI
    @Override
    protected void addGuiComponents()
    {
        // Title label for the login screen
        JLabel BankingLabel = new JLabel("Banking app login");

        // Position and size
        BankingLabel.setBounds(200,75, 400, 50);

        // Font styling
        BankingLabel.setFont(new Font("Dialog", Font.PLAIN, 38));

        // Center alignment
        BankingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add label to the GUI
        add(BankingLabel);

        // Label for the username field
        JLabel usernameLabel = new JLabel("Username:");

        // Position and size
        usernameLabel.setBounds(200,175, 400, 50);

        // Font styling
        usernameLabel.setFont(new Font("Dialog", Font.BOLD, 30));

        // Left alignment
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add label to the GUI
        add(usernameLabel);

        // Text field for entering the username
        JTextField usernameBox = new JTextField();

        // Position and size
        usernameBox.setBounds(225,225, 350, 40);

        // Font styling
        usernameBox.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Left alignment
        usernameBox.setHorizontalAlignment(SwingConstants.LEFT);

        // Add text field to the GUI
        add(usernameBox);

        // Label for the password field
        JLabel passwordLabel = new JLabel("Password:");

        // Position and size
        passwordLabel.setBounds(200,300, 400, 50);

        // Font styling
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 30));

        // Left alignment
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add label to the GUI
        add(passwordLabel);

        // Password field for entering the password (masked input)
        JPasswordField passwordBox = new JPasswordField();

        // Position and size
        passwordBox.setBounds(225,350, 350, 40);

        // Font styling
        passwordBox.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Left alignment
        passwordBox.setHorizontalAlignment(SwingConstants.LEFT);

        // Add password field to the GUI
        add(passwordBox);

        // Button for logging in
        JButton loginButton = new JButton("Login");

        // Position and size
        loginButton.setBounds(225,425, 125, 40);

        // Font styling
        loginButton.setFont(new Font("Dialog", Font.BOLD, 18));

        // Center alignment
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Add event listener for button click
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Get the username and password entered by the user
                String username = usernameBox.getText();

                String password = String.valueOf(passwordBox.getPassword());

                // Validate the login credentials using the MyJDBC class
                User user = MyJDBC.validateLogin(username, password);

                // If login is successful
                if(user != null)
                {
                    // Close the login window
                    LoginGui.this.dispose();

                    // Open the main banking application GUI
                    BankingAppGui bankingAppGui = new BankingAppGui(user);

                    bankingAppGui.setVisible(true);

                    // Show success message
                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successful");
                }
                // If login fails
                else
                {
                    // Show error message
                    JOptionPane.showMessageDialog(LoginGui.this, "Invalid username or password");
                }
            }
        });
        // Add login button to the GUI
        add(loginButton);

        // Button for creating a new account
        JButton createAccount = new JButton("Create Account");

        // Position and size
        createAccount.setBounds(375,425, 200, 40);

        // Font styling
        createAccount.setFont(new Font("Dialog", Font.BOLD, 18));

        // Center alignment
        createAccount.setHorizontalAlignment(SwingConstants.CENTER);

        // Add mouse listener for button click
        createAccount.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Close the login window
                LoginGui.this.dispose();

                // Open the registration GUI
                new RegisterGui().setVisible(true);
            }
        });
        // Add create account button to the GUI
        add(createAccount);
    }
}
