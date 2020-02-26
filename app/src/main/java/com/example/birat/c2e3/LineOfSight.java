package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.EOFException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LineOfSight extends AppCompatActivity {

    Button finishButton;
    EditText lineOfSight, lineOfSight2, doorway, cornerOne, cornerTwo, cornerThree, cornerFour, cameraOne, cameraTwo;
    EditText cameraThree;
    InetAddress serverIP;

    //TODO: Delete later
    Button debugButton;

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
        lineOfSight2 = (EditText)findViewById(R.id.LineOfSightSpace2);
        doorway = (EditText)findViewById(R.id.Doorway1);
        cornerOne = (EditText)findViewById(R.id.Corner1);
        cornerTwo = (EditText) findViewById(R.id.Corner2);
        cornerThree = (EditText)findViewById(R.id.Corner3);
        cornerFour = (EditText)findViewById(R.id.Corner4);
        cameraOne = (EditText)findViewById(R.id.Camera1);
        cameraTwo = (EditText)findViewById(R.id.Camera2);
        cameraThree =(EditText)findViewById(R.id.Camera3);

        //TODO: Delete later
        debugButton = (Button)findViewById(R.id.DebugButton);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem in IP address. Check try catch.");
            e.printStackTrace();
        }

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread setupSender = new Thread(new Sender(serverIP,8008,"Line of Sight:" + lineOfSight.getText().toString() +" " + "Line of Sight 2:" + lineOfSight2.getText().toString() + " "  +
                         "Doorway:" + doorway.getText().toString() +" " + "Corner1:" + cornerOne.getText().toString() + " " + "Corner2:" + cornerTwo.getText().toString()+ " "  + "Corner3:" + cornerThree.getText().toString() + " " +
                         "Corner4:" + cornerFour.getText().toString() + "Camera1:" + cameraOne.getText().toString() + " " + "Camera2:" + cornerTwo.getText().toString()+ " " + "Camera3:" + cameraThree.getText().toString() + " "));
                setupSender.start();
            }
        });

        debugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread defaultSender = new Thread(new Sender(serverIP,8008,"Line of Sight: 10, 20, 30"  + " " + "Line of Sight 2: 40, 50 ,60" + " "  +
                        "Doorway:" + doorway.getText().toString() +" " + "Corner1: 70, 80, 90" + " " + "Corner2: 7, 17, 20" + " "  + "Corner3: 25, 50, 75" + " " +
                        "Corner4: 17, 27, 37" + "Camera1: 47, 57, 67" + " " + "Camera2: 25, 50, 75" + " " + "Camera3:" + cameraThree.getText().toString() + " "));
                defaultSender.start();
            }
        });


    }
}
