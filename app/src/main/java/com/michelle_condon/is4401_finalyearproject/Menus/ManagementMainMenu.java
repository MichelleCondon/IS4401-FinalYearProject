package com.michelle_condon.is4401_finalyearproject.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.AddPages.AddSchedule;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.TimeOffReview;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.ManagementViewEmployeeSchedule;
import com.michelle_condon.is4401_finalyearproject.ProductCheck.ProductCheck;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.Register.SignupScreen;

public class ManagementMainMenu extends AppCompatActivity implements View.OnClickListener {

    //Declaring Variables
    private TextView clockin;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_main_menu);

        //Code for the Navigation Bar is Based on a Tutorial "Bottom Navigation Bar in Android" by Geeks For Geeks which can be found at "https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/"
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_employeeInfo) {
                employeeInfo();
            } else if (item.getItemId() == R.id.action_home) {
                home();
            } else if (item.getItemId() == R.id.action_signout) {
                signout();
            }
            return true;
        });
        //End

        //Account button in the toolbar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource Id's
        Button btnAddSchedule = findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(this);

        Button btnAddEmployee = findViewById(R.id.btnAddProduct);
        btnAddEmployee.setOnClickListener(this);

        //Assigning values by resource Id's
        Button btnCreateList = findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(this);

        //Assigning values by resource Id's
        Button view = findViewById(R.id.btnViewSchedule);
        view.setOnClickListener(this);

        //Assigning values by resource Id's - View Inventory button
        TextView inventory = findViewById(R.id.btnViewInventory);
        inventory.setOnClickListener(this);

        //Assigning values by resource Id's - Product Check button
        TextView productCheck = findViewById(R.id.btnProductCheck);
        productCheck.setOnClickListener(this);
    }

    //Navigation Bar Methods
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
    }

    private void employeeInfo() {
        startActivity(new Intent(this, UserProfile.class));
    }

    @Override
    //OnClick method
    public void onClick(View v) {
        //if statement for when a button is clicked
        if (v.getId() == R.id.btnAddSchedule) {
            startActivity(new Intent(this, AddSchedule.class));
        } else if (v.getId() == R.id.btnViewInventory) {
            startActivity(new Intent(this, UserProfile.class));
        } else if (v.getId() == R.id.btnAddProduct) {
            startActivity(new Intent(this, SignupScreen.class));
        } else if (v.getId() == R.id.btnViewSchedule) {
            startActivity(new Intent(this, ManagementViewEmployeeSchedule.class));
        } else if (v.getId() == R.id.btnProductCheck) {
            startActivity(new Intent(this, ProductCheck.class));
        } else if (v.getId() == R.id.btnCreateList) {
            startActivity(new Intent(this, TimeOffReview.class));
        }
    }
}
