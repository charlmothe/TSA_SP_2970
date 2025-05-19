// The User class represents a user in the banking application, with their personal information and current balance.
package db_objs;

// Import classes for handling BigDecimal numbers and rounding modes.
import java.math.BigDecimal;
import java.math.RoundingMode;

public class User
{

    // Private instance variables for user details.

    // Unique identifier for the user
    private int id;

    // User's username and password, which are set once and cannot be changed.
    private final String username, password;

    // User's current balance (can be modified).
    public BigDecimal currentBalance;

    // Constructor to initialize the User object with its attributes.
    public User(int id, String username, String password, BigDecimal currentBalance)
    {
        // Set the user ID.
        this.id = id;

        // Set the username.
        this.username = username;

        // Set the password.
        this.password = password;

        // Set the user's initial balance.
        this.currentBalance = currentBalance;
    }

    // Getter method to retrieve the user's ID.
    public int getId() {
        return id;  // Return the user's unique ID.
    }

    // Getter method to retrieve the user's username.
    public String getUsername()
    {
        // Return the username.
        return username;
    }

    // Getter method to retrieve the user's password.
    public String getPassword()
    {
        // Return the password (note: this might be used in validation, but avoid exposing in real apps).
        return password;
    }

    // Getter method to retrieve the current balance.
    public BigDecimal getCurrentBalance()
    {
        // Return the current balance (BigDecimal allows for precise currency calculations).
        return currentBalance;
    }

    // Setter method to update the user's current balance.

    // The balance is rounded to two decimal places for currency precision.
    public void setCurrentBalance(BigDecimal newBalance)
    {
        // Set the new balance, rounding it to two decimal places using floor rounding.
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }
}
