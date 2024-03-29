package com.michelle_condon.is4401_finalyearproject.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.RequestChange.HoursRequest;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.TimeOffRequest.TimeOffRequest;

public class AccountMenu extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        //Code for the Navigation Bar is Based on a Tutorial "Bottom Navigation Bar in Android" by Geeks For Geeks which can be found at "https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/"
        //Bottom Navigation Menu Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_account) {
                account();
            } else if (item.getItemId() == R.id.action_home) {
                home();
            } else if (item.getItemId() == R.id.action_signout) {
                signout();
            }
            return true;
        });
        //End

        //Account button displayed in the tool bar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Request Hours Change Button
        Button btnRequestHoursChange = findViewById(R.id.btnRequestHoursChange);
        btnRequestHoursChange.setOnClickListener(this);

        //Request Time Off Button
        Button btnRequestTimeOff = findViewById(R.id.btnRequestTimeOff);
        btnRequestTimeOff.setOnClickListener(this);

        //User Profile Button
        Button btnUserProfile = findViewById(R.id.btnViewProfile);
        btnUserProfile.setOnClickListener(this);

    }

    //Signout Button in the Navigation Bar
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    //Home Button in the Navigation Bar
    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    //Account Button in the Navigation Bar
    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    @Override
    public void onClick(View v) {
        //Request Hours Change Button
        if (v.getId() == R.id.btnRequestHoursChange) {
            startActivity(new Intent(this, HoursRequest.class));
            //Request Time Off Button
        } else if (v.getId() == R.id.btnRequestTimeOff) {
            startActivity(new Intent(this, TimeOffRequest.class));
            //View Profile Button
        } else if (v.getId() == R.id.btnViewProfile) {
            startActivity(new Intent(this, UserProfile.class));
        }
    }
}