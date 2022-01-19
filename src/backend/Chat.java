package backend;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import serverClient.Message;

public class Chat {
	private BlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	// kanske behöver denna backlog
	//private ArrayList<Message> backLog = new ArrayList<Message>(50); //TODO MACRO
	
	public Message readMessage() {
		try {
			return messages.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void addMessage(Message msg) {
		try {
			// TODO fixa så att dessa inte kan blir överflödiga
			messages.put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BlockingQueue<Message> getQueue() {
		return messages;
	}
}
