package com.michelle_condon.is4401_finalyearproject.AddPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.FetchEmployees;
import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

public class AddSchedule extends AppCompatActivity implements View.OnClickListener{

    //Code below to insert data into Firebase is based on a YouTube Video, by EducaTree, https://www.youtube.com/watch?v=iy6WexahCdY&t=328

    //Declare Variables
    EditText txtWeekNumber, txtEmployeename, txtMonday, txtTuesday, txtWednesday, txtThursday, txtFriday, txtSaturday, txtSunday;
    Button btnAddHours, btnAccount;
    DatabaseReference reff;
    FetchEmployees fetchEmployees;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

//Code below based on a tutorial by TutorialsPoint https://www.tutorialspoint.com/android/android_sending_email.htm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
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

        //Buttons on the menu
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        btnAccount = (Button) findViewById(R.id.btnAccount);
        //Listening for the users button click for clock in/out
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource ID
        txtWeekNumber = (EditText) findViewById(R.id.txtWeekNumber);
        txtEmployeename = (EditText) findViewById(R.id.txtEmpName);
        txtMonday = (EditText) findViewById(R.id.txtMonday);
        txtTuesday = (EditText) findViewById(R.id.txtTuesday);
        txtWednesday = (EditText) findViewById(R.id.txtWednesday);
        txtThursday = (EditText) findViewById(R.id.txtThursday);
        txtFriday = (EditText) findViewById(R.id.txtFriday);
        txtSaturday = (EditText) findViewById(R.id.txtSaturday);
        txtSunday = (EditText) findViewById(R.id.txtSunday);
        btnAddHours = (Button) findViewById(R.id.btnAddToSchedule);


        fetchEmployees = new FetchEmployees();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("EmployeeRoster");
        btnAddHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekNumber = txtWeekNumber.getText().toString();
                String employee = txtEmployeename.getText().toString();
                String monday = txtMonday.getText().toString();
                String tuesday = txtTuesday.getText().toString();
                String wednesday = txtWednesday.getText().toString();
                String thursday = txtThursday.getText().toString();
                String friday = txtFriday.getText().toString();
                String saturday = txtSaturday.getText().toString();
                String sunday = txtSunday.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("weekNumber", weekNumber);
                hashMap.put("employeeName", employee);
                hashMap.put("monday", monday);
                hashMap.put("tuesday", tuesday);
                hashMap.put("wednesday", wednesday);
                hashMap.put("thursday", thursday);
                hashMap.put("friday", friday);
                hashMap.put("saturday", saturday);
                hashMap.put("sunday", sunday);

                reff.child(employee + weekNumber).setValue(hashMap);
                sendEmail();
                Toast.makeText(AddSchedule.this, "New Roster Saved", Toast.LENGTH_LONG).show();
            }

        });
        //End

    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {startActivity(new Intent(this, ManagementMainMenu.class)); }

    private void account(){ }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"117320951@umail.ucc.ie"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Roster Available");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi all," + '\n' +
                "A new roster has been released and is available to view within the app " +
                "once you search for your name in the schedule section." +
                '\n' + "Kind Regards," + '\n' +
                "Management");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddSchedule.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

    }
}
//End

