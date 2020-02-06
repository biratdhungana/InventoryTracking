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
	
	public double[] referenceLine1;
	public double[] referenceLine2;
	public double[] camera1;
	public double[] camera2;
	public double[] corner1;
	public double[] corner2;
	public double[] corner3;

	public boolean firstLoop = true;
	
	public String tagId;

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
				 	
				 	//Database db = new Database();
				 	//db.insert(this.tagId, xNew, yNew, zNew);
				 
				 	//double[] updatedCoordinates = db.retrieveLastEntry(this.tagId);
				 	double[] updatedCoordinates = new double[]{xNew, yNew, zNew};
				 	
				 	//if(in room 1) {   code to send angles to camera depending on which room tag is in
					 
					 	CameraLineOfSight camera = new CameraLineOfSight();
				       	double[] angles = camera.angles(updatedCoordinates, referenceLine1, camera1);
						 
						SendToCamera sendCamera = new SendToCamera();
					

						try {
						 	sendCamera.sendAnglesCamera1(angles[0], angles[1]);
						 }
						 catch (Exception e) {
							 e.printStackTrace();
						 }
			        //}
					/*else {
					  CameraLineOfSight camera = new CameraLineOfSight();
				       double[] angles = camera.angles(updatedCoordinates, referenceLine2, camera2);
						 
						SendToCamera sendCamera = new SendToCamera();
					

						try {
						 	sendCamera.sendAnglesCamera2(angles[0], angles[1]);
						 }
						 catch (Exception e) {
							 e.printStackTrace();
					 
					 */
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
		String sReference = coordinates[1].substring(0, coordinates[1].length()-14);
		String sReferenceX = sReference.substring(0, sReference.indexOf(","));
		String sReferenceY = sReference.substring(sReference.indexOf(",")+1, sReference.indexOf(",", sReference.indexOf(",")+1));   
		String sReferenceZ = sReference.substring(sReference.indexOf(",", sReference.indexOf(",")+1)+1, sReference.length());
		
		double xReference = Double.parseDouble(sReferenceX);
		double yReference = Double.parseDouble(sReferenceY);
		double zReference = Double.parseDouble(sReferenceZ);

        referenceLine1 = new double[]{xReference, yReference, zReference};
		System.out.println("Reference Line of Camera 1= " + referenceLine1[0] + " " + referenceLine1[1] + " " + referenceLine1[2]);
		
		//Parse Reference Line for Camera 2
		String sReference2 = coordinates[2].substring(0, coordinates[2].length()-7);
		String sReferenceX2 = sReference2.substring(0, sReference2.indexOf(","));
		String sReferenceY2 = sReference2.substring(sReference2.indexOf(",")+1, sReference2.indexOf(",", sReference2.indexOf(",")+1));   
		String sReferenceZ2 = sReference2.substring(sReference2.indexOf(",", sReference2.indexOf(",")+1)+1, sReference2.length());
		
		double xReference2 = Double.parseDouble(sReferenceX2);
		double yReference2 = Double.parseDouble(sReferenceY2);
		double zReference2 = Double.parseDouble(sReferenceZ2);

        referenceLine2 = new double[]{xReference2, yReference2, zReference2};
		System.out.println("Reference Line of Camera 2 = " + referenceLine2[0] + " " + referenceLine2[1] + " " + referenceLine2[2]);
	    
		//Parse Location for camera1
		String sCamera1 = coordinates[7].substring(0, coordinates[7].length()-7);
		String sCamera1X = sCamera1.substring(0, sCamera1.indexOf(","));
		String sCamera1Y = sCamera1.substring(sCamera1.indexOf(",")+1, sCamera1.indexOf(",", sCamera1.indexOf(",")+1));   
		String sCamera1Z = sCamera1.substring(sCamera1.indexOf(",", sCamera1.indexOf(",")+1)+1, sCamera1.length());
		
		double xCamera1 = Double.parseDouble(sCamera1X);
		double yCamera1 = Double.parseDouble(sCamera1Y);
		double zCamera1 = Double.parseDouble(sCamera1Z);
		
		camera1 = new double[] {xCamera1, yCamera1, zCamera1};
		System.out.println("Camera 1 location = " + camera1[0] + " " + camera1[1] + " " + camera1[2]);
		
		//Parse Location for camera2
		String sCamera2 = coordinates[8].substring(0, coordinates[8].length());
		String sCamera2X = sCamera2.substring(0, sCamera2.indexOf(","));
		String sCamera2Y = sCamera2.substring(sCamera2.indexOf(",")+1, sCamera2.indexOf(",", sCamera2.indexOf(",")+1));   
		String sCamera2Z = sCamera2.substring(sCamera2.indexOf(",", sCamera2.indexOf(",")+1)+1, sCamera2.length());
		
		double xCamera2 = Double.parseDouble(sCamera2X);
		double yCamera2 = Double.parseDouble(sCamera2Y);
		double zCamera2 = Double.parseDouble(sCamera2Z);
		
		camera2 = new double[] {xCamera2, yCamera2, zCamera2};
		System.out.println("Camera 2 location = " + camera2[0] + " " + camera2[1] + " " + camera2[2]);
		
		//Parse Location for Corner1
		String sCorner1 = coordinates[4].substring(0, coordinates[4].length()-7);
		String sCorner1X = sCorner1.substring(0, sCorner1.indexOf(","));
		String sCorner1Y = sCorner1.substring(sCorner1.indexOf(",")+1, sCorner1.indexOf(",", sCorner1.indexOf(",")+1));   
		String sCorner1Z = sCorner1.substring(sCorner1.indexOf(",", sCorner1.indexOf(",")+1)+1, sCorner1.length());
		
		double xCorner1 = Double.parseDouble(sCorner1X);
		double yCorner1 = Double.parseDouble(sCorner1Y);
		double zCorner1 = Double.parseDouble(sCorner1Z);
		
		corner1 = new double[] {xCorner1, yCorner1, zCorner1};
		System.out.println("Corner 1 location = " + corner1[0] + " " + corner1[1] + " " + corner1[2]);
		
		//Parse Location for Corner2
		String sCorner2 = coordinates[5].substring(0, coordinates[5].length()-7);
		String sCorner2X = sCorner2.substring(0, sCorner2.indexOf(","));
		String sCorner2Y = sCorner2.substring(sCorner2.indexOf(",")+1, sCorner2.indexOf(",", sCorner2.indexOf(",")+1));   
		String sCorner2Z = sCorner2.substring(sCorner2.indexOf(",", sCorner2.indexOf(",")+1)+1, sCorner2.length());
		
		double xCorner2 = Double.parseDouble(sCorner2X);
		double yCorner2 = Double.parseDouble(sCorner2Y);
		double zCorner2 = Double.parseDouble(sCorner2Z);
		
		corner2 = new double[] {xCorner2, yCorner2, zCorner2};
		System.out.println("Corner 2 location = " + corner2[0] + " " + corner2[1] + " " + corner2[2]);
		
		//Parse Location for Corner2
		String sCorner3 = coordinates[6].substring(0, coordinates[6].length()-7);
		String sCorner3X = sCorner3.substring(0, sCorner3.indexOf(","));
		String sCorner3Y = sCorner3.substring(sCorner3.indexOf(",")+1, sCorner3.indexOf(",", sCorner3.indexOf(",")+1));   
		String sCorner3Z = sCorner3.substring(sCorner3.indexOf(",", sCorner3.indexOf(",")+1)+1, sCorner3.length());
		
		double xCorner3 = Double.parseDouble(sCorner3X);
		double yCorner3 = Double.parseDouble(sCorner3Y);
		double zCorner3 = Double.parseDouble(sCorner3Z);
		
		corner3 = new double[] {xCorner3, yCorner3, zCorner3};
		System.out.println("Corner 3 location = " + corner3[0] + " " + corner3[1] + " " + corner3[2]);
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
		         tagId = receiveMessage;
			     sersock.close();
		      }         
		      //this.receiveLocationData();

	      
	    	 }               
	}
	
}
