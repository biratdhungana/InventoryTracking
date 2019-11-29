package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class LoginActivity extends AppCompatActivity{

    Button loginButton, resetButton;
    EditText userName, password;
    int loginLimit = 3; //Limit for number of logins
    InetAddress serverIP;
    Sender s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.loginButton);
        resetButton = (Button)findViewById(R.id.resetButton);
        userName = (EditText)findViewById(R.id.usernameSpace);
        password = (EditText)findViewById(R.id.passwordSpace);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.println("Could not get IP address correctly.");
        }


        /**
         * When Login Button is pressed
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Temporary step - needs to be sent to server for final version

                if((userName.getText().toString().equals("b"))
                    && (password.getText().toString().equals("b"))){

                    Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();

                    //Take to the next activity
                    Intent choicePage = new Intent(LoginActivity.this,ChoiceActivity.class);

                    //s1 = new Sender(serverIP, 8008,"We connected!");

                    Thread testSnder = new Thread(new Sender(serverIP,8008,"We Connected"));
                    testSnder.start();


                    //Start new Page
                    startActivity(choicePage);

                }
                else{ //Wrong Credentials
                    loginLimit--;
                    Toast.makeText(getApplicationContext(),"Wrong Credentials!",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Attempts Left: " + loginLimit,Toast.LENGTH_LONG).show();

                    if (loginLimit==0){
                        loginButton.setEnabled(false);
                    }
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginLimit>0) {
                    Intent relaodLogin = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(relaodLogin);
                }else{
                    //TODO: Dissscuss the solution in this case
                    Toast.makeText(getApplicationContext(),"Please Figure out a Solution",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

} // end main class
