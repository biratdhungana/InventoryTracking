package com.example.birat.c2e3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SignUpActivity extends AppCompatActivity {

    Button finishSignUpB, cancelB;
    EditText userNameS, passwordS, confirmPasswordS, pinS;
    TextView messageS;
    InetAddress serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Till here

        setContentView(R.layout.activity_sign_up);

        finishSignUpB = (Button)findViewById(R.id.finishSignUpButton);
        cancelB = (Button)findViewById(R.id.CancelButton);

        userNameS = (EditText)findViewById(R.id.usernameSpaceSignUp);
        passwordS = (EditText)findViewById(R.id.passwordSpaceSignUp);
        confirmPasswordS = (EditText)findViewById(R.id.passwordSpaceSignUp2);
        pinS = (EditText)findViewById(R.id.AdminPINSpace);

        messageS = (TextView)findViewById(R.id.PINMessage);
        messageS.setVisibility(View.GONE);

        try{
            serverIP = InetAddress.getByName("192.168.1.101");
        }catch (UnknownHostException e){
            System.out.print("Problem in IP address. Check try catch.");
            e.printStackTrace();
        }

//        if (!(passwordS.getText().toString().equals(confirmPasswordS.getText().toString()))){
            //TODO: Figure this out button enabling/disabling
//            finishSignUpB.setEnabled(false);
//        }

        finishSignUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((pinS.getText().toString().equals("1234")) && (passwordS.getText().toString().equals(confirmPasswordS.getText().toString()))){
                    //If the pin is right and the passwords match
                    Thread signUpSender = new Thread(new Sender(serverIP,7007,userNameS.getText().toString() + " "
                                                        + passwordS.getText().toString()));
                    signUpSender.start();
                    System.out.println("This if condition passes.");
                }
                else if(!(passwordS.getText().toString().equals(confirmPasswordS.getText().toString()))){
                    //Passwords do not match
                    messageS.setText("Password Mismatch");
                    messageS.setVisibility(View.VISIBLE);
                }
                else{
                    //If the pin is wrong
                    Toast.makeText(getApplicationContext(),"Wrong PIN", Toast.LENGTH_LONG).show();

                    if (pinS.getText().toString().equals("")){
                        messageS.setText("Please contact the administrator for Admin PIN.");
                        messageS.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(backToLogin);
            }
        });
    }

}
