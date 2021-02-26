package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSchedule extends AppCompatActivity {

    //Code below to insert data into Firebase is based on a YouTube Video, by EducaTree, https://www.youtube.com/watch?v=iy6WexahCdY&t=328

    //Declare Variables
    EditText txtEmployeename, txtMonday, txtTuesday, txtWednesday, txtThursday, txtFriday;
    Button btnAddHours;
    DatabaseReference reff;
    FetchEmployees fetchEmployees;
//Code below based on a tutorial by TutorialsPoint https://www.tutorialspoint.com/android/android_sending_email.htm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //Assigning values by resource ID
        txtEmployeename = (EditText) findViewById(R.id.txtEmpName);
        txtMonday = (EditText) findViewById(R.id.txtMonday);
        txtTuesday = (EditText) findViewById(R.id.txtTuesday);
        txtWednesday = (EditText) findViewById(R.id.txtWednesday);
        txtThursday = (EditText) findViewById(R.id.txtThursday);
        txtFriday = (EditText) findViewById(R.id.txtFriday);
        btnAddHours = (Button) findViewById(R.id.btnAddToSchedule);

        //Removed any wording from within the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        fetchEmployees = new FetchEmployees();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("EmployeeRoster");
        btnAddHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting the value of each variable to whatever the user enters on the form
                fetchEmployees.setEmployeeName(txtEmployeename.getText().toString().trim());
                fetchEmployees.setMonday(txtMonday.getText().toString().trim());
                fetchEmployees.setTuesday(txtTuesday.getText().toString().trim());
                fetchEmployees.setWednesday(txtWednesday.getText().toString().trim());
                fetchEmployees.setThursday(txtThursday.getText().toString().trim());
                fetchEmployees.setFriday(txtFriday.getText().toString().trim());
                reff.push().setValue(fetchEmployees);
                sendEmail();
                Toast.makeText(AddSchedule.this, "Data saved", Toast.LENGTH_LONG).show();
            }

        });
        //End

    }

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
}
//End

