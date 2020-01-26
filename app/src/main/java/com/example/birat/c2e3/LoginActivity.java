package com.example.birat.c2e3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, resetButton, setupButton;
    EditText userName, password;
    int loginLimit = 3;
    String finalUserName, finalPassWord;

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
        userName = (EditText)findViewById(R.id.usernameSpace);
        password = (EditText)findViewById(R.id.passwordSpace);

        setupButton = (Button)findViewById(R.id.SetupButton);

        finalUserName = userName.getText().toString();
        finalPassWord = password.getText().toString();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Setup the activity pages

                //Route to the first input page
                Intent lineOfSightsPage = new Intent(LoginActivity.this, LineOfSight.class);
                startActivity(lineOfSightsPage);
            }
        });

        //TODO: Add signup new activity to go to after signup button


    }
}
