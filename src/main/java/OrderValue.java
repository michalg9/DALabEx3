package main.java;

public enum OrderValue{

		RETREAT,
		ATTACK;

	public OrderValue getDefault(){
		return OrderValue.RETREAT;
	}
	
}