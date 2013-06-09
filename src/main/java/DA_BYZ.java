package main.java;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DA_BYZ extends UnicastRemoteObject implements DA_BYZ_RMI{
	//private static Log log = LogFactory.getLog(DA_BYZ.class);
	private int pId;
	private String currProcess;
	private List<String> processList;
	private int noOfProcesses;
	private List<String> faultyProcessList;
	private int noOfFaulty;
	private int rounds;
	private boolean isFaulty = false;
	private boolean isGeneral = false;
	private Map<List<Integer>, Message> recvdMsgs = new HashMap<List<Integer>,Message>();
	
	protected DA_BYZ(int pId, String currentProcessName, List<String> processList, List<String> faultyProcesses, boolean faulty) throws RemoteException {
		super();
		this.pId = pId;
		this.currProcess = currentProcessName;
		this.processList = processList;
		this.noOfProcesses = processList.size();
		this.faultyProcessList = faultyProcesses;
		this.noOfFaulty = faultyProcesses.size();
		this.rounds = faultyProcesses.size() + 1;
		this.isFaulty = faulty;
		// By default General is the first process.
		if (pId == 1){
			this.isGeneral = true;
		}
	}
	
	public void sendAckMsg(Message m) throws MalformedURLException, RemoteException, NotBoundException{
		AckMessage aMsg = new AckMessage();
		aMsg.setCount(m.getCount());
		aMsg.setReceiver(m.getSender());
		aMsg.setSender(currProcess);
		
		Remote robj = Naming.lookup(m.getSender());
		
		DA_BYZ_RMI receiver = (DA_BYZ_RMI) robj;
		receiver.recvAckMsg(aMsg);
		
	}
	@Override
	public void recvAckMsg(AckMessage m){
		
	}
	@Override
	public synchronized String receive(Message m) throws RemoteException {
		
		recvdMsgs.put(m.getPath(), m);
		try {
			sendAckMsg(m);
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retrieve sender process' clock
		//VectorClock msgClock= m.getClock();
		// Contents of received message
		//System.out.printf("\n\nReceived message: (%s, %d) ", m.getMsg(), msgClock.get(m.getId()));

		return null;
	}
	
	/**
	 * This function sends messages to all the running remote peer processes.
	 * @param neighbour remote destination process 
	 * @param msg message to be sent to neighbour
	 */
	@Override
	public void sendMsg(String lieutenant, Message msg) {
		try {
			Remote robj = Naming.lookup(lieutenant);
			
			DA_BYZ_RMI processserver = (DA_BYZ_RMI) robj;

			//System.out.printf("\nSend msg:( %s, %d) to %s\n", processID, msg.getClock().get(msg.getId()), neighbour);
			processserver.receive(msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	 /** 
	  * This function broadcasts messages to all the other running lieutenant processes in case of General or broadcasts to other
	  * lieutenants in case of a sender lieutenant
     */	
	public void broadcast() {
		int count = 0;
		for (String destProcess : processList){
			OrderValue order = OrderValue.RETREAT;
			Message msg = new Message(count, order, currProcess, destProcess);
			List<Integer> path = msg.getPath();
			// append process Id to path where allowed
			if (!path.contains(pId)){
				path.add(pId);
				msg.setPath(path);
				sendMsg(destProcess, msg);
			}
			
		}

	}	
}