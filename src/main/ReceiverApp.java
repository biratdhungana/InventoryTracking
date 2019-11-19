package main;

import java.net.DatagramSocket;

public class ReceiverApp {

	public ReceiverApp() {

	}
	
	public void receive(int port) {
		
		DatagramSocket socket = null ;
		try
	    {
	         // Convert the argument to ensure that is it valid
	         socket = new DatagramSocket(port) ;
	         
	    }
		catch( Exception e )
		{
		    System.out.println( e ) ;
		}
	}
}
