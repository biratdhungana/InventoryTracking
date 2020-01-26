package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LineOfSight extends AppCompatActivity {

    Button finishButton;
    EditText lineOfSight, doorway, cornerOne, cornerTwo, cornerThree, cameraOne, cameraTwo;
    EditText cameraThree;
    InetAddress serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here

        setContentView(R.layout.activity_line_of_sight);

        finishButton = (Button)findViewById(R.id.FinishButton);
        lineOfSight = (EditText)findViewById(R.id.LineOfSightSpace);
        doorway = (EditText)findViewById(R.id.Doorway1);
        cornerOne = (EditText)findViewById(R.id.Corner1);
        cornerTwo = (EditText) findViewById(R.id.Corner2);
        cornerThree = (EditText)findViewById(R.id.Corner3);
        cameraOne = (EditText)findViewById(R.id.Camera1);
        cameraTwo = (EditText)findViewById(R.id.Camera2);
        cameraThree =(EditText)findViewById(R.id.Camera3);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem in IP address. Check try catch.");
            e.printStackTrace();
        }

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread setupSender = new Thread(new Sender(serverIP,8008,"Line of Sight:" + lineOfSight.getText().toString() +" " + "Doorway:" + doorway.getText().toString() +" " +
                        "Corner1:" + cornerOne.getText().toString() + " " + "Corner2:" + cornerTwo.getText().toString()+ " "  + "Corner3:" + cornerThree.getText().toString() + " " +
                        "Camera1:" + cameraOne.getText().toString() + " " + "Camera2:" + cornerTwo.getText().toString()+ " " + "Camera3:" + cameraThree.getText().toString() + " "));
                setupSender.start();
            }
        });
    }
}
