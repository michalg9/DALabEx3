package main.java;

public class Message{
	private Integer processId;
	private String sender;
	private String receiver;
	
	public Message(int pId, String sender, String receiver){
		this.processId= pId;
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSender() {
		return sender;
	}

	public Integer getProcessId() {
		return processId;
	}
}