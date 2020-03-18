package com.example.birat.c2e3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Tags extends AppCompatActivity {

    Button tag1B, tag2B, tag3B, tag4B,doorPictureB, logoutB;
    InetAddress serverIP;
    Intent liveStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here
        setContentView(R.layout.activity_tags);

        tag1B = (Button)findViewById(R.id.TagButton1);
        tag2B = (Button)findViewById(R.id.TagButton2);
        tag3B = (Button)findViewById(R.id.TagButton3);
        tag4B = (Button)findViewById(R.id.TagButton4);
        doorPictureB = (Button)findViewById(R.id.PictureButton);
        logoutB = (Button) findViewById(R.id.logoutButton);
        liveStream = new Intent(Tags.this, LiveStreamActivity.class);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem creating IP address!");
        }


        tag1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread tag1Sender = new Thread(new Sender(serverIP,7007,"Request: Tag 1."));
                tag1Sender.start();

                startActivity(liveStream);
            }
        });

        tag2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tag2Sender = new Thread(new Sender(serverIP,7007,"Request: Tag 2."));
                tag2Sender.start();

                startActivity(liveStream);
            }
        });

        tag3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tag3Sender = new Thread(new Sender(serverIP,7007,"Request: Tag 3."));
                tag3Sender.start();

                startActivity(liveStream);
            }
        });

        tag4B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tag4Sender = new Thread(new Sender(serverIP,7007,"Request: Tag 4."));
                tag4Sender.start();

                startActivity(liveStream);
            }
        });

        doorPictureB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread doorAccess = new Thread(new Sender(serverIP,7007,"Request: Door Access."));
                doorAccess.start();

                Intent pictureDisplay = new Intent(Tags.this,DoorActivity.class);
                startActivity(pictureDisplay);
            }
        });

        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loggedOut = new Intent(Tags.this,LoginActivity.class);
                startActivity(loggedOut);
            }
        });


    }
}
