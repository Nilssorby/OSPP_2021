package serverClient;

import java.io.Serializable;

public class User implements Serializable{
    private String userName;
    private boolean connected;
    
    public User(){
    	this.userName = null;
		this.connected = true;
    }
    
    public User(String userName){
    	this.userName = userName;
		this.connected = true;
    }

    public String getUserName(){
    	return this.userName;
    }

    public boolean isConnected(){
    	return this.connected;
    }

    public void disconnect(){
    	this.connected = false;
    }

    public String toString(){
	if(userName == null){
	    return ("Anonymous: ");
	} else {
	    return (userName +  ": ");
	}
    }
}