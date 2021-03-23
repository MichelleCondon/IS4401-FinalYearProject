package com.michelle_condon.is4401_finalyearproject.UpdatePages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

public class UpdateSchedule extends AppCompatActivity implements View.OnClickListener{

    private EditText week, name, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private Button btnEdit, btnAccount, btnDelete;
    public TextView barcode;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_employeeInfo:
                        account();
                        break;
                    case R.id.action_home:
                        home();
                        break;
                    case R.id.action_signout:
                        signout();
                        break;
                }
                return true;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnUpdateSchedule);
        //Listening for the users button click for clock in/out
        btnAccount.setText(firebaseUser.getEmail());

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

        name = (EditText) findViewById(R.id.txtUpdateEmployee);
        name.setText(NAME);
        week = (EditText) findViewById(R.id.txtUpdateWeekNumber);
        week.setText(var1);
        monday = (EditText) findViewById(R.id.txtUpdateMonday);
        monday.setText(var2);
        tuesday = (EditText) findViewById(R.id.txtUpdateTuesday);
        tuesday.setText(var3);
        wednesday = (EditText) findViewById(R.id.txtUpdateWednesday);
        wednesday.setText(var4);
        thursday = (EditText) findViewById(R.id.txtUpdateThursday);
        thursday.setText(var5);
        friday = (EditText) findViewById(R.id.txtUpdateFriday);
        friday.setText(var6);
        saturday = (EditText) findViewById(R.id.txtUpdateSaturday);
        saturday.setText(var7);
        sunday = (EditText) findViewById(R.id.txtUpdateSunday);
        sunday.setText(var8);

        reff = FirebaseDatabase.getInstance().getReference().child("EmployeeRoster");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                String updatedName = name.getText().toString();
                String updatedWeek = week.getText().toString();
                String updatedMonday = monday.getText().toString();
                String updatedTuesday = tuesday.getText().toString();
                String updatedWednesday = wednesday.getText().toString();
                String updatedThursday = thursday.getText().toString();
                String updatedFriday = friday.getText().toString();
                String updatedSaturday = saturday.getText().toString();
                String updatedSunday = sunday.getText().toString();

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

                reff.child(updatedName + updatedWeek).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateSchedule.this, "Roster Updated", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {startActivity(new Intent(this, ManagementMainMenu.class)); }

    private void account(){ }


    @Override
    public void onClick(View v) {
         if (v.getId() == R.id.btnDelete){
            String nameWeek = (name.getText().toString() + week.getText().toString());
            reff.child(nameWeek).removeValue();
        }
    }
    }
