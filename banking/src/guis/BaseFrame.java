package guis;

import db_objs.User;

import javax.swing.*;
//Every JFrame we will create will extend from this class
public abstract class BaseFrame extends JFrame
{

    //Retrieves which account is using the programe
    protected User user;

    //sets the title to which frame you are in
    public BaseFrame(String title)
    {
        initialize(title);
    }
    public BaseFrame (String title, User user)
    {
        this.user = user;
        initialize(title);
    }
    private void initialize(String title)
    {
        // sets the title
        setTitle(title);

        //makes size 800 x 600 pixels
        setSize(800,600);

        //makes the program exit upon pressing x
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //allows you to place components where you want
        setLayout(null);

        //makes it to where you can not resize the base frame
        setResizable(false);

        //centers the JFrame to your screen
        setLocationRelativeTo(null);

        //adds whatever GUI components you want in the currennt JFrame
        addGuiComponents();
    }
    protected abstract void addGuiComponents();
}
