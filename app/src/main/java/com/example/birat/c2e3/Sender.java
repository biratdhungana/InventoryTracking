package com.example.birat.c2e3;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
                socket = new Socket(ip, port);
            }catch (SocketException e){
                e.printStackTrace();
            }

            //Initiate output stream
            DataOutputStream cmdOut = new DataOutputStream(socket.getOutputStream());

            while (true) {
                cmdOut.writeBytes(message); //Convert the command to bytes and send them out through the stream.

                //Receiving ACK from the input stream
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                messageReceived = in.readLine();

                socket.close();
            }//end while(true)
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch

    }//end run

}//end the nested class Sender
