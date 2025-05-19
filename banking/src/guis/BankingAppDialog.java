package guis; // declares the package for the class

import db_objs.MyJDBC; // imports MyJDBC class for database operations
import db_objs.Transaction; // imports Transaction class for handling transactions
import db_objs.User; // imports User class for user-related data
import javax.swing.*; // imports Swing components for GUI
import java.awt.*; // imports AWT classes for layout and fonts
import java.awt.event.ActionEvent; // imports ActionEvent for button actions
import java.awt.event.ActionListener; // imports ActionListener for event handling
import java.math.BigDecimal; // imports BigDecimal for precise decimal calculations
import java.util.ArrayList; // imports ArrayList for handling lists of transactions

//This class will create all the dialogs in our app (ie: deposit, withdraw, transfer)
public class BankingAppDialog extends JDialog implements ActionListener
{
    //creates a variable for the current user coming from our user class
    private User user;

    //creates a variable for GUI in the actual banking app coming from our BankingAppGui class
    private BankingAppGui bankingAppGui;

    //creates JLabel that will display user's balance, where to enter amount, user, and memo
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel, memoLabel;

    //creates TextField where user can enter amounts, other users, and memo
    private JTextField enterAmountField, enterUserField, memoField, transactionField;

    //creates an action button that can be used for all buttons we will need
    private JButton actionButton, searchButton, resetButton;

    //creates the panel where all the past transactions will be displayed
    private JPanel pastTransactionsPanel;

    //creates an array list that will hold the data for each past transaction
    private ArrayList<Transaction> pastTransactions, filteredTransactions;

    //This will be the header class that will hold every dialog
    public BankingAppDialog(BankingAppGui bankingAppGui, User user)
    {
        //sets the dialog size to 400 x 400 pixels
        setSize(400, 400);

        //makes the parent window (BankingAppGui) inaccessible while the child dialog is open
        setModal(true);

        //centers the dialog window on the screen
        setLocationRelativeTo(null);

        //ensures the program continues to run when the dialog is closed
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //disables resizing for the dialog window
        setResizable(false);

        //sets layout to null, allowing manual component placement
        setLayout(null);

        //initializes the BankingAppGui instance with the given parameter
        this.bankingAppGui = bankingAppGui;

        //initializes the user instance with the given parameter
        this.user = user;
    }

    //this method will put the current balance in the current open dialog
    public void addCurrentBalanceAmount()
    {
        //creates a JLabel to display the current user's balance
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());

