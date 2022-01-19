package serverClient.tests;

import java.io.*;
import java.net.*;
import backend.Character;
import serverClient.*;

public class bashTestClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    // TODO: readData borde nog inte vara av class Object
    
    public void startConnection(String ip, int port) {
	try{
	    clientSocket = new Socket(ip, port);
		    
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
	    in = new ObjectInputStream(clientSocket.getInputStream());
	    
	    ServerReader sR = new ServerReader(clientSocket,out,in);
		    
	    Thread reader = new Thread(sR);
	    reader.start();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    /*
    public void logIn(String username, String password) {
    	
    }
    
    public void logOut() {
    	
    }
    
    public void setUser(User user) {
    	
    }
    
    public User getUser() {
    	
    }
    */
    
    public void request(Object data) { // TODO: kanske kan skicka en exception om det blir något fel
    	try {
    		
    		out.writeObject(data);
    		out.flush();
    		out.reset();
    		
    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private class ServerReader implements Runnable{
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public ServerReader(Socket clientSocket,ObjectOutputStream out,ObjectInputStream in){
	    this.clientSocket = clientSocket;
	    this.out = out;
	    this.in = in;
	}
	
	public void run(){
		
	    try {
			
		Object data = new Object();
		while(true){
			data = in.readObject();
			if(data.getClass().equals(Message.class)) {
				
		    } else if (data.getClass().equals(Character.class)){
		    	
		    } else {
		    	
		    }
			
		    
		}
		    
	    } catch (EOFException e) { // TODO: kolla NoSuchElementException...
	    	System.out.println("Server connection lost...");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
    }
    
    public static void main(String[] args) {
    	bashTestClient client = new bashTestClient();
		client.startConnection("localhost",1234);
		
		
		User user = new User("Carl");
		client.request(user);
		//client.request(new Character());
		
		Message data = new Message(user);
		
		for(int i = 0; i < 20 ; i++) {
			client.request(new Character());
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				System.out.println(e);
			}
			data.setText("" + i);
			client.request(data);
		}
		try{
			Thread.sleep(2000);
		}catch(InterruptedException e){
			System.out.println(e);
		}
		System.exit(0);
		
    }
    
}