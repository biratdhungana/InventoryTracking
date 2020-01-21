package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Tags extends AppCompatActivity {

    Button tag1B;
    InetAddress serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        tag1B = (Button)findViewById(R.id.testButton);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem creating IP address!");
        }


        tag1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tag1Sender = new Thread(new Sender(serverIP,7007,"Requesting Info for Tag 1."));
                tag1Sender.start();
            }
        });
    }
}
