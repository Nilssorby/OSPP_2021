package serverClient;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import database.*;
import backend.*;
import backend.Character;

public class Server {
    private ServerSocket serverSocket;
    private ClientOverseer overseer;
    private BlockingQueue<Message> queue;
    private Set<ClientHandler> currUsers; // Does not uppdate after a user leaves however it does in the thread ClientOverseer
    private ReentrantLock mutex;
    private databaseHandler DB;
    
    public void startServer(int port) {
	try{
	    serverSocket = new ServerSocket(port);
	    queue = new LinkedBlockingQueue<Message>();
	    currUsers = new HashSet<>();
	    mutex = new ReentrantLock();
	    overseer = new ClientOverseer(queue,currUsers,mutex);
		    
	    DB = new databaseHandler();
	    DB.connectToDatabase();
	    
	    // För testning....
	    /*
	    password pw = new password("1234");
	    DB.updateCharacter(new Character("John",pw.hashPassword(),5000,21,new Equipment(),new Attributes(),1000000));
	    pw = new password("1234");
	    DB.updateCharacter(new Character("Bobby",pw.hashPassword(),5000,21,new Equipment(),new Attributes(),1000000));
	    */
	    //
	    
	    
	    new Thread(overseer).start();
	    System.out.println("OK!");
		    
	    while(true){
		Socket clientSocket = serverSocket.accept();
				
		System.out.println("Client {" + clientSocket.getPort() + "} connected...");
				
		User user = new User(); // Borde nog deklareras här annars kan den avläsas som null av andra
				
		ClientHandler handler = new ClientHandler(user,clientSocket,queue,overseer,DB);
				
		new Thread(handler).start();
		
	    }
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    private class ClientOverseer implements Runnable{
	private Set<ClientHandler> currUsers;
	private BlockingQueue<Message> queue;
	private ReentrantLock mutex;
	
	public ClientOverseer(BlockingQueue<Message> queue,Set<ClientHandler> currUsers,ReentrantLock mutex){
	    this.currUsers = currUsers;
	    this.queue = queue;
	    this.mutex = mutex;
	}
	
	
	public boolean userExists(User user){
		// --------- CRITICAL SECTION ---------
	    try {
		mutex.lock();
		Iterator<ClientHandler> clientIterator = currUsers.iterator();
		ClientHandler client;
		while(clientIterator.hasNext()){
			client = clientIterator.next();
			if(client.getUser().getUserName().equals(user.getUserName())) {
				return true;
			} else {
				return false;
			}
		}
	    } finally {
	    	mutex.unlock();
	    }
	    // --------- CRITICAL SECTION ---------
		return false;
	}
	
	
	public void addUser(ClientHandler newClient) {
	    // --------- CRITICAL SECTION ---------
	    try {
		mutex.lock();
		if(!userExists(newClient.getUser())) {
			this.currUsers.add(newClient);
		} else {
			throw new BadLoginException("User already logged in");
		}
	    } finally {
	    	mutex.unlock();
	    }
	    // --------- CRITICAL SECTION ---------
	}

	public void removeUser(ClientHandler newClient){
	    // --------- CRITICAL SECTION ---------
	    try {
	    	mutex.lock();
	    	this.currUsers.remove(newClient);
	    } finally {
	    	mutex.unlock();
	    }
	    // --------- CRITICAL SECTION ---------
	}
	
	public Set<ClientHandler> getUsers(){
	    	return this.currUsers;
	}

	public BlockingQueue<Message> getQueue(){
	    return this.queue;
	}
	
	public void run(){
	    try{
		while(true){
		    // --------- CRITICAL SECTION ---------
		    Message msg = queue.take(); // TODO: Use this instead of while(true)?? och null check???
		    try {
			mutex.lock();
			
			// TODO: borde ta bort denna...
			//currUsers.removeIf(u -> (u.getUser().isConnected() == false));
			//
			
			Iterator<ClientHandler> clientIterator = currUsers.iterator();
			ClientHandler client;
			while(clientIterator.hasNext()){
				
				// TODO: Fixa så att användare som hämntar från data basen ändå får meddelandet
				
				client = clientIterator.next();
				
				if(!client.getUser().isConnected()) {
					clientIterator.remove();
				}
				
			    if (client.getUser().isConnected() && !client.isLocked()){
			    	
			    	client.sendToClient(msg);
				
			    } 
			}
			//System.out.println(currUsers);
		    } finally {
			mutex.unlock();
		    }
		    // --------- CRITICAL SECTION ---------
		}
	    }catch(Exception e){
		e.printStackTrace();
	    }
	}
    }
    
    private class ClientHandler implements Runnable{
	private User user;
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BlockingQueue<Message> overseerOut;
	private ClientOverseer overseer;
	private ReentrantLock writerLock;
	private databaseHandler DB;
	
	public ClientHandler(User user, Socket clientSocket , BlockingQueue<Message> overseerBuffer, ClientOverseer overseer, databaseHandler DB){
	    this.user = user;
	    this.clientSocket = clientSocket;
	    this.overseerOut = overseerBuffer;
	    this.overseer = overseer;
	    this.writerLock = new ReentrantLock();
	    this.DB = DB;
	}
	
	public Socket getClient(){
	    return this.clientSocket;
	}

	public User getUser(){
	    return this.user;
	}
	
	public ObjectInputStream getInput() {
	    return this.in;
	}
	
	// TODO: Border synca streams en så att vi kan skriva till data bas och meddelanden
	public ObjectOutputStream getOutput() {
	    return this.out;
	}
	
	public boolean isLocked() {
	    return this.writerLock.isLocked();
	}
	//
	
	public void sendToClient(Object data) {
		try {
			writerLock.lock();
			this.out.writeObject(data);
			this.out.flush();
			writerLock.unlock();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
	    try{
	    
	    // Borde fixa EOFException för dessa...
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
		//
		
		try{
		
		// --------- CRITICAL SECTION ---------
		
		// TODO: Fixa så att dne stämmer med class hierarkin
		// TODO: Synca DB write/read
			
		Object data = new Object();
		    while(true){
		    data = in.readObject();
		    
		    if(data.getClass().equals(Message.class)) {
		    	// Syncas med BlockingQueue
		    	
		    	overseerOut.put( (Message) data);
		    	
		    } else if (data.getClass().equals(Character.class)){
		    	DB.updateCharacter((Character) data);
		    	//
		    	sendToClient( (Character) data);
		    	//
		    	user.disconnect();
		    	overseer.removeUser(this);
		    	
		    } else if (data.getClass().equals(loginInfo.class)){
		    	data = DB.loginCharacter(( (loginInfo) data).getUserName(),( (loginInfo) data).getPassword());
		    	
		    	try {
		    		
		    	if(data != null) {
		    		user = new User(( (Character) data).getUsername());
		    	} else {
		    		throw new BadLoginException("Wrong details");
		    	}
		    	
		    	
			    overseer.addUser(this);
			    //
			    sendToClient( (Character) data);
			    //
		    	} catch (BadLoginException e) {
		    		// Meddela till användaren
		    		sendToClient( new serverError(e.getMessage()) );
		    	}
		    	
		    } else if (data.getClass().equals(registerInfo.class)) {
				data = DB.registerCharacter(((registerInfo) data).getUserName(), ((registerInfo) data).getPassword());
				try {
		    		
					if(data != null) {
						user = new User(( (Character) data).getUsername());
					} else {
						throw new BadLoginException("Username already taken");
					}
					overseer.addUser(this);
					//
					sendToClient( (Character) data);
					//
					} catch (BadLoginException e) {
						// Meddela till användaren
						sendToClient( new serverError(e.getMessage()) );
		    	}
			} else {
		    	System.out.println("oops...");
		    }
		    }
		    
		    // Har inget val readObject retunerar ej null
		    /*
		    System.out.println("Client {" +  clientSocket.getPort() + "} disconnected...");
			    
		    user.disconnect();
		    in.close();
		    out.close();
		    clientSocket.close();
			*/
		} catch (SocketException | EOFException e){
		    System.out.println("Client {" +  clientSocket.getPort() + "} disconnected...");
		    user.disconnect();
		    overseer.removeUser(this);
		    in.close();
		    out.close();
		    clientSocket.close();
		    
		}
		// --------- CRITICAL SECTION ---------
		
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(1234);
	
    }
}


/*
//
try{
Thread.sleep(2000);
}catch(InterruptedException e){
System.out.println(e);
}
//
*/