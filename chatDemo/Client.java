//Created by Jeremiah Albright
//Client class is for sending/receiving messages from/to server
package chatDemo;



//Java implementation for multithreaded chat client 
//Save file as Client.java 

import java.io.*; 


import java.net.*;


import javax.swing.SwingUtilities; 
public class Client 
{ 

	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
		
		@SuppressWarnings("unused")
			Client cli = new Client(args[0],  Integer.parseInt(args[1]));
	
	}
	
	
	private gui guid;
    private InetAddress ip;
	private Socket s;
	private DataInputStream dis ;
	private DataOutputStream dos;
	
	public Client (String addr, int port) throws IOException {
		
		
	
		guid = new gui();
		
		// getting localhost ip 
		
		
	
					
		 ip = 	InetAddress.getByName(addr);
		
	    int ServerPort = port; 

		// for local machine only, running as client & server
	    ip = InetAddress.getByName(staticIP());
		
		
		
		 s = new Socket(ip, ServerPort); 
		
		// obtaining input and out streams 
		 dis = new DataInputStream(s.getInputStream()); 
		 dos = new DataOutputStream(s.getOutputStream()); 

		
		
		// sendMessage thread 
		Thread greetingMsg = new Thread(new Runnable() 
		{ 
			
			@Override
			public void run() { 
		
			try {
				dos.writeUTF(guid.getName()+" has connected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
				
		}	);
		
		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable() 
		{ 
			
			@Override
			public void run() { 
				boolean connected = true; //if client/server disconnect
				boolean reset = true;
				float currentTime;
				float startTime;
				 currentTime = (long) (System.nanoTime()/(1000000.0f));
				 startTime = currentTime;
				while (connected) { 

				//delaying the time to send message to server, so that the gui will not be affected when typing a message
					 currentTime = (long) (System.nanoTime()/(1000000.0f));
					 
					 
						//if current time surpasses the waiting time, increase wait time 
						if(reset) {
							//testing connection: System.out.println("current Time: "+currentTime+"finish time: "+startTime+(currentTime>startTime));

							startTime = currentTime+50;
							reset = false;
							
						}
						
					 
					 
					 //after waiting period has passed, update the  gui and send the message
					if(currentTime>startTime) {

					SwingUtilities.invokeLater(//allows you to make a thread that will update the GUI
							new Runnable(){
								@Override
								public void run() {
							

									if(guid.getPress()) {
									 System.out.println("message was sent!");
									 String  msg = guid.getMessage();
										try { 
											// write on the output stream 
											
											dos.writeUTF(msg); 
										
											guid.setPress();
										
										} catch (IOException e) { 
										System.out.println("error: couldnt send message to server");
											e.printStackTrace(); 
									
										}
										
									} 
								}

							
							}
							);
					//boolean to force wait time to to increase
					reset = true;
				
					
				
					} 
					
				
				}
			} 
		}); 
		
		// readMessage thread, will read the message sent from the server 
		Thread readMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 
				boolean connect = true;
				while (connect) { 
					try { 
						// read the message sent to this client 
						String msg = dis.readUTF(); 
						
						guid.messageReceived(msg);
					} catch (IOException e) { 
						 System.out.println("disconnected from server, error bellow: \n");
						 
						e.printStackTrace(); 
						connect = false;
					} 
				} 
			} 
		}); 
		
		greetingMsg.start();
		sendMessage.start(); 
		readMessage.start(); 

	}

//method to use this pc ip address
	private String staticIP() throws UnknownHostException {

		
		 InetAddress.getLocalHost().getHostAddress();
		
		return  InetAddress.getLocalHost().getHostAddress();
	}

	
} 
