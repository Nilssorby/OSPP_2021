package frontend.src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import serverClient.Client;
import serverClient.Message;
import serverClient.User;
import backend.Character;
import backend.Chat;

import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;

// import javax.swing.*;
import java.awt.*;

public class ChattScreen {
    private JFrame chattWindow = new JFrame("Chatt");
    private JTextArea textArea = new JTextArea(18,30);
    private JTextField chattField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private Color backgroundColor = new Color(0x7678ed);
    private Client client;
    private User user;
    private Thread chatUpdater;

    public ChattScreen(Character character,Client client) {
    	this.user = new User (character.getUsername());
    	this.client = client;
        createChattUI();
        this.chatUpdater = new Thread(new textUpdater(client.getChat(),textArea));
        chatUpdater.start();
    }

    public void createChattUI() {
        chattWindow.setSize(400, 400);
        chattWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //needed for .setBounds with JPanel to work properly
        chattWindow.setLayout(null);
                //set background
        chattWindow.getContentPane().setBackground(this.backgroundColor);

        centerWindow();

        textArea.setEditable(false);
        
        textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textArea.setLineWrap(true);
        
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBounds(5, 5, 375, 300);
        textAreaPanel.add(new JScrollPane(textArea));
        this.chattWindow.getContentPane().add(textAreaPanel);
        
        JPanel chattJPanel = new JPanel();
        chattJPanel.setBackground(this.backgroundColor);
        chattJPanel.setBounds(5, 305, 375, 90);
        chattField.setPreferredSize(new Dimension(300,45));
        chattJPanel.add(this.chattField);
        
        Border loginBorder = new LineBorder(new Color(0xffcb69), 3);
        sendButton.setBorder(loginBorder);
        sendButton.setBackground(new Color(0xf1dca7));
        sendButton.setOpaque(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String msg;
            	if(!(msg = chattField.getText()).equals("")) {
                Message message = new Message(user,msg);
                client.send(message);
                chattField.setText(null);
            	}
            }
        });
        
        // stänger threaden om förnstret stängs ned...
        chattWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            	chatUpdater.interrupt();
            }
        });
        chattJPanel.add(sendButton);
        this.chattWindow.getContentPane().add(chattJPanel);


        this.chattWindow.setVisible(true);

    }

    public void centerWindow() {
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - 400) / 2);
        int y = (int) ((dimension.getHeight() - 400) / 2);
        chattWindow.setLocation(x, y);

    }
    
    private class textUpdater implements Runnable{
    	private Chat chat;
    	private JTextArea textArea;
    	
    	
    	public textUpdater(Chat chat, JTextArea textArea) {
    		this.chat = chat;
    		this.textArea = textArea;
    	}
    	
    	public void run(){
    		try {
    			Message message;
    			BlockingQueue<Message> messages;
    			
				while(!Thread.interrupted()){
					messages = chat.getQueue();
					
					// Borde använda poll istället
					message = messages.take();
					
					textArea.append(message.toString() + "\n");
					
				}
			} catch (InterruptedException e) {
				// Fönster stängdes
			}
    	}
    }
    
    public static void main(String[] args) {
        //new ChattScreen();
    }
}
