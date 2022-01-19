package backend;

import java.util.*;
import serverClient.*;
import database.databaseHandler;
import frontend.src.*;

public class Login {

    public Login(String userName, String password, Client client) {
        password pw = new password(password);
        
        loginInfo loginInfo = new loginInfo(userName, pw.hashPassword());
        Character loggedInCharacter = (Character) client.request(loginInfo);
        // loggedInCharacter.printCharacter();
        if (loggedInCharacter != null) {
            new MainScreen(loggedInCharacter, client);
        } else {
            new LoginScreen(client, 1);
        }
    }

}
