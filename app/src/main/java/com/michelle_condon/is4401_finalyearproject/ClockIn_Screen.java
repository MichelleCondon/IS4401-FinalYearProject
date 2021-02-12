package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

public class ClockIn_Screen extends AppCompatActivity {

    //Code below is based on the Youtube video "Biometric Authentication|Android Studio|Java", Atif Pervaiz, https://www.youtube.com/watch?v=yPcxZWSszh8 (1)

    //Declare Variables
    private TextView lblOptions, txtStartShift;
    private TextView txtEndShift, txtBreak, txtEndBreak;
    private EditText txtEmployeeName;
    public Button btnStartShift, btnEndShift, btnStartBreak, btnEndBreak, btnAuthenticate1, btnAuthenticate2, btnAuthenticate3, btnAuthenticate4;
    public Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    DatabaseReference reff;
    Timesheets timesheets;
    String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in__screen);

        //Removes any text from the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Setting a dynamic title at runtime. Here, it displays the current time.
            actionBar.setTitle("");
        }

        //initialise buttons and text
        lblOptions = findViewById(R.id.lblOptions);
        btnStartShift = findViewById(R.id.btnStartShift);
        btnEndShift = findViewById(R.id.btnEndShift);
        btnEndBreak = findViewById(R.id.btnEndBreak);
        btnStartBreak = findViewById(R.id.btnStartBreak);
        btnAuthenticate1 = findViewById(R.id.btnAuthenticate1);
        btnAuthenticate2 = findViewById(R.id.btnAuthenticate2);
        btnAuthenticate3 = findViewById(R.id.btnAuthenticate3);
        btnAuthenticate4 = findViewById(R.id.btnAuthenticate4);
        txtStartShift = findViewById(R.id.txtStartShift);
        txtEndShift = findViewById(R.id.txtEndShift);
        txtBreak = findViewById(R.id.txtBreak);
        txtEndBreak = findViewById(R.id.txtEndBreak);
        txtEmployeeName = findViewById(R.id.txtEmployeeName);


        //Code below is based on the website Developers, Android Developers, https://developer.android.com/training/sign-in/biometric-auth (2)
        //initialise biometrics
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ClockIn_Screen.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //error authenticating
                lblOptions.setText("Authentication Error: " + errString);
                btnAuthenticate1.setVisibility(View.INVISIBLE);
                btnAuthenticate2.setVisibility(View.INVISIBLE);
                btnAuthenticate3.setVisibility(View.INVISIBLE);
                btnAuthenticate4.setVisibility(View.INVISIBLE);
                Toast.makeText(ClockIn_Screen.this, "Authentication Error, Please contact your manager", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //authentication succeed, continue tasks that require authentication
                lblOptions.setText("Authentication Succeeded at: " + timeStamp);
                txtEmployeeName.setVisibility(View.VISIBLE);
                Toast.makeText(ClockIn_Screen.this, "Authentication Succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onAuthenticationFailed() {
                //Failure with authentication
                super.onAuthenticationFailed();
                lblOptions.setText("Authentication Failed!");
                btnAuthenticate1.setVisibility(View.INVISIBLE);
                btnAuthenticate2.setVisibility(View.INVISIBLE);
                btnAuthenticate3.setVisibility(View.INVISIBLE);
                btnAuthenticate4.setVisibility(View.INVISIBLE);
                Toast.makeText(ClockIn_Screen.this, "Authentication Failed, Please contact your manager", Toast.LENGTH_SHORT).show();
            }
        });

        //setup title description on authentication dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText(" ")
                .build();


        //When the button is clicked bring up the authentication prompt and the second authenticate button
        // while setting the text of the labels for insertion into Firebase
        btnStartShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
                btnAuthenticate1.setVisibility(View.VISIBLE);
                txtStartShift.setText(timeStamp);
                txtEndShift.setText("0");
                txtBreak.setText("0");
                txtEndBreak.setText("0");


            }
        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnEndShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
                btnAuthenticate2.setVisibility(View.VISIBLE);
                txtEndShift.setText(timeStamp);
                txtStartShift.setText("0");
                txtBreak.setText("0");
                txtEndBreak.setText("0");


            }
        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnStartBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
                btnAuthenticate3.setVisibility(View.VISIBLE);
                txtBreak.setText(timeStamp);
                txtEndShift.setText("0");
                txtStartShift.setText("0");
                txtEndBreak.setText("0");

            }
        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnEndBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
                btnAuthenticate4.setVisibility(View.VISIBLE);
                txtEndBreak.setText(timeStamp);
                txtEndShift.setText("0");
                txtBreak.setText("0");
                txtStartShift.setText("0");


            }
        });

//Code below to insert data into Firebase is based on a YouTube Video, by EducaTree, https://www.youtube.com/watch?v=iy6WexahCdY&t=328 (3)
        timesheets = new Timesheets();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("Timesheet");
        btnAuthenticate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting the value of each variable to whatever value is derived from the system timestamp
                // and the name the employee enters
                timesheets.setEmployeeName(txtEmployeeName.getText().toString().trim());
                timesheets.setIn(txtStartShift.getText().toString().trim());
                timesheets.setBreak("0");
                timesheets.setEndBreak("0");
                timesheets.setOut("0");
                reff.push().setValue(timesheets);
                Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
            }
        });

        btnAuthenticate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
                timesheets.setEmployeeName(txtEmployeeName.getText().toString().trim());
                timesheets.setIn("0");
                timesheets.setBreak("0");
                timesheets.setEndBreak("0");
                timesheets.setOut(txtEndShift.getText().toString().trim());
                reff.push().setValue(timesheets);
                Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
            }
        });

        btnAuthenticate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
                timesheets.setEmployeeName(txtEmployeeName.getText().toString().trim());
                timesheets.setIn("0");
                timesheets.setBreak(txtBreak.getText().toString().trim());
                timesheets.setEndBreak("0");
                timesheets.setOut("0");
                reff.push().setValue(timesheets);
                Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
            }
        });

        btnAuthenticate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
                timesheets.setEmployeeName(txtEmployeeName.getText().toString().trim());
                timesheets.setIn("0");
                timesheets.setBreak("0");
                timesheets.setEndBreak(txtEndBreak.getText().toString().trim());
                timesheets.setOut("0");
                reff.push().setValue(timesheets);
                Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
            }
        });
        //End (1)
        //End (2)
        //End (3)
    }
}