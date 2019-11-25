package main;
import java.net.*;
import java.util.Arrays;

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
		System.out.println("Up-Down Angle (in radians) = " + angles[0]);
		System.out.println("Sideways Angle (in radians) = " + angles[1]);
		
	      
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
	        	 SendToCamera sendCamera = new SendToCamera();
	        	 sendCamera.send(angles[0], angles[1]);
	        	 System.out.println("Sent data");
	        	 
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
