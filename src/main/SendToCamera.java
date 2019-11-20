package main;

import org.json.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.ByteBuffer;

public class SendToCamera {

public JSONObject params;
	
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
	
	
	public void send(double upDownAngle, double sidewaysAngle) {
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
	
}
