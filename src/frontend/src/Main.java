package frontend.src;
import javax.swing.*;

import serverClient.*;
import backend.Character;

public class Main extends JFrame {

    public static void main(String[] args) {

        //use this for client/server connection
        
        Client client = new Client();
        client.startConnection("localhost", 1234);
        new LoginScreen(client);

        //use this to bypass client/server
    	
        /*
        Character character = new Character();
        Client client = new Client();
        new MainScreen(character, client);
        */
    }
}