package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.TimeOffReview;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.UpdatePages.UpdateProducts;
import com.michelle_condon.is4401_finalyearproject.UpdatePages.UpdateSchedule;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Review extends AppCompatActivity implements View.OnClickListener{

    //Declare Variables
    Button btnAccept, btnReject;
    public TextView empNames;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

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
        Button btnAccept =  findViewById(R.id.btnApprove);
        Button btnReject =  findViewById(R.id.btnDeny);

        //Data that was passed from the display item form
        Intent i = getIntent();
        String var1 = i.getStringExtra("dates");
        String var2 = i.getStringExtra("email");
        String var3 = i.getStringExtra("empHolidayName");
        String var4 = i.getStringExtra("status");

        TextView date = findViewById(R.id.txtReviewedDates);
        date.setText(var1);
        TextView email = findViewById(R.id.txtReviewEmail);
        email.setText(var2);
        empNames = findViewById(R.id.txtEmpNames);
        empNames.setText(var3);

        reff = FirebaseDatabase.getInstance().getReference().child("TimeOffRequests");

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

    @SuppressLint("IntentReset")
    protected void sendEmailApproval() {
        TextView email = findViewById(R.id.txtReviewEmail);
        String reviewedEmail = email.getText().toString();

        Log.i("Send email", "");
        String[] TO = {reviewedEmail};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Approved");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your time off request has been approved and your roster will be updated shortly" + "\n" + "Thanks," + "\n" + "Management");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Review.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("IntentReset")
    protected void sendEmailDenial() {
        TextView email = findViewById(R.id.txtReviewEmail);
        String reviewedEmail = email.getText().toString();
        Log.i("Send email", "");
        String[] TO = {reviewedEmail};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Rejected");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Unfortunately your time off request could not be approved by management at this time" + "\n" + "Thanks," + "\n" + "Management");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Review.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {
            String empName = empNames.getText().toString();
            reff.child(empName).removeValue();
            Toast.makeText(Review.this, "Request Approved", Toast.LENGTH_LONG).show();
            sendEmailApproval();
        } else if (v.getId() == R.id.btnDeny) {
            String empName = empNames.getText().toString();
            reff.child(empName).removeValue();
            Toast.makeText(Review.this, "Request Denied", Toast.LENGTH_LONG).show();
            sendEmailDenial();
        }
        }
    }

