package com.michelle_condon.is4401_finalyearproject;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.Hours;

public class TimeOffRequest extends AppCompatActivity {


    //Declare Variables
    EditText txtDay;
    Spinner mySpinner, mySpinner2;
    DatabaseReference reff;
    Button btnRequestTime, btnAccount;
    Hours hour;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_request);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mySpinner = (Spinner) findViewById(R.id.spinnerMonth);
        mySpinner2 = (Spinner) findViewById(R.id.spinnerDate);
        txtDay = (EditText) findViewById(R.id.txtDay);
        btnRequestTime = (Button) findViewById(R.id.btnRequestOff);
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setText(firebaseUser.getEmail());


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TimeOffRequest.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(TimeOffRequest.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);

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
                String date = mySpinner2.getSelectedItem().toString();
                String spinner = mySpinner.getSelectedItem().toString();
                alertDialog.setMessage("You have requested time off on " + date + "th of " + spinner);

                alertDialog.setButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        hour.setEmail(btnAccount.getText().toString());
                        String test = mySpinner.getSelectedItem().toString();
                        hour.setMonth(test);
                        String test2 = mySpinner2.getSelectedItem().toString();
                        hour.setDay(test2);
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
        String date = mySpinner2.getSelectedItem().toString();
        String month = mySpinner.getSelectedItem().toString();

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Request");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Management" + '\n' + '\n' +
                "A time off request has been made by: " + user +
                '\n' + '\n' + "They have requested the following day off: " + date + "th of " + month);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeOffRequest.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}