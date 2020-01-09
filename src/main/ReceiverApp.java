package main;


import java.io.*;
import java.net.*;

public class ReceiverApp {
	
	public String xUpdate;
	public String yUpdate;
	public String zUpdate;

	public ReceiverApp() {

	}
	
	public void receiveLocationData() throws Exception {
		
		  System.out.println("Server  ready to receive tag location data");
		  
          while (true){
		      ServerSocket sersock = new ServerSocket(6001);
		      Socket sock = sersock.accept();                          
		      InputStream istream = sock.getInputStream();
		      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		 
		      String locationUpdate;               
		      if((locationUpdate = receiveRead.readLine()) != null)  
		      {
		         System.out.println("Tag Location Update: " + locationUpdate);
		         xUpdate = locationUpdate.substring(locationUpdate.indexOf("=")+1, locationUpdate.indexOf(","));
			     sersock.close();
		      }         
	      
		      
	     }               
	}
	
	public void receiveApp() throws Exception {
		
		System.out.println("Server  ready to receive data from App");
		
        while (true){
		      ServerSocket sersock = new ServerSocket(8008);
		      Socket sock = sersock.accept();                          
		      InputStream istream = sock.getInputStream();
		      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		 
		      String receiveMessage;               
		      if((receiveMessage = receiveRead.readLine()) != null)  
		      {
		         System.out.println("Data received from App: " + receiveMessage);         
			     sersock.close();
		      }         
	      
	     }               
	}
}
