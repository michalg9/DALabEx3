package main.java;

import java.util.LinkedList;
import java.util.List;

public class Message{
	private int count;
	private String sender;
	private String receiver;
	private OrderValue val;
	private List<Integer> path;
	
	public Message(int count, OrderValue order, String sender, String receiver){
		this.setCount(count);
		this.setSender(sender);
		this.setReceiver(receiver);
		this.setVal(order);
		this.setPath(new LinkedList<Integer>());
	}

	public List<Integer> getPath() {
		return path;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public OrderValue getVal() {
		return val;
	}

	public void setVal(OrderValue val) {
		this.val = val;
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