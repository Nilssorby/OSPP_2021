package serverClient.tests;
import java.io.*;
import java.net.*;
import java.util.*;

public class testClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    // private connection ; kolla om main thread har disconnectat
    
    public void startConnection(String ip, int port) {
	try{
	    clientSocket = new Socket(ip, port);
	    //
	    out = new PrintWriter(clientSocket.getOutputStream(),true);
	    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    //
	    
	    ServerWriter sW = new ServerWriter(out);
	    Thread writer = new Thread(sW);
	    writer.start();

	    String serverMsg;
	    while((serverMsg = in.readLine()) != null){
		System.out.println(serverMsg);
	    }

	    writer.interrupt();
	}
	catch(IOException e){
	    e.printStackTrace();
	    System.exit(0);
	}
    }

    private class ServerWriter implements Runnable{
	private PrintWriter out;
	
	public ServerWriter(PrintWriter out){
	    this.out = out;
	}
	
	public void run(){
	    try{
		BufferedReader reader = new BufferedReader(new FileReader("longMessage10k.txt"));

		String line = reader.readLine();
		String key = "";
	    
		while ((line = reader.readLine()) != null) {
		    key += line;
		}
		reader.close();
		out.println(key);
		
	    } catch (Exception e) {
		
	    }
	}
    }
    
    public static void main(String[] args) {        
	testClient client = new testClient();
	client.startConnection("localhost",1234);
    }
    
}
