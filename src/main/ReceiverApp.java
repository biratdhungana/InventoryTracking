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
	
	public void receiveLocationData() throws Exception {
		
		  System.out.println("Server  ready to receive tag location data");
		  
          	  while (true){
			      System.out.println("while again");
		              ServerSocket sersock = new ServerSocket(6001);
			      Socket sock = sersock.accept();
			      System.out.println("Socket = " + sersock);
			      System.out.println("Sock = " + sock);
		              InputStream istream = sock.getInputStream();
			      InputStreamReader isr = new InputStreamReader(istream);
			      BufferedReader receiveRead = new BufferedReader(isr);
				sersock.close(); 
			      String locationUpdate; 
			      Thread.sleep(1000);

			      while((locationUpdate = receiveRead.readLine()) != null) //!= null && locationUpdate != "null")   
			      {
				 
				 	System.out.println(locationUpdate);

					if(locationUpdate.contains("x")) {
			         	xUpdate = locationUpdate.substring(locationUpdate.indexOf("x")+2, locationUpdate.indexOf(","));
			         	System.out.println("xUpdate = " + xUpdate);
			         	yUpdate = locationUpdate.substring(locationUpdate.indexOf("y")+2, locationUpdate.indexOf(",", locationUpdate.indexOf(",")+1));
			         	System.out.println("yUpdate = " + yUpdate);
			         	zUpdate = locationUpdate.substring(locationUpdate.indexOf("z")+2, locationUpdate.lastIndexOf(","));
			         	System.out.println("zUpdate = " + zUpdate);
					}
					else{
						break;
					}
					
			         
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
