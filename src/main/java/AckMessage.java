package main.java;


public class AckMessage{
	private int count;
	private String sender;
	private String receiver;
	
	public AckMessage(int count, String sender, String receiver){
		this.setCount(count);
		this.setSender(sender);
		this.setReceiver(receiver);

	}
	
	public AckMessage() {
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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