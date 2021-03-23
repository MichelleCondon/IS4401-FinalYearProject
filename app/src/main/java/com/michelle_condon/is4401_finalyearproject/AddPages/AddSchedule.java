package com.michelle_condon.is4401_finalyearproject.AddPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.Models.FetchEmployees;
import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

public class AddSchedule extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    EditText txtWeekNumber, txtEmployeename, txtMonday, txtTuesday, txtWednesday, txtThursday, txtFriday, txtSaturday, txtSunday;
    Button btnAddHours, btnAccount;
    DatabaseReference reff;
    FetchEmployees fetchEmployees;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //Code for the Navigation Bar is Based on a Tutorial "Bottom Navigation Bar in Android" by Geeks For Geeks which can be found at "https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/"
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_employeeInfo) {
                account();
            } else if (item.getItemId() == R.id.action_home) {
                home();
            } else if (item.getItemId() == R.id.action_signout) {
                signout();
            }
            return true;
        });
        //End

        //Account Button
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values to variables by resource ID
        txtWeekNumber = findViewById(R.id.txtWeekNumber);
        txtEmployeename = findViewById(R.id.txtEmpName);
        txtMonday = findViewById(R.id.txtMonday);
        txtTuesday = findViewById(R.id.txtTuesday);
        txtWednesday = findViewById(R.id.txtWednesday);
        txtThursday = findViewById(R.id.txtThursday);
        txtFriday = findViewById(R.id.txtFriday);
        txtSaturday = findViewById(R.id.txtSaturday);
        txtSunday = findViewById(R.id.txtSunday);
        btnAddHours = findViewById(R.id.btnAddToSchedule);

        fetchEmployees = new FetchEmployees();
        //Accessing data from Firebase Item Employee Roster
        reff = FirebaseDatabase.getInstance().getReference().child("EmployeeRoster");
        btnAddHours.setOnClickListener(v -> {
            //Text box Validation - ensuring no text boxes are left blank
            String vWeekNumber = txtWeekNumber.getText().toString();
            String vEmployeeName = txtEmployeename.getText().toString();
            String vMonday = txtMonday.getText().toString();
            String vTuesday = txtTuesday.getText().toString();
            String vWednesday = txtWednesday.getText().toString();
            String vThursday = txtThursday.getText().toString();
            String vFriday = txtFriday.getText().toString();
            String vSaturday = txtSaturday.getText().toString();
            String vSunday = txtSunday.getText().toString();

            if (vWeekNumber.isEmpty()) {
                txtWeekNumber.setError("Week Number is Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vEmployeeName.isEmpty()) {
                txtWeekNumber.setError("Employee Name is Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vMonday.isEmpty()) {
                txtWeekNumber.setError("Monday Hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vTuesday.isEmpty()) {
                txtWeekNumber.setError("Tuesday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vWednesday.isEmpty()) {
                txtWeekNumber.setError("Wednesday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vThursday.isEmpty()) {
                txtWeekNumber.setError("Thursday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vFriday.isEmpty()) {
                txtWeekNumber.setError("Friday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vSaturday.isEmpty()) {
                txtWeekNumber.setError("Saturday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }
            if (vSunday.isEmpty()) {
                txtWeekNumber.setError("Sunday hours are Required");
                txtWeekNumber.requestFocus();
                return;
            }

            //Validation to ensure the roster doesn't already exist in Firebase
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressWarnings("unchecked")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(txtEmployeename.getText().toString() + txtWeekNumber.getText().toString())) {
                        Toast.makeText(AddSchedule.this, "This Schedule Already Exists, Please Update if you Need to Make Changes", Toast.LENGTH_LONG).show();
                    } else {
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
                        Toast.makeText(AddSchedule.this, ("New Roster has been added for: " + txtEmployeename.getText().toString()), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddSchedule.this, ("Roster has not been Created"), Toast.LENGTH_LONG).show();
                }
            });

        });


    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, ManagementMainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, UserProfile.class));
    }

    //Code below based on a tutorial by TutorialsPoint https://www.tutorialspoint.com/android/android_sending_email.htm
    @SuppressLint("IntentReset")
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
    //End

    @Override
    public void onClick(View v) {
        //Does Nothing
    }
}


