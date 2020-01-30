package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SenderApp {

	public static Socket s;
    public static ServerSocket ss;
    public static InputStreamReader isr;
    public static BufferedReader bufferedReader;
    public static String message;
    
	public void send(Database db, String ip, int port)  {   
		Database database = db;         //Database is not created yet
		                                //Database will store the snapshots
		
		try {
			Socket s = new Socket(ip, port);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			//pw.write(database.retrieveLastEntry());      //The latest 5 snapshots in the database will be retrieved and sent to the app
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
