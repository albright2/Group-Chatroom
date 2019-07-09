//Created by Jeremiah Albright
//Server for groupchat

package chatDemo;

//Java implementation of Server side 

//It contains two classes : Server and ClientHandler 
//Save file as Server.java 
import java.io.*; 

import java.util.*; 
import java.net.*;
import java.text.SimpleDateFormat; 

//Server class 
public class Server 
{ 

	// Vector to store active clients 
	static Vector<ClientHandler> ar = new Vector<>(); 
	
	// counter for clients 
	static int i = 0; 

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(12345); //this server socket
		
		
		Socket s; //a client socket
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			// Accept the incoming request 
			s = ss.accept();  //a socket accepts server socket, which in turn waits for that socket to come
			
			System.out.println("New client request received : " + s); 
			
			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
			System.out.println("Creating a new handler for this client..."); 

			// Create a new handler object for handling this request. 
			
			
			 //create mtch =                       s is the socket for client,  # of client connected, inputstream, output                
			ClientHandler mtch = new ClientHandler(s                         ,  "client" + i         ,  dis      ,  dos); 

			// Create a new Thread with this object. 
			Thread t = new Thread(mtch);  //a whole thread to handle this socket through object mtch
			
			System.out.println("Adding this client"+i+" to active client list"); 

			// add this client to active clients list 
			ar.add(mtch);  //added to vector

			// start the thread. 
			t.start(); //start this thread for this client

			// increment i for new client. 
			// i is used for naming only, and can be replaced 
			// by any naming scheme 
			i++; 

		} 
	} 
} 

//ClientHandler class 
class ClientHandler implements Runnable 
{ 
	Scanner scn = new Scanner(System.in); 
	private String name; 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	Socket s; 
	boolean isloggedin; 
	
	// constructor 
	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 
		this.isloggedin=true; 
	} 

	
	//method for sending a single message
	//have to address the recipient when sending messages
	

	private void toOneClient(String received) throws IOException{
		
		
		

		// break the string into message and recipient part with tokenizer
		
		StringTokenizer st = new StringTokenizer(received, "#");
		
		
		// 1st string is the message
		String MsgToSend = st.nextToken(); 
		//2nd string is the contact
		String recipient = st.nextToken(); 

		// search for the recipient in the connected devices list. 
		// ar is the vector storing client of active users 
		for (ClientHandler mc : Server.ar) 
		{ 
			// if the recipient is found, write on its 
			// output stream 
			System.out.println(mc.name);
			System.out.println(recipient);

			if (mc.name.equals(recipient) && mc.isloggedin) 
			{ 
				mc.dos.writeUTF(MsgToSend); 
				break; 
			} 

	
		}
	
	
	}
	

	@Override
	public void run() { 
		String received; 
		boolean connected = true;
		
		
		
		
		//server will collect usernames incase of disconnection
		try {
			
			//tell other clients that someone has connected

			received = dis.readUTF();
			StringTokenizer st = new StringTokenizer(received, "has");
			// 1st string is username
			String user = st.nextToken(); 
			this.name=user;
			toAllClients("> "+received);

			//tell this client how many other clients are connected
			
			int a = Server.ar.size()-1;
			String amount = Integer.toString(a) +" other users are connected #"+this.name;
			System.out.println(amount);
			toOneClient(amount);
			
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		
		
		
		
		while (connected) 
		{ 
			try
			{ 
			
				//received contains the message sent from client using datainputstream
				received = dis.readUTF(); 
				toAllClients(received);
				
				
			} catch (IOException e) { 
				System.out.println(this.name+" has disconnected, error bellow:\n");
				try {
				//	toAllClients("> "+this.name +" has left");
					toAllClients("logout");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				connected = false;
				e.printStackTrace(); 
			} 
			
		} 
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	}


//currently working on logging out successfully 
	private void toAllClients(String recipient) throws IOException {

		if(recipient.equals("logout")){ 
			this.isloggedin=false; 
			this.s.close(); 
			Server.ar.remove(this);
			//above works, but need to tell users that they have left, aliong with deleting one from server
			//Server.ar.
			recipient = this.name+ " has left";
		} 
		
		for (ClientHandler mc : Server.ar) 
		{ 
			
			

		if(!mc.name.equalsIgnoreCase(this.name)&& mc.isloggedin) //send message to other clients but this client
		{	mc.dos.writeUTF(recipient);
		
		}	 
	
	}
	
	} 
} 
