package main.java;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
	@Override
	public synchronized String receive(Message m) throws RemoteException {

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
	public void sendMessage(String neighbour, Message msg) {
		try {
			Remote robj = Naming.lookup(neighbour);
			
			DA_BYZ_RMI processserver = (DA_BYZ_RMI) robj;

			//System.out.printf("\nSend msg:( %s, %d) to %s\n", processID, msg.getClock().get(msg.getId()), neighbour);
			processserver.receive(msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	 /** 
	  * This function broadcasts messages to all the other running peer processes.
     */	
	public void broadcast() {
		
		if (isGeneral == true){
			for (String destProcess : processList){
				Message msg = new Message(pId, currProcess, destProcess);
			}
		}
		
		}

		Message msg;
		//VectorClock sTimestamp = new VectorClock();
		synchronized (this) {
			//vclock.incrementClock(processID);
			//VectorClock tempVc = VectorClock.copyClock(vclock);
//			int newVal=vclock.incrementClock(processID).get(processID);
//			sTimestamp.put(processID, newVal);
     		//String content = "test message from process " + processID;
			
			//msg = new Message(content, processID, tempVc);
		}
		//Collections.shuffle(neighbourList);
		//System.out.printf("CLOCK incremented by %d before sending the message\n", vclock.get(processID));
		
//		int delayedIndex = -1;
//		for (int i = 0; i < neighbourList.size(); i++) {
//			String neighbour = neighbourList.get(i);
//			if (this.processID.contains("1") && neighbour.contains("ProcessServer3")) {
//				delayedIndex = i;
//			}
//			
//			if (i != delayedIndex) {
//				sendMessage(neighbour, msg);
//			}
//		}
//		if (delayedIndex != -1) {
//			String delayedNeighbour = neighbourList.get(delayedIndex);
//			System.out.printf("\n\nDELAYING MSG FROM %s TO %s\n", this.processID, delayedNeighbour);
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			VectorClock.printClock(msg.getClock());
//			sendMessage(delayedNeighbour, msg);
//		}
		
	}
	
}