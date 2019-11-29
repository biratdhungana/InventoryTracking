package main;
import java.net.*;
import java.util.Arrays;
import java.io.*;

public class ReceiverCameraData implements Runnable {
	
	Thread sendCameraData;
	Thread receiveCameraData;
	Thread sendAppData;
	Thread receiveFromApp;

	public ReceiverCameraData() {

		this.sendCameraData = new Thread(this,"sendCameraData");
		this.receiveCameraData = new Thread(this, "receiveCameraData"); 
		this.sendAppData = new Thread(this,"sendAppData");
		this.receiveFromApp = new Thread(this, "receiveFromApp");
	}
	
	public void start()
	{
		System.out.println("Server Running");
		this.sendCameraData.start();
		this.receiveFromApp.start();
		this.sendAppData.start();
		this.receiveFromApp.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		if(Thread.currentThread().getName().equals("sendCameraData"))
		{
			CameraLineOfSight camera = new CameraLineOfSight();
       	 	double[] angles = camera.angles();
       	 	
       	 	SendToCamera sendCamera = new SendToCamera();
       	 	try {
				sendCamera.sendAngles(angles[0], angles[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	 	
       	 	
		}
		else if(Thread.currentThread().getName().equals("receiveCameraData"))
		{
			
		}
		else if(Thread.currentThread().getName().equals("sendAppData"))
		{
			
		}
		else //receive from app
		{
			ReceiverApp rApp = new ReceiverApp();
			try {
				rApp.receive();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main( String args[] )
	{
		CameraLineOfSight cameraPrint = new CameraLineOfSight();
	      
	    int[] lineofsight = cameraPrint.equationOfLine(cameraPrint.cameraLocation, cameraPrint.tagLocation);
		System.out.println("z = " + lineofsight[0] + "x+" + lineofsight[1]+ "y+" + lineofsight[2]);
			
		int[] entranceBoundary = cameraPrint.equationOfLine(cameraPrint.doorEdge1, cameraPrint.doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x+" + entranceBoundary[1]+ "y+" + entranceBoundary[2]);
		
		double[] anglesPrint = cameraPrint.angles();
		System.out.println("Up-Down Angle (in radians) = " + anglesPrint[0]);
		System.out.println("Sideways Angle (in radians) = " + anglesPrint[1]);
		
	      
	    DatagramSocket socket = null ;  
	      
	    try
	    {
	         // Convert the argument to ensure that is it valid
	         //socket = new DatagramSocket(4445) ;                       //socket

	         
	         for( ;; )
	         {
	        	 //receive data from app regarding which RF tag (inventory #) the camera needs to track
	        	 /*
	        	 ReceiverApp rfTagData = new ReceiverApp();
	        	 rfTagData.receive(6000);
	        	 */
	        	 
	        	 /*
	        	 //Receiving data from App
	        	 System.out.println("Waiting to receive");
	        	 DatagramPacket packet = new DatagramPacket(new byte[10], 10);
	        	 socket.receive(packet);
	        	 System.out.println("Received data = " + Arrays.toString(packet.getData()));
	        	 */
	        	 
	        	 //send data to cameras regarding which RF tag (inventory #) the camera needs to track

	        	 //double[] angles = camera.angles();
			  
	        	 //SendToCamera sendCamera = new SendToCamera();
	        	 //sendCamera.sendAngles(angles[0], angles[1]);
	        	 CameraLineOfSight camera = new CameraLineOfSight();
	        	 ReceiverCameraData program = new ReceiverCameraData();
	        	 program.start();
			 
	        	 camera.referenceLine[0] = camera.tagLocation[0];
				 camera.referenceLine[1] = camera.tagLocation[1];
				 camera.referenceLine[2] = camera.tagLocation[2]; 
	
				 System.out.println("Please input new coordinates in the form of 'x y z'");
				 BufferedReader input = new BufferedReader(new InputStreamReader(System.in));   //keyboard input for x,y,z coordinates
				 String inputString = input.readLine(); 
				 String[] coordinates = inputString.split(" ");
				 
				 camera.tagLocation[0] = Integer.parseInt(coordinates[0]);   //update x coordinate to new input value
				 camera.tagLocation[1] = Integer.parseInt(coordinates[1]);   //update y coordinate to new input value
				 camera.tagLocation[2] = Integer.parseInt(coordinates[2]);   //update z coordinate to new input value 
	        	 
	        	 /*
	        	 //receive snapshots/livestream data from cameras
		         DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;   //initialize packet of data to be received
	             socket.receive( packet ) ;     //receive data from camera
	             
	             //Send camera/livestream data to android app
	             Database db = new Database();
	             SenderApp sender = new SenderApp();     //initialize sender
	             sender.send(db, "134.117.59.135", 6000);
	             */
	             
	             
	         }
	    }
	    catch( Exception e )
		{
		    System.out.println( e ) ;
		}
	      
	  	
	}
}
