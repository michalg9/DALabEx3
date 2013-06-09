package main.java;

import java.rmi.RemoteException;

public interface DA_BYZ_RMI extends java.rmi.Remote{

	String receive(Message m) throws RemoteException;
	
}