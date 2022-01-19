package serverClient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// TODO: kanske inte borde extenda user beror på hierakin...
public class Message implements Serializable{
	private User user;
    private String text;

    
    public Message(){
    	this.user = null;
    	this.text = null;
    	
    }
    
    public Message(User user){
    	this.user = user;
    	this.text = null;
    }
    
    public Message(User user, String text){
    	this.user = user;
    	this.text = text;
    }
    
    public String getText(){
	return this.text;
    }

    public String setText(String text){
	this.text = text;
	return this.text;
    }

    public String toString(){
    DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    LocalDateTime now = LocalDateTime.now();
	return ( dtf.format(now) + " | "+ user + " " + text);
    }
}