import db_objs.User;
import guis.BankingAppGui;
import guis.LoginGui;
import guis.RegisterGui;

import javax.swing.*;
import java.math.BigDecimal;
//This class will launch our whole app, beginning with the login screen
public class AppLauncher
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new LoginGui().setVisible(true);
            }

        });
    }
}
