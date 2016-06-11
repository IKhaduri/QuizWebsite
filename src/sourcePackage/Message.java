package sourcePackage;

import java.sql.Timestamp;

public class Message {
	private String sender;
	private String receiver;
	private String message;
	private Timestamp date;
	private boolean seen;
	
	/**
	 * Constructor
	 * @param sender - sender user name
	 * @param receiver - receiver user name
	 * @param message - message text
	 * @param date - delivery date
	 * @param seen - true, if the message is seen
	 */
	public Message(String sender, String receiver, String message, Timestamp date, boolean seen){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.date = date;
		this.seen = seen;
	}
	
	/**
	 * @return sender
	 */
	public String getSender(){
		return sender;
	}
	
	/**
	 * @return receiver
	 */
	public String getReceiver(){
		return receiver;
	}
	
	/**
	 * @return message text
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * @return delivery date
	 */
	public Timestamp getDate(){
		return date;
	}
	
	/**
	 * @return true, if the message is seen
	 */
	public boolean isSeen(){
		return seen;
	}
}
