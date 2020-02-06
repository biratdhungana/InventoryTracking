package main;
import java.net.*;
import java.util.Arrays;
import java.io.*;

public class ReceiverCameraData implements Runnable {
	
	Thread sendCameraData;
	Thread receiveCameraData;
	Thread sendAppData;
	Thread receiveFromApp;
	Thread receiveTagLocation;

	public ReceiverCameraData() {

		
		this.sendCameraData = new Thread(this,"sendCameraData");
		this.receiveCameraData = new Thread(this, "receiveCameraData"); 
		this.sendAppData = new Thread(this,"sendAppData");
		this.receiveTagLocation = new Thread(this, "receiveTagLocation");
		this.receiveFromApp = new Thread(this, "receiveFromApp");
		
		
	}
        	
	public void start()
	{
		System.out.println("Server Running");
		this.receiveFromApp.start();
		this.sendCameraData.start();
		this.receiveCameraData.start();
		this.receiveTagLocation.start();
		this.sendAppData.start();
		
	}
	
	
	
	public void run() {
		// TODO Auto-generated method stub
		
		if(Thread.currentThread().getName().equals("sendCameraData"))
		{
		
       	 	
		}
		else if(Thread.currentThread().getName().equals("receiveCameraData"))
		{
			
		}
		else if(Thread.currentThread().getName().equals("sendAppData"))
		{
			
		}
		else if(Thread.currentThread().getName().equals("receiveTagLocation"))  //receive tag location update polling
		{
			
			
			ReceiverApp locationUpdate = new ReceiverApp();
			try {
				//locationUpdate.receiveLocationData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	                	
			
		}
		else //receive from app
		{
			
			ReceiverApp rApp = new ReceiverApp();
			try {
				rApp.receiveInitialApp();
				rApp.receiveTagApp();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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
