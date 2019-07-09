//Created by Jeremiah Albright
//login information required before using client class
package chatDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;


public class login {

private String username;
JFrame frame;
JButton send;
JTextField tf;
private boolean ended;

login() {
  		username ="";
  		frame= new JFrame("Login");
  		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(400, 400);
         JPanel panel = new JPanel(); // the panel is not visible in output
         JLabel label = new JLabel("Enter Username");
        tf = new JTextField(10); // accepts upto 10 characters

          send = new JButton("Enter");
         panel.add(label); 
         panel.add(tf); 

         panel.add(send); 

         frame.getContentPane().add(BorderLayout.SOUTH, panel);
         frame.setVisible(true);

         ended =false;
       
	  //sending messages into chatroom
	   send.addActionListener(new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent evt) {
	        	
	        	username = tf.getText()+" ";
	        	//only the first token will be the username
	        	StringTokenizer a = new StringTokenizer(username," ");
	        
	        	username = a.nextToken();
	        	tf.setText("");
	        	
	        	endit();
	        	frame.setVisible(false);
	        	System.out.println("ok");

	          
	        }
	      });  	
  	
	   
  	
}
  	
public String username() {
	
	return username;
}

public void end() {
	
	
	frame.setVisible(false);
	
	
}

public boolean done() {
	return ended;
}
public void endit() {this.ended =true;}


}

  
  
  
  
  
  
  
  