package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    //Code below is based on the Youtube video "Welcome Screen (Splash Page) Android Studio|Beginner's Guide", Ben O'Brien, https://www.youtube.com/watch?v=9O1lI0BRCCE
    //Logo was generated on https://hatchful.shopify.com/
    private static int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets splash screen to full screen in the window of the phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Paired XML file that contains the design of the splash screen
        setContentView(R.layout.activity_splash_screen);

        //Fade out details
        Animation fadeOut = new AlphaAnimation(1, 0);

        //Design specs for the splash screen - duration, start time & acceleration curve
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1800);
        ImageView image = findViewById(R.id.companyLogoImage);

        //Fades out splash screen after the pre-determined duration of time
        image.setAnimation(fadeOut);

        //Schedule the runnable to be executed at some point in the future
        new Handler().postDelayed(new Runnable() {

            //Run method - opens the main activity when called and closes the existing splash screen
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            //Splashscreen times out
        }, SPLASH_SCREEN_TIMEOUT);

    }
}
//End