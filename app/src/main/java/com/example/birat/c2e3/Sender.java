package com.example.birat.c2e3;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Sender implements Runnable{
    int port;
    String message;
    InetAddress ip;
    String messageReceived;
    Socket socket;

    public Sender (){
    }
    /**
     * Constructor Sender class.
     *
     * @param ip
     * @param port
     * @param message
     */
    public Sender(InetAddress ip, int port, String message){
        this.port=port;
        this.ip=ip;
        this.message=message;
    }
    /**
     * Setter method to set message.
     *
     * @param m
     */
    public void setMessage(String m){
        message = m;
    }

    @Override
    public void run() {
        try {

            try{
                System.out.println("Trying to create a socket!");
                socket = new Socket(ip, port);
                System.out.println("Socket created!");
            }catch (SocketException e){
                e.printStackTrace();
            }

            //Initiate output stream
            DataOutputStream cmdOut = new DataOutputStream(socket.getOutputStream());

            while (true) {
                cmdOut.writeBytes(message); //Convert the command to bytes and send them out through the stream
                socket.close();
            }//end while(true)
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch

    }//end run

}//end the class Sender
