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

    //UI Views
    private TextView authStatusTv;
    private Button authenticate;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in__screen);

        //init UI Views
        authStatusTv = findViewById(R.id.authStatusTv);
        authenticate = findViewById(R.id.authenticate);

        //init bio metric
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
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                super.onAuthenticationSucceeded(result);
                //authentication succeed, continue tasks that require auth
                authStatusTv.setText("Authenticate Succeeded!");
                Toast.makeText(ClockIn_Screen.this, "Authentication Succeeded!", Toast.LENGTH_SHORT).show();
        }

            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
                authStatusTv.setText("Authentication Failed!");
                Toast.makeText(ClockIn_Screen.this, "Authentication Succeeded!", Toast.LENGTH_SHORT).show();
            }
    });

        //setup title description on auth dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerpprint authentication")
                .setNegativeButtonText("User app password")
                .build();
        //handle authentication
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate((promptInfo));

            }
        });
    }
}