package serverClient;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

import backend.Character;
import backend.Chat;
import backend.loginInfo;
import backend.registerInfo;
import backend.password;
import backend.serverError;



public class Client {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ServerReader sR;
    private Chat chat = new Chat();
    
    public void startConnection(String ip, int port) {
	try{
	    clientSocket = new Socket(ip, port);
		    
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
	    in = new ObjectInputStream(clientSocket.getInputStream());
	    
	    sR = new ServerReader(clientSocket,out,in);
		    
	    Thread reader = new Thread(sR);
	    reader.start();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    public Chat getChat() {
    	return this.chat;
    }
    
    public void send(Object data) { // TODO: kanske kan skicka en exception om det blir något fel
    	try {
    		
    		out.writeObject(data);
    		out.flush();
    		
    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    	sR.destroyFirstQueueObject();
    }
    
    public Object request(Object data) { // TODO: kanske kan skicka en exception om det blir något fel
    	try {
    		
    		out.writeObject(data);
    		out.flush();
    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return sR.retrieve();
    }
    
    public void destroyFirst() {
    		sR.retrieve();
    }
    
    private class ServerReader implements Runnable{
    private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BlockingQueue<Object> readData;
	
	public ServerReader(Socket clientSocket,ObjectOutputStream out,ObjectInputStream in){
	    this.clientSocket = clientSocket;
	    this.out = out;
	    this.in = in;
	    this.readData = new LinkedBlockingQueue<Object>();
	}
	
	
	// TODO: Borde kolla på in.readObject eller något så att vi kan få rätt data
	// TODO: gör en kö typ eller något...
	public Object retrieve() {
		try {
			
			// Borde använda poll istället
	    	Object data = readData.take();
	    	// TODO: Kanske borde hanteras annorlunda
			if(data.getClass().equals(serverError.class)) {
				return null;
			} else {
				return data;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void destroyFirstQueueObject() {
		try {
			if(readData.size() != 0) {
				readData.take();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//
	
	public void run(){
		
	    try {
			
		Object data = new Object();
		while(!Thread.interrupted()){
			data = in.readObject();
			
			
			if(data.getClass().equals(Message.class)) {
		    	// Syncas med BlockingQueue
				//System.out.println( (Message) data)
				
				chat.addMessage( (Message) data);
				
		    } else if (data.getClass().equals(Character.class)){
		    	readData.add(data);
		    } else if (data.getClass().equals(loginInfo.class)){
		    	readData.add(data);
		    	
		    // TODO: Kanske borde hanteras annorlunda
		    } else if (data.getClass().equals(registerInfo.class)) {
				readData.add(data);
			} else if (data.getClass().equals(serverError.class)){
		    	readData.add(data);
		    } else {
		    	System.out.println("oops...");
		    }
			data = null;
		    
		}
		    
	    } catch (EOFException e) { // TODO: kolla NoSuchElementException...
	    	System.out.println("Server connection lost...");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
    }
    
    public static void main(String[] args) {
    	Client client = new Client();
		client.startConnection("localhost",1234);
		
		Scanner messageBuffer = new Scanner(System.in);
		
		System.out.print("Enter a username: ");
		String username = messageBuffer.nextLine();
		System.out.print("Enter a password: ");
		String passWord = messageBuffer.nextLine();
		
		
		
		password pW = new password(passWord);
		loginInfo user = new loginInfo(username,pW.hashPassword());
		System.out.println(client.request(user));
		
		
		Message msg = new Message(new User(user.getUserName()));
		
		while(true) {
			
			msg.setText(messageBuffer.nextLine());
			client.send(msg);
			
		}
    }
    
}