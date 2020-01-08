package main;


import java.io.*;
import java.net.*;

public class ReceiverApp {

	public ReceiverApp() {

	}
	
	public void receive() throws Exception {
		
            while (true){
	      ServerSocket sersock = new ServerSocket(6001);
	      System.out.println("Server  ready to receive data from App");
	      Socket sock = sersock.accept( );                          
	      InputStream istream = sock.getInputStream();
	      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
	 
	      String receiveMessage;               
	        //receiveMessage = receiveRead.readLine();
	        if((receiveMessage = receiveRead.readLine()) != null)  
	        {
	           System.out.println("Data received from App: " + receiveMessage);         
		   sersock.close();
	        }         
	      
	     }               
	}
}
