package com.michelle_condon.is4401_finalyearproject.UpdatePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class UpdateSchedule extends AppCompatActivity implements View.OnClickListener {

    private EditText week, name, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

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
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource Id's
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnEdit = findViewById(R.id.btnUpdateSchedule);

        //Based off documentation of transferring data between activities on the Android Developer website at "//https://developer.android.com/guide/components/intents-filters"
        Intent i = getIntent();
        String NAME = i.getStringExtra("employeeName");
        String var1 = i.getStringExtra("weekNumber");
        String var2 = i.getStringExtra("monday");
        String var3 = i.getStringExtra("tuesday");
        String var4 = i.getStringExtra("wednesday");
        String var5 = i.getStringExtra("thursday");
        String var6 = i.getStringExtra("friday");
        String var7 = i.getStringExtra("saturday");
        String var8 = i.getStringExtra("sunday");

        name = findViewById(R.id.txtUpdateEmployee);
        name.setText(NAME);
        week = findViewById(R.id.txtUpdateWeekNumber);
        week.setText(var1);
        monday = findViewById(R.id.txtUpdateMonday);
        monday.setText(var2);
        tuesday = findViewById(R.id.txtUpdateTuesday);
        tuesday.setText(var3);
        wednesday = findViewById(R.id.txtUpdateWednesday);
        wednesday.setText(var4);
        thursday = findViewById(R.id.txtUpdateThursday);
        thursday.setText(var5);
        friday = findViewById(R.id.txtUpdateFriday);
        friday.setText(var6);
        saturday = findViewById(R.id.txtUpdateSaturday);
        saturday.setText(var7);
        sunday = findViewById(R.id.txtUpdateSunday);
        sunday.setText(var8);
        //End
        reff = FirebaseDatabase.getInstance().getReference().child("EmployeeRoster");
        btnEdit.setOnClickListener(v -> {
            String updatedName = name.getText().toString();
            String updatedWeek = week.getText().toString();
            String updatedMonday = monday.getText().toString();
            String updatedTuesday = tuesday.getText().toString();
            String updatedWednesday = wednesday.getText().toString();
            String updatedThursday = thursday.getText().toString();
            String updatedFriday = friday.getText().toString();
            String updatedSaturday = saturday.getText().toString();
            String updatedSunday = sunday.getText().toString();

            //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz whihc can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
            HashMap hashMap = new HashMap();
            hashMap.put("employeeName", updatedName);
            hashMap.put("weekNumber", updatedWeek);
            hashMap.put("monday", updatedMonday);
            hashMap.put("tuesday", updatedTuesday);
            hashMap.put("wednesday", updatedWednesday);
            hashMap.put("thursday", updatedThursday);
            hashMap.put("friday", updatedFriday);
            hashMap.put("saturday", updatedSaturday);
            hashMap.put("sunday", updatedSunday);

            reff.child(updatedName + updatedWeek).updateChildren(hashMap).addOnSuccessListener(o -> Toast.makeText(UpdateSchedule.this, "Roster Updated", Toast.LENGTH_LONG).show());
            //End
        });

    }

    //Navigation bar methods
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, ManagementMainMenu.class));
    }

    private void employeeInfo() {
        startActivity(new Intent(this, UserProfile.class));
    }


    @Override
    public void onClick(View v) {
        //If statement for when a button is clicked
        if (v.getId() == R.id.btnDelete) {
            String nameWeek = (name.getText().toString() + week.getText().toString());
            reff.child(nameWeek).removeValue();
            Toast.makeText(UpdateSchedule.this, "Roster Deleted", Toast.LENGTH_LONG).show();
        }
    }
}
