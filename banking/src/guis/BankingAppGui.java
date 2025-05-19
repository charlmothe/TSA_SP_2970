// This class represents the main GUI for the banking application where users can interact with their bank account.
package guis;
import db_objs.MyJDBC;
import db_objs.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import lib.ChatBot;

// The class extends BaseFrame to inherit common window functionality and implements ActionListener to handle button click events.
public class BankingAppGui extends BaseFrame implements ActionListener
{

    // Declare a JTextField that will display the user's current balance.
    private JTextField currentBalanceField;

    // Getter method to access the currentBalanceField. This allows other classes to retrieve or update the current balance display.
    public JTextField getCurrentBalanceField()
    {
        return currentBalanceField;
    }

    // Constructor that takes a User object and passes it to the parent constructor.
    public BankingAppGui(User user)
    {
        // Sets the window title to "Banking App" and passes the user to the parent class.
        super("Banking App", user);
    }

    // Override the addGuiComponents method to add all the necessary components (buttons, labels, etc.) to the GUI.
    @Override
    protected void addGuiComponents()
    {
        // Welcome label that displays the username of the logged-in user.
        JLabel welcome = new JLabel("Welcome " + user.getUsername());

        welcome.setBounds(525, 10, 250, 30);

        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        // Setting font and alignment for the welcome message.
        welcome.setFont(new Font("Dialog", Font.PLAIN, 26));

        // Adding the welcome label to the frame.
        add(welcome);

        // Label for displaying the current balance title.
        JLabel balanceLabel = new JLabel("Current Balance:");

        balanceLabel.setBounds(10, 50, 250, 30);

        balanceLabel.setHorizontalAlignment(SwingConstants.LEFT);

        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 24));

        // Adding the balance label to the frame.
        add(balanceLabel);

        // JTextField to display the current balance, formatted to show a dollar sign and the user's balance.
        currentBalanceField = new JTextField("$" + user.getCurrentBalance());

        currentBalanceField.setBounds(10, 75, 250, 30);

        // Right-align the text in the field.
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set font for displaying balance.
        currentBalanceField.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Make the field non-editable as it's just for display.
        currentBalanceField.setEditable(false);

        // Adding the current balance field to the frame.
        add(currentBalanceField);

        // Deposit button for depositing money into the account. When clicked, it triggers an action event.
        JButton depositButton = new JButton("Deposit");

        depositButton.setBounds(10, 125, 250, 30);

        // Center-align the text on the button.
        depositButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the font of the button.
        depositButton.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Adding this class as an ActionListener to handle button clicks.
        depositButton.addActionListener(this);

        // Adding the deposit button to the frame.
        add(depositButton);

        // Withdraw button for withdrawing money from the account.
        JButton withdrawButton = new JButton("Withdraw");

        withdrawButton.setBounds(10, 175, 250, 30);

        withdrawButton.setHorizontalAlignment(SwingConstants.CENTER);

        withdrawButton.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Adding this class as an ActionListener to handle button clicks.
        withdrawButton.addActionListener(this);

        // Adding the withdraw button to the frame.
        add(withdrawButton);

        // Transfer button for transferring money to another user's account.
        JButton transferButton = new JButton("Transfer");

        transferButton.setBounds(10, 225, 250, 30);

        transferButton.setHorizontalAlignment(SwingConstants.CENTER);

        transferButton.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Adding this class as an ActionListener to handle button clicks.
        transferButton.addActionListener(this);

        // Adding the transfer button to the frame.
        add(transferButton);

        // Transaction History button that allows the user to view their past transactions.
        JButton transactionHistoryButton = new JButton("Transaction History");

        transactionHistoryButton.setBounds(10, 275, 250, 30);

        transactionHistoryButton.setHorizontalAlignment(SwingConstants.CENTER);

        transactionHistoryButton.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Adding this class as an ActionListener to handle button clicks.
        transactionHistoryButton.addActionListener(this);

        // Adding the transaction history button to the frame.
        add(transactionHistoryButton);

        // Logout button for logging out of the application. It triggers a logout and closes this window.
        JButton logoutButton = new JButton("Logout");

        logoutButton.setBounds(600, 500, 125, 30);

        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Set font for the logout button.
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 18));

        // Adding this class as an ActionListener to handle button clicks.
        logoutButton.addActionListener(this);

        // Adding the logout button to the frame.
        add(logoutButton);

        // JTextArea to display a welcome message and chat-like interaction for the user.
        JTextArea chatArea = new JTextArea();


        chatArea.setSize(500, 150);

        chatArea.setLocation(10, 325);

        // Prevent user from typing directly into the chat area.
        chatArea.setEditable(false);

        chatArea.setVisible(true);

        //makes it to wear you can scroll if the text gets large
        JScrollPane Chat =new JScrollPane(chatArea);

        //give the caht area a scrollbar to look through the chat
        Chat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //sets the chat's location
        Chat.setBounds(10, 325, 500, 150); // Set size and position here


        // Enable line wrapping to ensure text doesn't overflow the area.
        chatArea.setLineWrap(true);

        // Wrap words instead of breaking in the middle of them.
        chatArea.setWrapStyleWord(true);

        // Add a border around the chat area.
        chatArea.setBorder(BorderFactory.createLineBorder(Color.black));

        chatArea.append("ChatBot: Hello " + user.getUsername() + " I am your personal financing assistant, ask me anything you need help with\n");

        // Adding the chat area to the frame.
        add(Chat);

        // JTextField where the user can type messages for the chat interaction.
        JTextField chatField = new JTextField();

        chatField.setSize(425, 30);

        chatField.setLocation(10, 475);

        chatField.setVisible(true);

        chatField.setBorder(BorderFactory.createLineBorder(Color.black));

        // Set the background color to white.
        chatField.setBackground(Color.white);

        // Adding the text field to the frame.
        add(chatField);

        // Send button for sending the typed message in chatField to chatArea.
        JButton sendButton = new JButton("Send");

        sendButton.setBounds(435, 475, 75, 30);

        // Center-align the button's text.
        sendButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Set font size for the button.
        sendButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        sendButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                // When Send is clicked, append the message from the chatField to the chatArea.
                String gtext = chatField.getText();


                // puts user info in chat field
                chatArea.append(user.getUsername() + ": " + gtext + "\n");

                chatArea.append("ChatBot: " + ChatBot.Chatbot(gtext) + "\n");

                // Clear the chat input field after sending the message.
                chatField.setText("");
            }
        });
        // Adding the send button to the frame.
        add(sendButton);

        //adding an image to add a little bit of color to the GUI
        JLabel TSA_image = new JLabel(loadImage("src/lib/AR_TSA_Logo.png"));

        TSA_image.setBounds(590, 345, 147, 107);

        add(TSA_image);

        //making a label to put on top of the community chat
        JLabel comunityText = new JLabel("Comunity Text: Post how the app has helped the environment!");

        comunityText.setHorizontalAlignment(SwingConstants.LEFT);

        comunityText.setFont(new Font("Dialog", Font.PLAIN, 12));

        comunityText.setBounds(275, 75, 500, 25);

        add(comunityText);

        //lets add a recycling symbol to get a splash of color
        JLabel Recycle_image = new JLabel(loadImage("src/lib/Recycle_Symbol.png"));

        Recycle_image.setBounds(750, 65, 35, 35);

        add(Recycle_image);

        // JTextArea to display a welcome message and chat-like interaction for the user.
        JTextArea communityChat = new JTextArea();

        communityChat.setSize(500, 150);

        communityChat.setLocation(275, 100);

        // Prevent user from typing directly into the chat area.
        communityChat.setEditable(false);

        communityChat.setVisible(true);

        //makes it to wear you can scroll if the text gets large
        JScrollPane community =new JScrollPane(communityChat);

        //give the chat area a scrollbar to look through the chat
        community.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //sets the chat's location
        community.setBounds(275, 100, 500, 150); // Set size and position here


        // Enable line wrapping to ensure text doesn't overflow the area.
        communityChat.setLineWrap(true);

        // Wrap words instead of breaking in the middle of them.
        communityChat.setWrapStyleWord(true);

        // Add a border around the chat area.
        communityChat.setBorder(BorderFactory.createLineBorder(Color.black));

        // Adding the chat area to the frame.
        add(community);

        // JTextField where the user can type messages for the chat interaction.
        JTextField communityField = new JTextField();

        communityField.setSize(425, 30);

        communityField.setLocation(275, 250);

        communityField.setVisible(true);

        communityField.setBorder(BorderFactory.createLineBorder(Color.black));

        // Set the background color to white.
        communityField.setBackground(Color.white);

        // Adding the text field to the frame.
        add(communityField);

        //this arraylist is storing all the past chats
        ArrayList<String> pastChats = MyJDBC.getpastChats();

        //this for loop is adding all the past chats to the chat area
        for(int i = 0; i < pastChats.size(); i++)
        {
            communityChat.append(pastChats.get(i) + "\n");
        }


        // Send button for sending the typed message in chatField to chatArea.
        JButton communitySend = new JButton("Send");

        communitySend.setBounds(700, 250, 75, 30);

        // Center-align the button's text.
        communitySend.setHorizontalAlignment(SwingConstants.CENTER);

        // Set font size for the button.
        communitySend.setFont(new Font("Dialog", Font.PLAIN, 16));

        communitySend.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                // When Send is clicked, append the message from the chatField to the chatArea.
                String gtext = user.getUsername() + ": " + communityField.getText() + "\n";


                // puts user info in chat field
                communityChat.append(gtext);

                // Clear the chat input field after sending the message.
                communityField.setText("");

                MyJDBC.addChatToDatabase(gtext);
            }
        });
        // Adding the send button to the frame.
        add(communitySend);
    }

    // Event handler for all the buttons in the GUI.
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Get the action command (button label) of the clicked button.
        String buttonPressed = e.getActionCommand();

        // If the "Logout" button is clicked, show the login screen and dispose of this window.
        if(buttonPressed.equals("Logout"))
        {
            // Create a new LoginGui window and make it visible.
            new LoginGui().setVisible(true);

            // Close the current BankingAppGui window.
            dispose();
            return;
        }

        // Create a new BankingAppDialog window for performing actions like deposit, withdraw, etc.
        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);

        // Set the dialog title to the name of the button clicked.
        bankingAppDialog.setTitle(buttonPressed);

        // Handle different button presses for transactions and history.
        if(buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
                || buttonPressed.equalsIgnoreCase("Transfer"))
        {
            // Close this window on dialog close.
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            // Add the current balance to the dialog.
            bankingAppDialog.addCurrentBalanceAmount();

            // Add an action button (Deposit/Withdraw/Transfer).
            bankingAppDialog.addActionButton(buttonPressed);

            // Add a field for the user to enter a memo (optional note for the transaction).
            bankingAppDialog.addMemo();

            // If the Transfer button was clicked, add a field to specify the recipient of the transfer.
            if(buttonPressed.equalsIgnoreCase("Transfer"))
            {
                // Add the user field for selecting a recipient.
                bankingAppDialog.addUserField();
            }
        }
        else if(buttonPressed.equalsIgnoreCase("Transaction History"))
        {
            // If Transaction History was clicked, add the necessary components to view past transactions.
            bankingAppDialog.addPastTransactionsComponents();
        }

        // Display the dialog to perform the action.
        bankingAppDialog.setVisible(true);
    }
    //this class will allow us to put images onto the GUI
    private ImageIcon loadImage(String path)
    {
        try{
            //putting the image into a buffered image
            BufferedImage img = ImageIO.read(new File(path));

            return new ImageIcon(img);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
