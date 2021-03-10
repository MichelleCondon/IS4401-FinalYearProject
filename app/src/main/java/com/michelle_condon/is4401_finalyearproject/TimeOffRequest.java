package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.Hours;

import java.util.Calendar;
import java.util.Date;

public class TimeOffRequest extends AppCompatActivity {


    //Declare Variables
    TextView txtStart, txtEnd;
    DatabaseReference reff;
    Button btnRequestTime, btnAccount;
    Hours hour;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CalendarView calenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_request);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnRequestTime = (Button) findViewById(R.id.btnRequestOff);
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setText(firebaseUser.getEmail());
        txtStart = (TextView)findViewById(R.id.txtStart);
        txtEnd = (TextView)findViewById(R.id.txtEnd);
        calenderView = (CalendarView) findViewById(R.id.calendarView);

        Date today = new Date();






        //Referencing the hours class
        hour = new Hours();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("TimeOffRequests");

        btnRequestTime.setOnClickListener(new View.OnClickListener() {
            //Alert Dialog prompt will appear when the request change button is clicked
            //Code below is based on the website Stack Overflow, Aman Alam, https://stackoverflow.com/questions/4850493/how-to-open-a-dialog-when-i-click-a-button

            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(TimeOffRequest.this).create(); //Read Update
                alertDialog.setTitle("Confirm Request");


                alertDialog.setButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        hour.setEmail(btnAccount.getText().toString());
                        reff.push().setValue(hour);
                        Toast.makeText(TimeOffRequest.this, "Change Request has been Submitted", Toast.LENGTH_LONG).show();
                        sendEmail();
                    }
                });
                alertDialog.show();
            }
            //End
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"117320951@umail.ucc.ie"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String user = firebaseUser.getEmail();

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Request");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "" );

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeOffRequest.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}