        //sets the location and size of the balance label
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);

        //sets the font of the balance label to bold with size 16
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));

        //aligns the text in the label to be centered
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the balance label to the dialog
        add(balanceLabel);

        //creates a JLabel to prompt the user to enter an amount
        enterAmountLabel = new JLabel("Enter amount: ");

        //sets the location and size of the amount label
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);

        //sets the font of the amount label to plain with size 16
        enterAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the amount label to be centered
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the amount label to the dialog
        add(enterAmountLabel);

        //creates a text field for entering amounts
        enterAmountField = new JTextField();

        //sets the location and size of the amount text field
        enterAmountField.setBounds(100, 80, 200, 20);

        //sets the font of the text field to plain with size 16
        enterAmountField.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the text field to be centered
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the text field to the dialog
        add(enterAmountField);
    }

    //This will create a method for a button that users can universally use on all the dialog menus
    public void addActionButton(String actionButtonType)
    {
        //creates a button with the specified action type as its label
        actionButton = new JButton(actionButtonType);

        //sets the location and size of the button
        actionButton.setBounds(100, 300, 200, 20);

        //sets the font of the button label to plain with size 16
        actionButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the button label to be centered
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);

        //adds an action listener to the button for event handling
        actionButton.addActionListener(this);

        //adds the button to the dialog
        add(actionButton);
    }

    //this method adds a text field and label for entering a username
    public void addUserField()
    {
        //creates a label prompting the user to enter a username
        enterUserLabel = new JLabel("Enter User:");

        //sets the location and size of the user label
        enterUserLabel.setBounds(100, 130, 200, 20);

        //sets the font of the user label to plain with size 16
        enterUserLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the user label to be centered
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the user label to the dialog
        add(enterUserLabel);

        //creates a text field for entering a username
        enterUserField = new JTextField();

        //sets the location and size of the user text field
        enterUserField.setBounds(100, 160, 200, 20);

        //sets the font of the user text field to plain with size 16
        enterUserField.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the text field to be centered
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the text field to the dialog
        add(enterUserField);
    }

    //this method adds a text field and label for entering a memo
    public void addMemo()
    {
        //creates a label prompting the user to enter a memo
        memoLabel = new JLabel("Memo:");

        //sets the location and size of the memo label
        memoLabel.setBounds(100, 200, 200, 20);

        //sets the font of the memo label to plain with size 16
        memoLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the memo label to be centered
        memoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the memo label to the dialog
        add(memoLabel);

        //creates a text field for entering a memo
        memoField = new JTextField();

        //sets the location and size of the memo text field
        memoField.setBounds(100, 230, 200, 20);

        //sets the font of the memo text field to plain with size 16
        memoField.setFont(new Font("Dialog", Font.PLAIN, 16));

        //aligns the text in the memo text field to be centered
        memoField.setHorizontalAlignment(SwingConstants.CENTER);

        //adds the memo text field to the dialog
        add(memoField);
    }

    //this method adds components to display past transactions
    public void addPastTransactionsComponents()
    {
        //sets the size of the dialog window to 600x300 pixels
        setSize(600, 350);

        //centers the dialog window on the screen
        setLocationRelativeTo(null);

        //creates a panel to display past transactions
        pastTransactionsPanel = new JPanel();

        //sets the layout of the panel to BoxLayout with vertical alignment
        pastTransactionsPanel.setLayout(new BoxLayout(pastTransactionsPanel, BoxLayout.Y_AXIS));

        //sets the size of the past transactions panel
        pastTransactionsPanel.setSize(100, 100);

        //creates a scroll pane for the panel to enable scrolling
        JScrollPane scrollPane = new JScrollPane(pastTransactionsPanel);

        //sets the scroll bar policies for the scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //sets the location and size of the scroll pane
        scrollPane.setBounds(0, 50, getWidth() - 15, getHeight() - 70);

        //creates reset button
        resetButton = new JButton("Reset");

        //sets locations
        resetButton.setBounds(400,14,100,30);

        //sets font
        resetButton.setFont(new Font("Dialog", Font.BOLD, 16));

        //adds reset button to components
        add(resetButton);

        //creates an action listener for reset button
        resetButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //clears the search bar
                transactionField.setText(" ");

                //resets the pain to show all transactions 
                filteredTransactions = MyJDBC.getPastTransactions(user);
                refreshPastTransactionsPanel(filteredTransactions);
            }
        });

        //Creates a search bar to filer past transactions
        transactionField = new JTextField();

        //creates its position
        transactionField.setBounds(15,15,250,30);

        //sets font
        transactionField.setFont(new Font("Dialog", Font.PLAIN, 16));

        //sets the text to right align
        transactionField.setHorizontalAlignment(SwingConstants.RIGHT);

        //adds to components
        add(transactionField);

        //Creates a search button
        searchButton = new JButton("Search");

        //sets its location
        searchButton.setBounds(275,14,100,30);

        //sets Search font
        searchButton.setFont(new Font("Dialog", Font.BOLD, 16));

        //adds button to components
        add(searchButton);


        //retrieves the list of past transactions for the current user
        pastTransactions = MyJDBC.getPastTransactions(user);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = transactionField.getText().trim(); // Get user input
                ArrayList<Transaction> filteredTransactions;
        
                if (searchText.equalsIgnoreCase("deposit")) {
                    // Filter only deposit transactions
                    filteredTransactions = MyJDBC.getDepositTransactions(user);
                } else if (searchText.equalsIgnoreCase("withdraw")) {
                    // Filter only withdrawal transactions
                    filteredTransactions = MyJDBC.getWithdrawTransactions(user);
                } 
                else if (searchText.equalsIgnoreCase("transfer")) {
                    // Filter only transfer transactions
                    filteredTransactions = MyJDBC.getTransferTransactions(user);
                } 
                else if(searchText.length() == 10)
                {
                    // Filter only certain date transactions
                    filteredTransactions = MyJDBC.getDateTransactions(user, searchText);
                }
                else 
                {
                    // Default to fetching all transactions if search text is invalid
                    filteredTransactions = MyJDBC.getPastTransactions(user);
                    JOptionPane.showMessageDialog(BankingAppDialog.this, "Please either enter: \n withdraw, deposit, tranfer, or \n a date in the form: yyyy-mm-dd");
                }
        
                // Refresh the transactions panel with the filtered data
                refreshPastTransactionsPanel(filteredTransactions);
            }
        });
        
        //iterates through the list of transactions in reverse order
        for (int i = pastTransactions.size() - 1; i >= 0; i--) {
            //gets the current transaction from the list
            Transaction pastTransaction = pastTransactions.get(i);

            //creates a panel to display details of the current transaction
            JPanel pastTransactionsContainer = new JPanel();

            //sets the layout of the transaction container to BorderLayout
            pastTransactionsContainer.setLayout(new BorderLayout());

            //creates a label to display the transaction type
            JLabel transactionTypeLabel = new JLabel(pastTransaction.getTransactionType());

            //sets the font of the transaction type label to bold with size 18
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 18));

            //creates a label to display the transaction amount
            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));

            //sets the font of the transaction amount label to bold with size 18
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 18));

            //creates a label to display the transaction date
            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));

            //sets the font of the transaction date label to bold with size 18
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 18));

            //aligns the text in the transaction date label to be centered
            transactionDateLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //creates a label to display the transaction memo
            JLabel transactionMemoLabel = new JLabel("Memo: " + pastTransaction.getMemo());

            //sets the font of the transaction memo label to bold with size 18
            transactionMemoLabel.setFont(new Font("Dialog", Font.BOLD, 18));

            //adds the transaction type label to the west of the container
            pastTransactionsContainer.add(transactionTypeLabel, BorderLayout.WEST);

            //adds the transaction amount label to the east of the container
            pastTransactionsContainer.add(transactionAmountLabel, BorderLayout.EAST);

            //adds the transaction date label to the center of the container
            pastTransactionsContainer.add(transactionDateLabel, BorderLayout.CENTER);

            //adds the transaction memo label to the south of the container
            pastTransactionsContainer.add(transactionMemoLabel, BorderLayout.SOUTH);

            //sets a black border around the transaction container
            pastTransactionsContainer.setBorder(BorderFactory.createLineBorder(Color.black));

            //sets the background color of the transaction container to white
            pastTransactionsContainer.setBackground(Color.WHITE);

            //adds the transaction container to the past transactions panel
            pastTransactionsPanel.add(pastTransactionsContainer);


        }

        //adds the scroll pane to the dialog
        add(scrollPane);
    }

    //this method handles a deposit or withdrawal transaction
    private void handleTransaction(String transactionType, float amountVal, String memoVal)
    {
        //creates a transaction object
        Transaction transaction;

        //checks if the transaction type is "Deposit"
        if (transactionType.equalsIgnoreCase("Deposit"))
        {
            //updates the user's current balance by adding the deposit amount
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            //creates a transaction object for the deposit
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null, memoVal);
        }
        else
        {
            //updates the user's current balance by subtracting the withdrawal amount
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(-amountVal)));

            //creates a transaction object for the withdrawal
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null, memoVal);
        }

        //checks if the transaction is successfully added to the database and the balance is updated
        if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user))
        {
            //displays a success message to the user
            JOptionPane.showMessageDialog(this, transactionType + " successful");

            //resets the input fields and updates the current balance displayed
            resetFieldsAndUpdateCurrentBalance();
        }
        else
        {
            //displays an error message to the user if the transaction fails
            JOptionPane.showMessageDialog(this, transactionType + " failed");
        }
    }

    private void refreshPastTransactionsPanel(ArrayList<Transaction> transactions) {
        // Remove all existing components from the panel
        pastTransactionsPanel.removeAll();
    
        // Rebuild the panel with the filtered transactions
        for (Transaction transaction : transactions) {
            JPanel pastTransactionsContainer = new JPanel();
            pastTransactionsContainer.setLayout(new BorderLayout());
    
            JLabel transactionTypeLabel = new JLabel(transaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 18));
    
            JLabel transactionAmountLabel = new JLabel(String.valueOf(transaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 18));
    
            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 18));
            transactionDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
            JLabel transactionMemoLabel = new JLabel("Memo: " + transaction.getMemo());
            transactionMemoLabel.setFont(new Font("Dialog", Font.BOLD, 18));
    
            pastTransactionsContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionsContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionsContainer.add(transactionDateLabel, BorderLayout.CENTER);
            pastTransactionsContainer.add(transactionMemoLabel, BorderLayout.SOUTH);
    
            pastTransactionsContainer.setBorder(BorderFactory.createLineBorder(Color.black));
            pastTransactionsContainer.setBackground(Color.WHITE);
    
            pastTransactionsPanel.add(pastTransactionsContainer);
        }
    
        // Refresh the UI
        pastTransactionsPanel.revalidate();
        pastTransactionsPanel.repaint();
    }
    

    //this method resets input fields and updates the current balance display
    private void resetFieldsAndUpdateCurrentBalance()
    {
        //clears the text in the amount input field
        enterAmountField.setText("");

        //clears the text in the user input field if it exists
        if (enterUserField != null)
        {
            enterUserField.setText("");
        }

        //clears the text in the memo input field if it exists
        if (memoField != null)
        {
            memoField.setText("");
        }

        //updates the balance label to display the updated user balance
        balanceLabel.setText("Balance: $" + user.getCurrentBalance());

        //updates the balance display in the main banking app GUI
        bankingAppGui.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }

    //this method handles the transfer of funds between users
    private void handleTransfer(User user, String transferredUser, float amount, String memo)
    {
        //checks if the entered amount is negative
        if (amount < 0)
        {
            //displays an error message if the amount is negative
            JOptionPane.showMessageDialog(this, "Please enter a positive amount");
        }
        //checks if the transfer is successfully completed
        else if (MyJDBC.transfer(user, transferredUser, amount, memo))
        {
            //displays a success message to the user
            JOptionPane.showMessageDialog(this, "Transfer Successful");

            //resets the input fields and updates the current balance display
            resetFieldsAndUpdateCurrentBalance();
        }
        else
        {
            //displays an error message if the transfer fails
            JOptionPane.showMessageDialog(this, "Transfer Failed");
        }
    }

    //this method handles actions performed when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //gets the label of the button that was clicked
        String buttonPressed = e.getActionCommand();

        //parses the entered amount as a float value
        float amountVal = Float.parseFloat(enterAmountField.getText());

        //gets the text entered in the memo field
        String memoVal = String.valueOf(memoField.getText());

        //checks if the "Deposit" button was clicked
        if (buttonPressed.equalsIgnoreCase("Deposit"))
        {
            //checks if the entered amount is negative
            if (amountVal < 0)
            {
                //displays an error message for negative amounts
                JOptionPane.showMessageDialog(BankingAppDialog.this, "Amount cannot be negative");
                return;
            }
            else
            {
                //handles the deposit transaction
                handleTransaction(buttonPressed, amountVal, memoVal);
            }
        }
        else
        {
            //compares the entered amount with the user's current balance
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));

            //checks if the user has insufficient funds
            if (result < 0)
            {
                //displays an error message for insufficient funds
                JOptionPane.showMessageDialog(this, "Insufficient funds");
                return;
            }

            //checks if the "Withdraw" button was clicked
            if (buttonPressed.equalsIgnoreCase("Withdraw"))
            {
                //checks if the entered amount is negative
                if (amountVal < 0)
                {
                    //displays an error message for negative amounts
                    JOptionPane.showMessageDialog(BankingAppDialog.this, "Amount cannot be negative");
                    return;
                }
                else
                {
                    //handles the withdrawal transaction
                    handleTransaction(buttonPressed, amountVal, memoVal);
                }
            }
            else
            {
                //gets the username entered for the transfer
                String transferredUser = enterUserField.getText();

                //handles the fund transfer
                handleTransfer(user, transferredUser, amountVal, memoVal);
            }
        }
    }
}





