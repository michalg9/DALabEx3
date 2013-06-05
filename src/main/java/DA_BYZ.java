package main.java;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DA_BYZ extends UnicastRemoteObject implements DA_BYZ_RMI{
	private static Log log = LogFactory.getLog(DA_BYZ.class);
	
	protected DA_BYZ() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
}