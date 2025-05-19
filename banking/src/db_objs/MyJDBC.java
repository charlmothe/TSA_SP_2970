// This class handles all database operations related to the banking app, such as user authentication, transaction management, and balance updates.
package db_objs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MyJDBC
{
    // URL of the MySQL database server
    private static final String DB_URL = "jdbc:mysql://34.134.208.212:3306/bank_schema";

    // Database username
    private static final String DB_USER = "root";

    // Database password
    private static final String DB_PASSWORD = "Fbla2025";

    // This method validates the login credentials of a user
    public static User validateLogin(String username, String password)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to check if the user exists in the database with the provided username and password
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?");

            // Set the provided username and password in the query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query and retrieve the result
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, return a new User object with the retrieved information
            if(resultSet.next())
            {
                int userId = resultSet.getInt("id");
                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");
                return new User(userId, username, password, currentBalance);
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return null if no matching user is found
        return null;
    }

    // This method registers a new user in the database
    public static boolean register(String username, String password)
    {
        try
        {
            // Check if the username already exists
            if(!checkUser(username))
            {
                // Establish a connection to the database
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Prepare a SQL query to insert a new user into the 'users' table
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, current_balance)" + "VALUES (?, ?, ?)");

                // Set the provided username, password, and initial balance (0) in the query
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setBigDecimal(3, new BigDecimal(0));

                // Execute the query to insert the new user
                preparedStatement.executeUpdate();
                // Return true if the user was successfully registered
                return true;
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return false if the user could not be registered
        return false;
    }

    // This method checks if a username already exists in the database
    private static boolean checkUser(String username)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to check if the username exists in the 'users' table
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If no result is found, the user does not exist
            if (!resultSet.next())
            {
                // The username is available
                return false;
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // The user already exists
        return true;
    }

    // This method adds a transaction record to the database
    public static Boolean addTransactionToDatabase(Transaction transaction)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to insert the transaction into the 'transactions' table
            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date, transaction_memo)" +
                            " VALUES (?, ?, ?, NOW(), ?)");

            // Set the transaction details in the query
            insertTransaction.setInt(1, transaction.getUserID());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());
            insertTransaction.setString(4, transaction.getMemo());

            // Execute the query to insert the transaction
            insertTransaction.executeUpdate();
            // Return true if the transaction was successfully added
            return true;
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return false if the transaction could not be added
        return false;
    }
    // This method adds a chat record to the database
    public static Boolean addChatToDatabase(String chat)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to insert the chat into the 'chat' table
            PreparedStatement insertChat = connection.prepareStatement(
                    "INSERT INTO chat(chat_content)" +
                            " VALUES (?)");

            // Set the chat details in the query
            insertChat.setString(1, chat);

            // Execute the query to insert the chat
            insertChat.executeUpdate();
            // Return true if the transaction was successfully added
            return true;
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return false if the transaction could not be added
        return false;
    }


    // This method updates the user's current balance in the database
    public static boolean updateCurrentBalance(User user)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to update the current balance of the user in the 'users' table
            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE id = ?");
            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());

            // Execute the query to update the balance
            updateBalance.executeUpdate();

            // Return true if the balance was successfully updated
            return true;
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return false if the balance could not be updated
        return false;
    }

    // This method handles the transfer of money between users
    public static boolean transfer(User user, String transferredUsername, float transferAmount, String memo)
    {
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare a SQL query to retrieve the transferred user by username
            PreparedStatement queryUser = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            queryUser.setString(1, transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();

            // If the transferred user is found, process the transaction
            while(resultSet.next())
            {
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );

                // Create transaction objects for both the transferring and receiving users
                Transaction transferredTransaction = new Transaction(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null,
                        memo
                );

                Transaction receivedTransaction = new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null,
                        memo
                );

                // Update the current balance of the transferred user
                transferredUser.setCurrentBalance(
                        transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount))
                );

                // Update the transferred user's balance
                updateCurrentBalance(transferredUser);

                // Log the transaction for the transferred user
                addTransactionToDatabase(transferredTransaction);

                // Update the current balance of the transferring user
                user.setCurrentBalance(
                        user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount))
                );

                // Update the transferring user's balance
                updateCurrentBalance(user);

                // Log the transaction for the transferring user
                addTransactionToDatabase(receivedTransaction);

                // Return true if the transfer was successful
                return true;
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return false if the transfer failed
        return false;
    }

    // This method retrieves a list of past transactions for a specific user, depending on what number the method passes is what information it shows
    public static ArrayList<Transaction> getPastTransactions(User user)
    {

        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllTransactions = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?");
            selectAllTransactions.setInt(1, user.getId());



            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllTransactions.executeQuery();

            // Loop through the result set and add each transaction to the list
            while(resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date"),
                        resultSet.getString("transaction_memo")
                );

                // Add the transaction to the list
                pastTransactions.add(transaction);
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastTransactions;
    }
    public static ArrayList<Transaction> getDateTransactions(User user, String info)
    {

        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllTransactions = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ? AND DATE(transaction_date) = ?");
            selectAllTransactions.setInt(1, user.getId());
            selectAllTransactions.setString(2, info);



            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllTransactions.executeQuery();

            // Loop through the result set and add each transaction to the list
            while(resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date"),
                        resultSet.getString("transaction_memo")
                );

                // Add the transaction to the list
                pastTransactions.add(transaction);
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastTransactions;
    }
    public static ArrayList<Transaction> getWithdrawTransactions(User user)
    {

        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllTransactions = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ? AND transaction_type = 'Withdraw'");
            selectAllTransactions.setInt(1, user.getId());




            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllTransactions.executeQuery();

            // Loop through the result set and add each transaction to the list
            while(resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date"),
                        resultSet.getString("transaction_memo")
                );

                // Add the transaction to the list
                pastTransactions.add(transaction);
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastTransactions;
    }
    public static ArrayList<Transaction> getDepositTransactions(User user)
    {

        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try
        {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllTransactions = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ? AND transaction_type = 'Deposit'");
            selectAllTransactions.setInt(1, user.getId());




            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllTransactions.executeQuery();

            // Loop through the result set and add each transaction to the list
            while(resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date"),
                        resultSet.getString("transaction_memo")
                );

                // Add the transaction to the list
                pastTransactions.add(transaction);
            }
        }
        catch(SQLException e)
        {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastTransactions;
    }
    public static ArrayList<Transaction> getTransferTransactions(User user) {

        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllTransactions = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ? AND transaction_type = 'Transfer'");
            selectAllTransactions.setInt(1, user.getId());


            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllTransactions.executeQuery();

            // Loop through the result set and add each transaction to the list
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date"),
                        resultSet.getString("transaction_memo")
                );

                // Add the transaction to the list
                pastTransactions.add(transaction);
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastTransactions;
    }
    public static ArrayList<String> getpastChats() {

        ArrayList<String> pastChats = new ArrayList<>();
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Prepare a SQL query to retrieve all transactions for the user
            PreparedStatement selectAllChats = connection.prepareStatement(
                    "SELECT chat_content FROM chat");


            // Execute the query and retrieve the result
            ResultSet resultSet = selectAllChats.executeQuery();

            // Loop through the result set and add each transaction to the list
            while (resultSet.next()) {

                //getting the current chat
                String chatContent = resultSet.getString("chat_content");


                // Add the chats to the list
                pastChats.add(chatContent);
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Return the list of past transactions
        return pastChats;
    }

}
