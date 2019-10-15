package main;
import java.net.*;

public class ReceiverArduinoData {


	public ReceiverArduinoData() {

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
	         int port = Integer.parseInt( args[1] ) ;                  //port number   
	         InetAddress host = InetAddress.getByName( args[0] ) ;     //host address
	         socket = new DatagramSocket(port) ;                       //socket

	         
	         for( ;; )
	         {
		         DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;   //initialize packet of data to be received
	             socket.receive( packet ) ;     //receive data
	         }
	    }
	    catch( Exception e )
		{
		    System.out.println( e ) ;
		}
	      
	  	
	}
}
