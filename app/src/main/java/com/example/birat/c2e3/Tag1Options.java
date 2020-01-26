package com.example.birat.c2e3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Tag1Options extends AppCompatActivity {

    InetAddress serverIP;
    Button livestreamT1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here

        setContentView(R.layout.activity_tag1_options);

        livestreamT1 = (Button)findViewById(R.id.livestreamT1);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem creating IP address!");
        }


        livestreamT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webPage = new Intent(Tag1Options.this, LiveStreamActivity.class);
                startActivity(webPage);

                // Thread tag1Sender = new Thread(new Sender(serverIP,7007,"Requesting Info for Tag 1."));
                // tag1Sender.start();
            }
        });
    }
}
