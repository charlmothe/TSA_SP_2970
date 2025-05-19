// The RegisterGui class handles the user interface for account registration in the banking application.
package guis;

// Import MyJDBC class for database interactions.
import db_objs.MyJDBC;

// Import Swing components for GUI design.
import javax.swing.*;

// Import AWT for layout and font handling.
import java.awt.*;

// Import AWT for ActionEvent handling.
import java.awt.event.ActionEvent;

// Import AWT for ActionListener.
import java.awt.event.ActionListener;

// Import MouseAdapter for mouse event handling.
import java.awt.event.MouseAdapter;

// Import MouseEvent for handling mouse clicks.
import java.awt.event.MouseEvent;

public class RegisterGui extends BaseFrame
{

    // Constructor that initializes the frame with the title "Register Account".
    public RegisterGui()
    {
        super("Register Account");
    }

    // Overridden method to add all the components to the registration GUI.
    @Override
    protected void addGuiComponents()
    {

        // Title label for creating an account.
        JLabel CreateLabel = new JLabel("Create Account");

        // Set position and size.
        CreateLabel.setBounds(200, 25, 400, 50);

        // Set font style and size.
        CreateLabel.setFont(new Font("Dialog", Font.PLAIN, 38));

        // Center the text.
        CreateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the frame.
        add(CreateLabel);

        // Username label and text box for input.
        JLabel usernameLabel = new JLabel("Username:");

        // Set position and size.
        usernameLabel.setBounds(200, 75, 400, 50);

        // Set font style and size.
        usernameLabel.setFont(new Font("Dialog", Font.BOLD, 30));

        // Left align the text.
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add username label to the frame.
        add(usernameLabel);

        // TextField for username input.
        JTextField usernameBox = new JTextField();

        // Set position and size.
        usernameBox.setBounds(225, 125, 350, 40);

        // Set font style and size.
        usernameBox.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Left align the text.
        usernameBox.setHorizontalAlignment(SwingConstants.LEFT);

        // Add username text box to the frame.
        add(usernameBox);

        // Password label and password field for input.
        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(200, 175, 400, 50);

        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 30));

        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add password label to the frame.
        add(passwordLabel);

        // PasswordField for password input.
        JPasswordField passwordBox = new JPasswordField();

        passwordBox.setBounds(225, 225, 350, 40);

        passwordBox.setFont(new Font("Dialog", Font.PLAIN, 24));

        passwordBox.setHorizontalAlignment(SwingConstants.LEFT);

        // Add password text field to the frame.
        add(passwordBox);

        // Re-enter password label and password field for confirmation.
        JLabel repasswordLabel = new JLabel("Re-enter password:");

        repasswordLabel.setBounds(200, 285, 400, 30);

        repasswordLabel.setFont(new Font("Dialog", Font.BOLD, 30));

        repasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add re-password label to the frame.
        add(repasswordLabel);

        // Re-enter PasswordField for password confirmation.
        JPasswordField repasswordBox = new JPasswordField();

        repasswordBox.setBounds(225, 325, 350, 40);

        repasswordBox.setFont(new Font("Dialog", Font.PLAIN, 24));

        repasswordBox.setHorizontalAlignment(SwingConstants.LEFT);

        // Add re-password text field to the frame.
        add(repasswordBox);

        // Login button to navigate back to the login screen.
        JButton loginButton = new JButton("Login to existing account");

        loginButton.setBounds(225, 450, 350, 40);

        loginButton.setFont(new Font("Dialog", Font.BOLD, 18));

        loginButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Add mouse listener to the login button to trigger login screen navigation.
        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Close the current registration screen.
                RegisterGui.this.dispose();
                // Open the login screen.
                new LoginGui().setVisible(true);
            }
        });
        // Add the login button to the frame.
        add(loginButton);

        // Create account button to handle registration process.
        JButton createAccount = new JButton("Create Account");

        createAccount.setBounds(225, 400, 350, 40);

        createAccount.setFont(new Font("Dialog", Font.BOLD, 18));

        createAccount.setHorizontalAlignment(SwingConstants.CENTER);

        // Add action listener to the create account button for account creation.
        createAccount.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Retrieve input data for username, password, and re-entered password.
                String username = usernameBox.getText();

                String password = String.valueOf(passwordBox.getPassword());

                String rePassword = String.valueOf(repasswordBox.getPassword());

                // Validate user input.
                if (validateUserInput(username, password, rePassword))
                {
                    // If input is valid, attempt to register the user in the database.
                    if (MyJDBC.register(username, password))
                    {
                        // Close the registration screen.
                        RegisterGui.this.dispose();

                        // Open the login screen.
                        LoginGui loginGui = new LoginGui();

                        // Make the login screen visible.
                        loginGui.setVisible(true);

                        JOptionPane.showMessageDialog(RegisterGui.this, "Account Creation Successful");
                    }
                    else
                    {
                        // If username already exists, show an error message.
                        JOptionPane.showMessageDialog(RegisterGui.this, "Username already exists");
                    }
                }
            }
        });
        // Add the create account button to the frame.
        add(createAccount);
    }

    // Helper method to validate user input.
    private boolean validateUserInput(String username, String password, String rePassword)
    {
        // Check if any field is empty.
        if (username.length() == 0 || password.length() == 0 || rePassword.length() == 0)
        {
            JOptionPane.showMessageDialog(RegisterGui.this, "Please enter all the fields correctly.");

            return false;
        }

        // Check if the username is at least 6 characters long.
        if (username.length() < 6)
        {
            JOptionPane.showMessageDialog(RegisterGui.this, "Username must be at least 6 characters long");

            return false;
        }

        // Check if the password and re-entered password match.
        if (!password.equals(rePassword))
        {
            JOptionPane.showMessageDialog(RegisterGui.this, "Passwords do not match");

            return false;
        }

        // If all validations pass, return true.
        return true;
    }
}
