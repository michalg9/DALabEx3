package main.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DA_BYZ_Main{
	public static String getIp() {

		try {
			InetAddress IP = InetAddress.getLocalHost();
			String ipString = IP.getHostAddress();
			System.out.println("IP of my system is := " + ipString);
			return ipString;
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	public static List<String> getProcessListFromProperties() {

		String neighboursString = getPropertyByName("processes");
		String[] neighbourArray = neighboursString.split(";");
		List<String> neighbourList = Arrays.asList(neighbourArray);
 
    	return neighbourList;
	}

	public static String getPropertyByName(String name) {
		Properties prop = new Properties();

		try {
			// load a properties file
			prop.load(new FileInputStream("config.properties"));

			// get the property value and print it out

			String result = prop.getProperty(name);

			return result;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static List<String> removeFromTheList(List<String> list, String element) {

		int indexToRemove = list.indexOf(element);
		ArrayList<String> filteredList = new ArrayList<String>();
	    for (int i = 0; i < list.size(); i++) {
	    	if (i != indexToRemove) {
	    		filteredList.add(list.get(i));
	        }  
		}

	    return filteredList;

	}

	public static void main(String[] args) {
		int id = Integer.parseInt(args[0]);
		int portNum = Integer.parseInt(args[1]);
		try {
			LocateRegistry.createRegistry(portNum);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ipAddress = getIp();
		List<String> processList = getProcessListFromProperties();
		String processName = getPropertyByName("processName");
		String bindName = "/" + processName + id;
		String currentProcessName = "//" + ipAddress + bindName;	
		System.out.println("Current process name is " + currentProcessName);
		
	}
}