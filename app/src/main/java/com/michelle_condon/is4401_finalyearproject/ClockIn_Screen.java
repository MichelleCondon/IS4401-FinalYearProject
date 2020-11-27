package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class ClockIn_Screen extends AppCompatActivity {
    //Code below is based on the Youtube video "Biometric Authentication| Android Studio| Java", Atif Pervaiz,	https://www.youtube.com/watch?v=yPcxZWSszh8 (1)
    //UI Views
    private TextView authStatusTv;
    private Button authenticate, endShift, startBreak, endBreak;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in__screen);

        //initialise buttons and text
        authStatusTv = findViewById(R.id.authStatusTv);
        authenticate = findViewById(R.id.authenticate);
        endShift = findViewById(R.id.endShift);
        endBreak = findViewById(R.id.endBreak);
        startBreak = findViewById(R.id.startBreak);


        //Code below is based on the website Developers, Android Developers, https://developer.android.com/training/sign-in/biometric-auth (2)
        //initialise biometrics
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ClockIn_Screen.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //error authenticating
                authStatusTv.setText("Authentication Error: " + errString);
                Toast.makeText(ClockIn_Screen.this, "Authentication Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //authentication succeed, continue tasks that require auth
                authStatusTv.setText("Authenticate Succeeded!");
                Toast.makeText(ClockIn_Screen.this, "Authentication Succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                //Failure with authentication
                super.onAuthenticationFailed();
                authStatusTv.setText("Authentication Failed!");
                Toast.makeText(ClockIn_Screen.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        //setup title description on authentication dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText("Login with Password Instead")
                .build();
        //handle authentication
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
            }
        });
        endShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
            }
        });
        startBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
            }
        });
        endBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));
            }
        });
        //End (1)
        //End (2)
    }
}