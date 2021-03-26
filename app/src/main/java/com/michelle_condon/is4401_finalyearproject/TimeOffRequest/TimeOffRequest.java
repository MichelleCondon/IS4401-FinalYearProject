package com.michelle_condon.is4401_finalyearproject.TimeOffRequest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class TimeOffRequest extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView mShowSelectedDateText, txtDates, txtHolidayName;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_request);

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

        //Getting the current logged in user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Calender Picker Elements
        Button mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);
        txtDates = findViewById(R.id.txtDates);
        txtHolidayName = findViewById(R.id.txtEmpNameHoliday);

        //Creating an instance of the material date picker builder which makes sure to add the "dateRangePicker" which is the material date range picker which is the
        //second type of the date picker in material design date picker which we need to pass the pair of Long values, because the start date and end date is stored as a "Long" type value
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        //properties of the materialDateBuilder
        materialDateBuilder.setTitleText("SELECT A DATE");

        //instance of the material date picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        //select date button which opens the material design date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        //handle the positive button click from the material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive
                    // button that is ok button update the
                    // selected date
                    mShowSelectedDateText.setVisibility(View.VISIBLE);
                    txtDates.setVisibility(View.VISIBLE);
                    txtDates.setText(materialDatePicker.getHeaderText());
                    mShowSelectedDateText.setText("Selected Dates are:");

                });
    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    @Override
    public void onClick(View v) {
        //Pushing data to Firebase using a hash map
        reff = FirebaseDatabase.getInstance().getReference().child("TimeOffRequests");
        String employeeEmail = firebaseUser.getEmail();
        String dates = txtDates.getText().toString();
        String empHolidayName = txtHolidayName.getText().toString();
        String status = ("Pending");

        //Validation
        if (dates.equals("TextView")) {
            Toast.makeText(TimeOffRequest.this, "Your must select a series of dates in order to confirm your request", Toast.LENGTH_LONG).show();
        } else if (dates.equals("")) {
            Toast.makeText(TimeOffRequest.this, "Your must select a series of dates in order to confirm your request", Toast.LENGTH_LONG).show();
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("empHolidayName", empHolidayName);
            hashMap.put("employeeEmail", employeeEmail);
            hashMap.put("dates", dates);
            hashMap.put("status", status);

            reff.child(empHolidayName).setValue(hashMap);
            Toast.makeText(TimeOffRequest.this, "Your Time Off Request has been sent to Management", Toast.LENGTH_LONG).show();
            sendEmail();
        }
    }

    @SuppressLint("IntentReset")
    protected void sendEmail() {
        //Automated email to send to confirm time off days
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, user + " has requested the following days off: " + "\n" + txtDates.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeOffRequest.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}

