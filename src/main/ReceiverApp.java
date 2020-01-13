package main;


import java.io.*;
import java.net.*;

public class ReceiverApp {
	
	public String xUpdate;
	public String yUpdate;
	public String zUpdate;
	
	public double xNew;
	public double yNew;
	public double zNew;

	public ReceiverApp() {

	}
	
	public synchronized void receiveLocationData() throws Exception {
		
		  System.out.println("Server  ready to receive tag location data");
		  
          while (true){
		              ServerSocket sersock = new ServerSocket(6001);
		              Socket sock = sersock.accept();                          
		              InputStream istream = sock.getInputStream();
			      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			 
			      String locationUpdate = null;               
			      if((locationUpdate = receiveRead.readLine()) != null)   
			      {
			         System.out.println("Tag Location Update: " + locationUpdate);
			         xUpdate = locationUpdate.substring(locationUpdate.indexOf("x")+2, locationUpdate.indexOf(","));
			         System.out.println("xUpdate = " + xUpdate);
			         yUpdate = locationUpdate.substring(locationUpdate.indexOf("y")+2, locationUpdate.indexOf(",", locationUpdate.indexOf(",")+1));
			         System.out.println("yUpdate = " + yUpdate);
			         zUpdate = locationUpdate.substring(locationUpdate.indexOf("z")+2, locationUpdate.lastIndexOf(","));
			         System.out.println("zUpdate = " + zUpdate);
			         
					 
				 xNew = Double.parseDouble(xUpdate);
				 yNew = Double.parseDouble(yUpdate);
				 zNew = Double.parseDouble(zUpdate);
				 
				 double[] updatedAngles = new double[]{xNew, yNew, zNew};
					 
				 CameraLineOfSight camera = new CameraLineOfSight();
			       	 double[] angles = camera.angles(updatedAngles);
			       	 
				 SendToCamera sendCamera = new SendToCamera();
				 
			       	 try {
						 sendCamera.sendAngles(angles[0], angles[1]);
					 } catch (Exception e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
			      		 
			      } 
	                      sersock.close(); 
	                      Thread.sleep(1000);
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
