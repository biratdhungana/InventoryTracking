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

    Button tag1B,logoutB;
//    InetAddress serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here
        setContentView(R.layout.activity_tags);

        tag1B = (Button)findViewById(R.id.TagButton1);
        logoutB = (Button) findViewById(R.id.logoutButton);

//        try{
//            serverIP = InetAddress.getByName("192.168.1.101");
//        }catch (UnknownHostException e){
//            System.out.print("Problem creating IP address!");
//        }


        tag1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tag1Options = new Intent(Tags.this, Tag1Options.class);
                startActivity(tag1Options);

               // Thread tag1Sender = new Thread(new Sender(serverIP,7007,"Requesting Info for Tag 1."));
               // tag1Sender.start();
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
