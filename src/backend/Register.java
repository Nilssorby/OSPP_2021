package backend;

import serverClient.*;
import frontend.src.*;

public class Register {
    public Register(String userName, String password, Client client) {
        password pw = new password(password);
        
        registerInfo registerInfo = new registerInfo(userName, pw.hashPassword());
        Character registeredCharacter = (Character) client.request(registerInfo);
        // loggedInCharacter.printCharacter();
        if (registeredCharacter != null) {
            new MainScreen(registeredCharacter, client);
        } else {
            new LoginScreen(client, 2);
        }
    }
}
