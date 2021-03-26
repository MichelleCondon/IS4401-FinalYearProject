package com.michelle_condon.is4401_finalyearproject.RequestChange;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Models.Hours;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class HoursRequest extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    EditText txtHours;
    EditText txtName;
    DatabaseReference reff;
    Hours hour;
    Button btnAccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_request);

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

        //Used for the account button based off the logged in user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Assigning values by resource ID
        SwitchCompat switchMorning = findViewById(R.id.switchMorning);
        SwitchCompat switchEvening = findViewById(R.id.switchEvening);

        Spinner spinnerDate = findViewById(R.id.spinnerDate);
        Spinner spinnerMonth = findViewById(R.id.spinnerMonth);
        Spinner spinnerDay = findViewById(R.id.spinnerDay);

        txtHours = findViewById(R.id.txtHours);
        Button btnRequestHours = findViewById(R.id.btnRequestChange);
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setText(firebaseUser.getEmail());
        txtName = findViewById(R.id.txtRequestName);

        //Drop downs for all three spinners
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(HoursRequest.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<>(HoursRequest.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.date));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<>(HoursRequest.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.weekDays));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(myAdapter3);


        //Referencing the hours class
        hour = new Hours();

        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("HourChangeRequests");

        //Alert Dialog prompt will appear when the request change button is clicked
        //Code below is based on the website Stack Overflow, by Aman Alam, https://stackoverflow.com/questions/4850493/how-to-open-a-dialog-when-i-click-a-button
        btnRequestHours.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(HoursRequest.this).create(); //Read Update
            alertDialog.setTitle("Confirm Request");
            alertDialog.setMessage("Are you sure you want to request a change to your hours?");

            alertDialog.setButton("Confirm", (dialog, which) -> {

                if (switchMorning.isChecked()) {
                    String textDate = spinnerDate.getSelectedItem().toString();
                    String textMonth = spinnerMonth.getSelectedItem().toString();
                    String textDay = spinnerDay.getSelectedItem().toString();
                    String textHours = txtHours.getText().toString();
                    String textName = txtName.getText().toString();

                    //Validation
                    if (textDate.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a date to continue", Toast.LENGTH_LONG).show();
                        spinnerDay.requestFocus();
                        return;
                    }
                    if (textMonth.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a month to continue", Toast.LENGTH_LONG).show();
                        spinnerMonth.requestFocus();
                        return;
                    }
                    if (textDay.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a day to continue", Toast.LENGTH_LONG).show();
                        spinnerDay.requestFocus();
                        return;
                    }
                    if (textHours.isEmpty()) {
                        txtHours.setError("You must fill out the hours you wish to change to continue");
                        txtHours.requestFocus();
                        return;
                    }
                    if (textName.isEmpty()) {
                        txtName.setError("You must fill out your name to continue");
                        txtName.requestFocus();
                        return;
                    }
                    if (textDate.equals("30") && textMonth.equals("February")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in February", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("February")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in February", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("April")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in April", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("June")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in June", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("September")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in September", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("November")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in November", Toast.LENGTH_LONG).show();
                        return;
                    }
                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        //Validation to check if request has already been made
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(textName + textDate + textMonth)) {
                                Toast.makeText(HoursRequest.this, "You have already sent a change request for this date, please contact management", Toast.LENGTH_LONG).show();
                            } else {
                                String email = btnAccount.getText().toString();
                                String date = spinnerDate.getSelectedItem().toString();
                                String month = spinnerMonth.getSelectedItem().toString();
                                String weekDay = spinnerDay.getSelectedItem().toString();
                                String hours = txtHours.getText().toString();
                                String name = txtName.getText().toString();
                                String preference = ("Morning");

                                //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz whihc can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
                                HashMap hashMap = new HashMap();
                                hashMap.put("email", email);
                                hashMap.put("date", date);
                                hashMap.put("day", weekDay);
                                hashMap.put("month", month);
                                hashMap.put("hours", hours);
                                hashMap.put("preference", preference);
                                hashMap.put("name", name);

                                reff.child(name + date + month).setValue(hashMap);
                                Toast.makeText(HoursRequest.this, "Change Request has been Submitted", Toast.LENGTH_LONG).show();
                                sendEmail();
                                //End
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (switchEvening.isChecked()) {
                    String textDate = spinnerDate.getSelectedItem().toString();
                    String textMonth = spinnerMonth.getSelectedItem().toString();
                    String textDay = spinnerDay.getSelectedItem().toString();
                    String textHours = txtHours.getText().toString();
                    String textName = txtName.getText().toString();

                    //Validation
                    if (textDate.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a date to continue", Toast.LENGTH_LONG).show();
                        spinnerDay.requestFocus();
                        return;
                    }
                    if (textMonth.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a month to continue", Toast.LENGTH_LONG).show();
                        spinnerMonth.requestFocus();
                        return;
                    }
                    if (textDay.isEmpty()) {
                        Toast.makeText(HoursRequest.this, "You must select a day to continue", Toast.LENGTH_LONG).show();
                        spinnerDay.requestFocus();
                        return;
                    }
                    if (textHours.isEmpty()) {
                        txtHours.setError("You must fill out the hours you wish to change to continue");
                        txtHours.requestFocus();
                        return;
                    }
                    if (textName.isEmpty()) {
                        txtName.setError("You must fill out your name to continue");
                        txtName.requestFocus();
                        return;
                    }
                    if (textDate.equals("30") && textMonth.equals("February")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in February", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("February")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in February", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("April")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in April", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("June")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in June", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("September")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in September", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (textDate.equals("31") && textMonth.equals("November")) {
                        Toast.makeText(HoursRequest.this, "Please select a valid date in November", Toast.LENGTH_LONG).show();
                        return;
                    }
                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        //Validation to check if request has already been made
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(textName + textDate + textMonth)) {
                                Toast.makeText(HoursRequest.this, "You have already sent a change request for this date, please contact management", Toast.LENGTH_LONG).show();
                            } else {
                                //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz which can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
                                String email = btnAccount.getText().toString();
                                String date = spinnerDate.getSelectedItem().toString();
                                String day = spinnerDay.getSelectedItem().toString();
                                String month = spinnerMonth.getSelectedItem().toString();
                                String hours = txtHours.getText().toString();
                                String name = txtName.getText().toString();
                                String preference = ("Evening");

                                HashMap hashMap = new HashMap();
                                hashMap.put("email", email);
                                hashMap.put("date", date);
                                hashMap.put("day", day);
                                hashMap.put("month", month);
                                hashMap.put("hours", hours);
                                hashMap.put("preference", preference);
                                hashMap.put("name", name);

                                reff.child(name + date + month).setValue(hashMap);
                                Toast.makeText(HoursRequest.this, "Change Request has been Submitted", Toast.LENGTH_LONG).show();
                                sendEmail();
                                //End
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else if (!switchEvening.isChecked() && !switchMorning.isChecked()) {
                    Toast.makeText(HoursRequest.this, "You must select a shift preference to continue", Toast.LENGTH_LONG).show();
                } else if (switchEvening.isChecked() && switchMorning.isChecked()) {
                    Toast.makeText(HoursRequest.this, "You can only choose one shift preference", Toast.LENGTH_LONG).show();
                }
            });
            alertDialog.show();
        });
    }

    //Navigation Bar methods
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    //Code to send emails is from Tutorials Point which can be found at "https://www.tutorialspoint.com/android/android_sending_email.htm"
    @SuppressLint("IntentReset")
    protected void sendEmail() {
        //Automated email to send to confirm hour change
        Log.i("Send email", "");
        //Using my student email as the 'admin' email address
        String[] TO = {"117320951@umail.ucc.ie"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String user = firebaseUser.getEmail();
        Spinner spinnerDate = findViewById(R.id.spinnerDate);
        Spinner spinnerMonth = findViewById(R.id.spinnerMonth);
        String date = spinnerDate.getSelectedItem().toString();
        String month = spinnerMonth.getSelectedItem().toString();

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Request");
        emailIntent.putExtra(Intent.EXTRA_TEXT, user + " has requested a change to their hours on: " + date + (" ") + month);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HoursRequest.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
//End

    @Override
    public void onClick(View v) {
        //If statement for if a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        }
    }
}