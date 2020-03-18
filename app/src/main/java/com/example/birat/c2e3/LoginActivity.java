package com.example.birat.c2e3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, resetButton, setupButton,signUpButton;
    EditText userName, password;
    int loginLimit = 3;
    String finalUserName, finalPassWord;
    InetAddress serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here

        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.loginButton);
        resetButton = (Button)findViewById(R.id.resetButton);
        signUpButton = (Button)findViewById(R.id.SignUpButton);
        userName = (EditText)findViewById(R.id.usernameSpace);
        password = (EditText)findViewById(R.id.passwordSpace);

        setupButton = (Button)findViewById(R.id.SetupButton);

        finalUserName = userName.getText().toString();
        finalPassWord = password.getText().toString();

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem in IP address. Check try catch.");
            e.printStackTrace();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread loginSender = new Thread(new Sender(serverIP, 8008, userName.getText().toString()+password.getText().toString()));
                loginSender.start();

                //TODO: Implement Receiving End Verification

                if((userName.getText().toString().equals("b"))
                        && (password.getText().toString().equals("b"))){
                    Intent tagsPage = new Intent(LoginActivity.this,Tags.class);
                    startActivity(tagsPage);
                }// end if
                else{
                    loginLimit--;
                    Toast.makeText(getApplicationContext(),"Wrong Credentials!",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Attempts Left: " +loginLimit,Toast.LENGTH_LONG).show();

                    if (loginLimit==0){
                        loginButton.setEnabled(false);
                    }

                }//end else

            }// end onClick
        });//end loginButton setOnClick

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reload = new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(reload);
            }
        });

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Route to the first input page
                Intent lineOfSightsPage = new Intent(LoginActivity.this, LineOfSight.class);
                startActivity(lineOfSightsPage);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Route to signup page
                Intent signUpPage = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signUpPage);

            }
        });




    }
}
