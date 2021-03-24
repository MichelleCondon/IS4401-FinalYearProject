package com.michelle_condon.is4401_finalyearproject.Menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.BarcodeScanner.BarcodeScanner;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.DisplayItems;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.EmployeeSchedule;
import com.michelle_condon.is4401_finalyearproject.GoogleMaps.MapsActivity;
import com.michelle_condon.is4401_finalyearproject.List.VirtualList;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.ProductCheck.ProductCheck;
import com.michelle_condon.is4401_finalyearproject.R;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    //Declaring Variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Code for the Navigation Bar is Based on a Tutorial "Bottom Navigation Bar in Android" by Geeks For Geeks which can be found at "https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/"
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

        //Account button based on Firebase login
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource Id's
        TextView clockin = findViewById(R.id.btnClockIn);
        clockin.setOnClickListener(this);

        Button btnAddItem = findViewById(R.id.btnAddProduct);
        btnAddItem.setOnClickListener(this);

        Button btnCreateList = findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(this);

        Button btnViewSchedule = findViewById(R.id.btnViewSchedule);
        btnViewSchedule.setOnClickListener(this);

        Button inventory = findViewById(R.id.btnViewInventory);
        inventory.setOnClickListener(this);

        Button productCheck = findViewById(R.id.btnProductCheck);
        productCheck.setOnClickListener(this);
    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    @Override
    public void onClick(View v) {
        //If statements for when buttons are clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnClockIn) {
            startActivity(new Intent(this, MapsActivity.class));
        } else if (v.getId() == R.id.btnViewInventory) {
            startActivity(new Intent(this, DisplayItems.class));
        } else if (v.getId() == R.id.btnAddProduct) {
            startActivity(new Intent(this, BarcodeScanner.class));
        } else if (v.getId() == R.id.btnViewSchedule) {
            startActivity(new Intent(this, EmployeeSchedule.class));
        } else if (v.getId() == R.id.btnProductCheck) {
            startActivity(new Intent(this, ProductCheck.class));
        } else if (v.getId() == R.id.btnCreateList) {
            startActivity(new Intent(this, VirtualList.class));
        }
    }
}