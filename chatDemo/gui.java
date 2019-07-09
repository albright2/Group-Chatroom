package chatDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class gui {
	
	//String p is for this user to send message to server
	//String user is this users name from login page
	private String p,user;
	//boolean press determines if user has sent their message to the server,
	//client class will use this to determine when to send the message to server.
	private boolean press;
	 
	private	 Hashtable<Integer, String> msg;
	private JTextArea ta;
	private JFrame frame;
	private JPanel panel ;// the panel is not visible in output
	private JLabel label ;
	private JTextField tf; 
	private JButton send ;
	private JButton reset ;
	private String date;
	 public gui() {
		p =date="";
		press = false;
	
		
		//setup login and get this users name
		loginUser();
		run();
		
	}
   
    
    
    
    public  void setPress() {
    	press = false;
    	p = "";
      // 	System.out.println(press);

    }
    
   
    public  boolean  getPress() {
    	return press;
    }
    
   
    
    private void loginUser() {
    	
    	
    	login log = new login();
    	//this while loop will wait for user to finish their login, before setting up the chatframe
    	while(log.frame.isVisible() ){user= log.username();}
    	
    	
    	
    	
    }
    private  void run() {


    	
    	
    
        msg = new Hashtable<Integer, String>(); //history of messages stored
       
        
        //Creating the Frame
        frame = new JFrame("Chat Room: "+user);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        
        frame.setSize(800, 800);
        
     // extra feature, allow user to send pictures/files, audio recording, etc
        
/*		
 * 		
        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE"); //change this to add "attachments of jpg, png, gif images
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);*/

        //Creating the panel at bottom and adding components
        panel = new JPanel(); // the panel is not visible in output
        label = new JLabel("Enter Text");
        tf = new JTextField(10); // accepts upto 10 characters
        send = new JButton("Send");
        reset = new JButton("Reset"); //reset this user's chat text area
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
         ta = new JTextArea();
        ta.setEditable(false);
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
      //  frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
   

    	
        
  	  //sending messages into chatroom
  	   send.addActionListener(new ActionListener() {
  		
  	        @Override
  	         public void actionPerformed(ActionEvent evt) {
  	        	
  	        	//hashtable 'msg' keeps history of messages
  	        	msg.put(msg.size()+1, tf.getText());
  	        	//change color of text to black, to indicate message was sent
  	        	ta.setForeground(Color.black);
  	        	
  	        	if(!todaysDate(date)) {
  	        		
  	        		date = new SimpleDateFormat("MM/dd/yyyy\n").format(Calendar.getInstance().getTime());
  	        		p=date;
  	        	}
  	       
  	        	
  	        	
  	        	
  	        	
  	        	
  	        	//timestamp for this user's chat message
  	        	//displayed in textfield
  	        	String timeStamp = new SimpleDateFormat("hh:mm:ss a\n").format(Calendar.getInstance().getTime());
  	        	p = p+timeStamp;
  	        	//message to send to server
 	        	 p =p+"   "+user+"> "+tf.getText();
 	        	 ta.append("   "+p+"\n"); //prints to the chat room "text area"
  	        	
  	        
  	        	//clear user's text section after sending
  	        	tf.setText("");
  	        //notify client to send the message to the server
  	        	press = true; 
  	        	
  	         }
  	      });
    
   
  	  
   	  //clear chatbox
   	   reset.addActionListener(new ActionListener() {
   	        @Override
   	         public void actionPerformed(ActionEvent evt) {
   	        	msg.clear();
   	        	ta.setText("");
   	        	
   	        	
   	         }
   	      });
   	   
   	   
    
	}
   	
   	
    
   	   public  String getMessage() {
   		
   		   return p;
   		   
   	   }
   	   public  String getName() {
   		  
   		   return user;
   		   
   	   }

   	  public void messageReceived(String p) {
   		StringTokenizer st = new StringTokenizer(p, "\n");
		// 1st string is the date sent
		String user = st.nextToken(); 
		this.date = user+"\n";
		ta.setForeground(Color.blue);
		   ta.append(p+"\n");
		   
		 
		   
	   }
   
   	  
   	  //method to determine if message received from server is today's date
   private boolean todaysDate(String newD) {
	   
	   
	   
	   String pattern = "MM/dd/yyyy\n";
	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	   String date = simpleDateFormat.format(new Date());
	   if(date.equals(newD)) {
		 
		   return true;
		   
	   }
	   
	   return false;
   }
   	   

}

	 
    
    
    
    
    
    