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
	
	public double[] referenceLine;
	public double[] camera1;

	public boolean firstLoop = true;

	public ReceiverApp() {

	}
	
	public void receiveLocationData() throws Exception {
		
		  System.out.println("Server ready to receive tag location data");
		  
          	  while (true){
		              ServerSocket sersock = new ServerSocket(6001);
			      Socket sock = sersock.accept();
		              InputStream istream = sock.getInputStream();
			      InputStreamReader isr = new InputStreamReader(istream);
			      BufferedReader receiveRead = new BufferedReader(isr);
			      sersock.close(); 
			      String locationUpdate; 
			      Thread.sleep(1000);

			      while((locationUpdate = receiveRead.readLine()) != null)    
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
					 }
					 catch (Exception e) {
						 e.printStackTrace();
					 }
			      }
			}
	}
	
	public void receiveInitialApp() throws Exception {
		
		System.out.println("Server ready to receive initial data from App");
		
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
        
        //Parse Reference Line for Camera 1
	    String[] coordinates = receiveMessage.split(":");
		String sReference = coordinates[1].substring(0, coordinates[1].length()-7);
		String sReferenceX = sReference.substring(0, sReference.indexOf(","));
		String sReferenceY = sReference.substring(sReference.indexOf(",")+1, sReference.indexOf(",", sReference.indexOf(",")+1));   
		String sReferenceZ = sReference.substring(sReference.indexOf(",", sReference.indexOf(",")+1)+1, sReference.length());
		
		double xReference = Double.parseDouble(sReferenceX);
		double yReference = Double.parseDouble(sReferenceY);
		double zReference = Double.parseDouble(sReferenceZ);

        referenceLine = new double[]{xReference, yReference, zReference};
		System.out.println("Reference Line = " + referenceLine[0] + " " + referenceLine[1] + " " + referenceLine[2]);
	    
		//Parse Location for camera1
		String sCamera1 = coordinates[8].substring(0, coordinates[8].length()-7);
		String sCamera1X = sCamera1.substring(0, sCamera1.indexOf(","));
		String sCamera1Y = sCamera1.substring(sCamera1.indexOf(",")+1, sCamera1.indexOf(",", sCamera1.indexOf(",")+1));   
		String sCamera1Z = sCamera1.substring(sCamera1.indexOf(",", sCamera1.indexOf(",")+1)+1, sCamera1.length());
		
		double xCamera1 = Double.parseDouble(sCamera1X);
		double yCamera1 = Double.parseDouble(sCamera1Y);
		double zCamera1 = Double.parseDouble(sCamera1Z);
		
		camera1 = new double[] {xCamera1, yCamera1, zCamera1};
		System.out.println("Camera 1 location = " + camera1[0] + " " + camera1[1] + " " + camera1[2]);
	}

	public void receiveTagApp() throws Exception {
		
		System.out.println("Server ready to receive Tag data from App");
		
        	while (true){
		      ServerSocket sersock = new ServerSocket(7007);
		      Socket sock = sersock.accept();                          
		      InputStream istream = sock.getInputStream();
		      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		 
		      String receiveMessage;               
		      if((receiveMessage = receiveRead.readLine()) != null)  
		      {
		         System.out.println("Data received from App: " + receiveMessage);         
			     sersock.close();
		      }         
		      //this.receiveLocationData();

	      
	    	 }               
	}
	
}
