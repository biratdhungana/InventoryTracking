package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.ByteBuffer;

public class SendToCamera {

	
	public SendToCamera() {
		
	}

	/*public JSONObject send(double xAngle, double yAngle, double zAngle) throws JSONException {
		//Get the information that will go through the sender
		params = new JSONObject();
		params.put("Angle to be moved on x-axis", xAngle);
		params.put("Angle to be moved on y-axis", yAngle);
		params.put("Angle to be moved on z-axis", zAngle);
		return params;
		}
		*/
	
	private static byte[] doubleToByteArray(double x, double y) {
        byte[] bytes = new byte[20];
        ByteBuffer.wrap(bytes).putDouble(x);
        ByteBuffer.wrap(bytes).putDouble(y);
        return bytes;
    }
	
	/*
	public void send(double upDownAngle, double sidewaysAngle) throws JSONException {
		//Get the information that will go through the sender
		try {
			@SuppressWarnings("resource")
			Socket s = new Socket("134.117.39.197", 6000);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			
			pw.write((int)upDownAngle);
			System.out.println("Sent up down angle");
			pw.write((int)sidewaysAngle);
			System.out.println("Sent sideways angle");
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		}
	*/
	
	/*
	public void sendTo(double upDownAngle, double sidewaysAngle) {
		DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            
            CameraLineOfSight camera = new CameraLineOfSight();
            double[] angles = camera.angles();
            
            byte[] angleData = doubleToByteArray(upDownAngle, sidewaysAngle);
            
            byte[] address = {(byte) 192, (byte) 168, (byte) 1, (byte) 100};
            InetAddress aHost = InetAddress.getByAddress(address);
            int serverPort = 6000;
            DatagramPacket request
                    = new DatagramPacket(angleData, angleData.length, aHost, serverPort);
            aSocket.send(request);
            System.out.println("Inside function");
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
	}
	*/
	public void sendAngles(double upDownAngle, double sidewaysAngle) throws Exception
	  {
	      Socket sock = new Socket("192.168.1.102", 6000);
	      System.out.println("Sending angles to Camera");
	      //Socket sock = sersock.accept( );                          
	                              // reading from keyboard (keyRead object)
	      //BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		                      // sending to client (pwrite object)
	      System.out.println("Socket created and Accepted.");
	      System.out.println("Connection established");
	      OutputStream ostream = sock.getOutputStream(); 
	      PrintWriter pwrite = new PrintWriter(ostream, true);
	      String sendMessage;               
	      //while(true)
	     // {
	        String angleData = new String(Double.toString(upDownAngle) + " " + Double.toString(sidewaysAngle));
	        System.out.println("Angles Sent to Cameras: " + angleData);
	        sendMessage = angleData;
	        pwrite.println(sendMessage);             
	        pwrite.flush();
	      //}               
	    }                    
	

	
}
