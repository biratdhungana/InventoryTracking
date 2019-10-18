package main;
import java.net.*;

public class ReceiverCameraData {


	public ReceiverCameraData() {

	}
	
	private final static int PACKETSIZE = 100 ;

	public static void main( String args[] )
	{
		CameraLineOfSight camera = new CameraLineOfSight();
	      
	    int[] lineofsight = camera.equationOfLine(camera.cameraLocation, camera.tagLocation);
		System.out.println("z = " + lineofsight[0] + "x+" + lineofsight[1]+ "y+" + lineofsight[2]);
			
		int[] entranceBoundary = camera.equationOfLine(camera.doorEdge1, camera.doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x+" + entranceBoundary[1]+ "y+" + entranceBoundary[2]);
		
		double[] angles = camera.angles();
		System.out.println("Angle on x axis (in radians) = " + angles[0]);
		System.out.println("Angle on y axis (in radians) = " + angles[1]);
		System.out.println("Angle on z axis (in radians) = " + angles[2]);
		
		
	      // Check the arguments
	    if( args.length != 2 )   //if the number of arguments do not include port and host
	    {
	       System.out.println( "usage: Receiver port" ) ;
	       return ;
	    }
	      
	    DatagramSocket socket = null ;  
	      
	    try
	    {
	         // Convert the argument to ensure that is it valid
	         socket = new DatagramSocket(4445) ;                       //socket

	         
	         for( ;; )
	         {
	        	 //receive data from app regarding which RF tag (inventory #) the camera needs to track 
	        	 ReceiverApp rfTagData = new ReceiverApp();
	        	 rfTagData.receive(6000);
	        	 
	        	 //send data to cameras regarding which RF tag (inventory #) the camera needs to track
	        	 SendToCamera sendCamera = new SendToCamera();
	        	 sendCamera.send(angles[0], angles[1], angles[2]);
	        	 
	        	 //receive snapshots/livestream data from cameras
		         DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;   //initialize packet of data to be received
	             socket.receive( packet ) ;     //receive data from camera
	             
	             //Send camera/livestream data to android app
	             Database db = new Database();
	             SenderApp sender = new SenderApp();     //initialize sender
	             sender.send(db, "134.117.59.135", 6000);
	             
	             
	         }
	    }
	    catch( Exception e )
		{
		    System.out.println( e ) ;
		}
	      
	  	
	}
}
