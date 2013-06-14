package main.java;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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
	// Received messages from other lieutenants
	//private Map<List<Integer>, Message> recvdMsgs = new HashMap<List<Integer>,Message>();
	
	private List<Map<List<Integer>, Message>> recvdMsgs = new ArrayList<Map<List<Integer>, Message>>();
	//After sending the message remove from this
	private Map<List<Integer>, Message> sentMsgs = new HashMap<List<Integer>,Message>();
	// Add received messages to evaluation list for later processing
	private Map<List<Integer>, Message> evalMsgs = new HashMap<List<Integer>,Message>();
	private int msgSentCounter=0;
	
	private ProcessState pState;
	
	int roundNumber;
	
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
		this.pState = ProcessState.SAFE;
		this.roundNumber = 0;
		
	}
	
	public void startAlgo(){

		OrderValue order = OrderValue.ATTACK;
		if (pId == 1){
			broadcast(order);
		}


	}
	
	

	
	public synchronized String receive(Message m) throws RemoteException {
		System.out.printf("Receive: ? - %s %s, round number %d\n", currProcess, m, roundNumber);
		
		Map<List<Integer>,Message> receiveMap;
		if (recvdMsgs.size() < roundNumber+1)
		{
			receiveMap = new HashMap<List<Integer>,Message>();
			recvdMsgs.add(receiveMap);
		}
		
		receiveMap = recvdMsgs.get(roundNumber);
		receiveMap.put(m.getPath(), m);
		recvdMsgs.set(roundNumber, receiveMap);
		System.out.printf("Saved\n");
		return null;
	}
	
	/**
	 * This function sends messages to all the running remote peer processes.
	 * @param neighbour remote destination process 
	 * @param msg message to be sent to neighbour
	 */
	public void sendMsg(String lieutenant, Message msg) {
		System.out.printf("Send: %s - %s %s\n",currProcess, lieutenant, msg);
		try {
			Remote robj = Naming.lookup(lieutenant);
			DA_BYZ_RMI processserver = (DA_BYZ_RMI) robj;
			
			//msg.setCount(msgSentCounter++);

			//put the message in sending queue, removed only when ack is received
			//sentMsgs.put(msg.getPath(), msg);
			processserver.receive(msg);
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}
	}
	
	 /** 
	  * This function broadcasts messages to all the other running lieutenant processes in case of General or broadcasts to other
	  * lieutenants in case of a sender lieutenant
     */	
	
	public void oralMessage(int step, String general, List<String> lieutenant) {
		for (String destProcess : processList) {
			
			
		}
		
	}
	public void broadcast(OrderValue order) {
		
		//Message msg;
		while (roundNumber < rounds)
		{
			if (roundNumber == 0)
			{
				for (String destProcess : processList){
					Message	msg = new Message(msgSentCounter, order, currProcess, destProcess);
					List<Integer> path = new LinkedList<Integer>();
					path.add(pId);
					msg.setPath(path);
					sendMsg(destProcess, msg);
				}
			}
			else
			{
				for (Message msg : recvdMsgs.get(roundNumber-1).values())
				{
					List<String> processToSend = new ArrayList<String>();
					
					
					for (String destProcess : processList)
					{
						boolean isOkay = true;
						for (int processNumber: msg.getPath())
						{
							if (destProcess.contains(Integer.toString(processNumber)))
								isOkay = false;
						}
						if (isOkay)
							processToSend.add(destProcess);
					}
					
					for (String destProcess : processToSend){
						Message	newMsg = new Message(msgSentCounter, order, currProcess, destProcess);
						List<Integer> path = new LinkedList<Integer>();
						path.add(pId);
						msg.setPath(path);
						sendMsg(destProcess, newMsg);
					}
					
				}
			}
			System.out.println("a");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			roundNumber++;
		}
		
		
//			else{
//				
//				for (Message m : recvdMsgs.values()) {
//					List<Integer> path = m.getPath();
//					Message	msg = new Message(msgSentCounter, order, currProcess, destProcess);
//					// ? what about the count
//					
//					// append process Id to path where allowed
//					if (!path.contains(pId)){
//						path.add(pId);
//						msg.setPath(path);
//						sendMsg(destProcess, msg);
//					}
//					
//				}
//			}
			
		
		
	}

}