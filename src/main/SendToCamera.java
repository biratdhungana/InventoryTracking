package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class SendToCamera {

	
	public SendToCamera() {
		
	}

	
	public void sendAnglesCamera1(double upDownAngle, double sidewaysAngle) throws Exception
	 {
         // System.out.println("Creating socket");
	      Socket sock = new Socket("192.168.1.104", 6000);
	      //System.out.println("Sending angles to Camera");
	      //System.out.println("Socket created and Accepted.");
	      //System.out.println("Connection established");
	      OutputStream ostream = sock.getOutputStream(); 
	      PrintWriter pwrite = new PrintWriter(ostream, true);
	      String sendMessage;               
		  String angleData = new String(Double.toString(upDownAngle) + " " + Double.toString(sidewaysAngle));
		  sendMessage = angleData;
		  pwrite.println(sendMessage);             
		  pwrite.flush();
	//	  System.out.println("Angles Sent to Cameras: " + angleData);


	//	  System.out.println("Server ready to receive Acknowledgement of Camera Movement");

		  BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	//	  System.out.println("input stream created");
		  String receiveMessage;
		  if((receiveMessage = in.readLine()) != null){

	//			System.out.println("Camera Movement Acknowledgement Received: " + receiveMessage);
				sock.close();
	//			System.out.println("Socket closed");
		  }

    	}         
	
	public void sendAnglesCamera2(double upDownAngle, double sidewaysAngle) throws Exception
	 {
         // System.out.println("Creating socket");
	      Socket sock = new Socket("192.168.1.104", 6000);
	  //    System.out.println("Sending angles to Camera");
	   //   System.out.println("Socket created and Accepted.");
	    //  System.out.println("Connection established");
	      OutputStream ostream = sock.getOutputStream(); 
	      PrintWriter pwrite = new PrintWriter(ostream, true);
	      String sendMessage;               
	      String angleData = new String(Double.toString(upDownAngle) + " " + Double.toString(sidewaysAngle));
	      sendMessage = angleData;
	      pwrite.println(sendMessage);             
	      pwrite.flush();
	     // System.out.println("Angles Sent to Cameras: " + angleData);


	      //System.out.println("Server ready to receive Acknowledgement of Camera Movement");

	      BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//	      System.out.println("input stream created");
	      String receiveMessage;
	      if((receiveMessage = in.readLine()) != null){
	//			System.out.println("Camera Movement Acknowledgement Received: " + receiveMessage);
				sock.close();
	//			System.out.println("Socket closed");
	      }

   	}        

}
