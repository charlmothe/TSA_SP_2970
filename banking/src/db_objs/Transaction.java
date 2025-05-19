// This class represents a transaction, including details like user ID, transaction type, amount, date, and memo.
package db_objs;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction
{
    // Stores the ID of the user associated with this transaction
    private final int userID;

    // Stores the type of the transaction (e.g., Deposit, Withdraw, Transfer)
    private final String transactionType;

    // Stores the amount involved in the transaction
    private final BigDecimal transactionAmount;

    // Stores the date and time when the transaction occurred
    private final Date transactionDate;

    // Stores a memo or description associated with the transaction
    private final String memo;

    // Constructor to initialize all fields of the Transaction class
    public Transaction(int userID, String transactionType, BigDecimal transactionAmount, Date transactionDate, String memo)
    {
        // Sets the userID field to the provided value
        this.userID = userID;

        // Sets the transactionType field to the provided value
        this.transactionType = transactionType;

        // Sets the transactionAmount field to the provided value
        this.transactionAmount = transactionAmount;

        // Sets the transactionDate field to the provided value
        this.transactionDate = transactionDate;

        // Sets the memo field to the provided value
        this.memo = memo;
    }

    // Getter method to retrieve the user ID of the transaction
    public int getUserID() {return userID;}

    // Getter method to retrieve the type of transaction (e.g., Deposit, Withdraw)
    public String getTransactionType() {return transactionType;}

    // Getter method to retrieve the amount of the transaction
    public BigDecimal getTransactionAmount() {return transactionAmount;}

    // Getter method to retrieve the date and time of the transaction
    public Date getTransactionDate() {return transactionDate;}

    // Getter method to retrieve the memo or description of the transaction
    public String getMemo() {return memo;}
}
