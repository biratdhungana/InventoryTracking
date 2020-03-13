package main;
import java.net.*;
import java.util.Arrays;
import java.io.*;

public class ReceiverCameraData implements Runnable {
	
	public Thread receiveFromApp;
	public Thread receiveTagLocation;
	public Thread images;

	public ReceiverCameraData() {

		this.receiveTagLocation = new Thread(this, "receiveTagLocation");
		this.receiveFromApp = new Thread(this, "receiveFromApp");
		this.images = new Thread(this, "images");
		
		
	}
        	
	public void start()
	{
		System.out.println("Server Running");
		this.receiveFromApp.start();
		this.images.start();
		//this.receiveTagLocation.start();
		
	}
	
	
	
	public void run() {
		// TODO Auto-generated method stub
		if(Thread.currentThread().getName().equals("receiveTagLocation"))  //receive tag location update polling
		{
			
			ReceiverApp locationUpdate = new ReceiverApp();
			try {
				locationUpdate.receiveLocationData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	                	
		}
		else if(Thread.currentThread().getName().equals("receiveFromApp"))//receive from app
		{
			
			ReceiverApp rApp = new ReceiverApp();
			try {
				rApp.receiveInitialApp();
				rApp.receiveTagApp();     //commented out because it is currently a one tag system - uncomment when more tags are added
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		else {   //receive images from camera, store, and send to user app
			
		}
	}
        

	public static void main( String args[] )
	{
	
	    try
	    {

	        	 ReceiverCameraData program = new ReceiverCameraData();
	        	 program.start();

	    }
	    catch( Exception e )
		{
		    System.out.println( e ) ;
	        }
	      
	  	
	}
}
