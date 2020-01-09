/*
 * LEAPS - Low Energy Accurate Positioning System.
 *
 * Copyright (c) 2016-2020, LEAPS. All rights reserved.
 */

package com.decawave.argomanager.components.impl;

        import org.apache.commons.lang3.ObjectUtils;

        import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.InputStreamReader;
        import java.net.InetAddress;
        import java.net.Socket;
        import java.net.SocketException;

        import com.decawave.argo.api.struct.Position;
        import com.decawave.argo.api.struct.RangingAnchor;
        import com.decawave.argomanager.Constants;
        import com.decawave.argomanager.components.LocationDataLogger;
        import com.decawave.argomanager.debuglog.ApplicationComponentLog;
        import com.decawave.argomanager.debuglog.LogEntryTagFactory;
        import com.decawave.argomanager.util.Util;

//YOLO
        import java.net.InetAddress;
        import java.net.UnknownHostException;
//YOLO

        import java.util.List;

        import javax.inject.Inject;

        import eu.kryl.android.common.log.ComponentLog;

public class Sender implements Runnable{
    int port;
    String message;
    InetAddress ip;
    String messageReceived;
    Socket socket;
    Position position;


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
                cmdOut.writeBytes(message); //Convert the command to bytes and send them out through the stream.

                //message = "" + position + "";
                socket.close();
            }//end while(true)

        } catch (Exception e) {
            e.printStackTrace();
        }//end catch

    }//end run

}//end the nested class Sender