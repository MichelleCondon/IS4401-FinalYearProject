package com.michelle_condon.is4401_finalyearproject.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.R;

public class SplashScreen extends AppCompatActivity {

    //Code below is based on the website "Android Splash Screen With Progress Bar Example" by Codexpedia, "https://www.codexpedia.com/android/android-splash-screen-with-progress-bar-example/"
    //Logo was generated on https://hatchful.shopify.com/

    //Declare progress bar as a variable
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Shows the splash screen
        setContentView(R.layout.activity_splash_screen);
        mProgress = findViewById(R.id.splashScreenProgressBar);

        // Start operation in a background thread
        new Thread(() -> {
            doWork();
            startApp();
            finish();
        }).start();
    }

    private void doWork() {
        //For loop to load progress bar on the screen
        for (int progress = 10; progress < 100; progress += 10) {
            try {
                Thread.sleep(500);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                Toast.makeText(SplashScreen.this, "App Failed to Load, Please Contact Management", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startApp() {
        //Open a new activity
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
//End