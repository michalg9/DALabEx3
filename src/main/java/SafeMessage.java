package main.java;

import java.io.Serializable;


public class SafeMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender;
	private String receiver;
	
	public SafeMessage(int count, String sender, String receiver){
		this.setSender(sender);
		this.setReceiver(receiver);

	}
	
	public SafeMessage() {
		// TODO Auto-generated constructor stub
	}



	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}