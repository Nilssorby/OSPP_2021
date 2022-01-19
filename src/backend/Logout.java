package backend;

import serverClient.*;

public class Logout {
    public Logout(Character character, Client client){
        client.send(character);
        client.destroyFirst();
    }    
}
