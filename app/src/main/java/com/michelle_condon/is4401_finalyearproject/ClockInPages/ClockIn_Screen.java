package com.michelle_condon.is4401_finalyearproject.ClockInPages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

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
import com.michelle_condon.is4401_finalyearproject.Models.Timesheets;
import com.michelle_condon.is4401_finalyearproject.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;

@SuppressWarnings("unchecked")
public class ClockIn_Screen extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube video "Biometric Authentication|Android Studio|Java", Atif Pervaiz, https://www.youtube.com/watch?v=yPcxZWSszh8

    //Declare Variables
    private TextView txtStartShift;
    private EditText txtClockInName;
    private TextView txtEndShift, txtBreak, txtEndBreak;
    public Button btnStartShift, btnEndShift, btnStartBreak, btnEndBreak, btnAuthenticate1, btnAuthenticate2, btnAuthenticate3, btnAuthenticate4, btnAccount;
    public Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    DatabaseReference reff;
    Timesheets timesheets;
    @SuppressLint("SimpleDateFormat")
    String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in__screen);

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

        //Account button in the tool bar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //assign values to variables using resource id
        btnStartShift = findViewById(R.id.btnStartShift);
        btnEndShift = findViewById(R.id.btnEndShift);
        btnEndBreak = findViewById(R.id.btnEndBreak);
        btnStartBreak = findViewById(R.id.btnStartBreak);
        btnAuthenticate1 = findViewById(R.id.btnAuthenticate1);
        btnAuthenticate2 = findViewById(R.id.btnAuthenticate2);
        btnAuthenticate3 = findViewById(R.id.btnAuthenticate3);
        btnAuthenticate4 = findViewById(R.id.btnAuthenticate4);
        txtStartShift = findViewById(R.id.txtStartShift);
        txtEndShift = findViewById(R.id.txtEndShift);
        txtBreak = findViewById(R.id.txtBreak);
        txtEndBreak = findViewById(R.id.txtEndBreak);
        txtClockInName = findViewById(R.id.txtClockInName);
        //Code below is based off code by JibW which can be found on StackOverflow at "https://stackoverflow.com/questions/17042430/android-edittexttextbox-auto-capitalizing-first-letter-of-each-word-while"
        txtClockInName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        //End

        //Code below is based on the website Developers, Android Developers, https://developer.android.com/training/sign-in/biometric-auth
        //initialise biometrics
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ClockIn_Screen.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //error authenticating
                btnAuthenticate1.setVisibility(View.INVISIBLE);
                btnAuthenticate2.setVisibility(View.INVISIBLE);
                btnAuthenticate3.setVisibility(View.INVISIBLE);
                btnAuthenticate4.setVisibility(View.INVISIBLE);
                Toast.makeText(ClockIn_Screen.this, "Authentication Error, Please contact your manager", Toast.LENGTH_SHORT).show();
            }

            //If biometric authentication succeeds
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //authentication succeed, continue tasks that require authentication
                Toast.makeText(ClockIn_Screen.this, "You have been successfully authenticated", Toast.LENGTH_SHORT).show();
            }

            //If biometric authentication fails
            @Override
            public void onAuthenticationFailed() {
                //Failure with authentication
                super.onAuthenticationFailed();
                btnAuthenticate1.setVisibility(View.INVISIBLE);
                btnAuthenticate2.setVisibility(View.INVISIBLE);
                btnAuthenticate3.setVisibility(View.INVISIBLE);
                btnAuthenticate4.setVisibility(View.INVISIBLE);
                Toast.makeText(ClockIn_Screen.this, "Authentication Failed, Please contact your manager", Toast.LENGTH_SHORT).show();
            }
        });

        //setup title description on authentication dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText(" ")
                .build();


        //When the button is clicked bring up the authentication prompt and the second authenticate button
        // while setting the text of the labels for insertion into Firebase
        btnStartShift.setOnClickListener(v -> {
            biometricPrompt.authenticate((promptInfo));
            btnAuthenticate1.setVisibility(View.VISIBLE);
            txtStartShift.setText(timeStamp);
            txtEndShift.setText("0");
            txtBreak.setText("0");
            txtEndBreak.setText("0");
            txtClockInName.setVisibility(View.VISIBLE);


        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnEndShift.setOnClickListener(v -> {
            biometricPrompt.authenticate((promptInfo));
            btnAuthenticate2.setVisibility(View.VISIBLE);
            txtEndShift.setText(timeStamp);
            txtStartShift.setText("0");
            txtBreak.setText("0");
            txtEndBreak.setText("0");
            txtClockInName.setVisibility(View.VISIBLE);


        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnStartBreak.setOnClickListener(v -> {
            biometricPrompt.authenticate((promptInfo));
            btnAuthenticate3.setVisibility(View.VISIBLE);
            txtBreak.setText(timeStamp);
            txtEndShift.setText("0");
            txtStartShift.setText("0");
            txtEndBreak.setText("0");
            txtClockInName.setVisibility(View.VISIBLE);

        });
        //When the button is clicked bring up the authentication prompt and the second authenticate button while setting the text of the labels for insertion into Firebase
        btnEndBreak.setOnClickListener(v -> {
            biometricPrompt.authenticate((promptInfo));
            btnAuthenticate4.setVisibility(View.VISIBLE);
            txtEndBreak.setText(timeStamp);
            txtEndShift.setText("0");
            txtBreak.setText("0");
            txtStartShift.setText("0");
            txtClockInName.setVisibility(View.VISIBLE);


        });


        timesheets = new Timesheets();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("Timesheet");

        //Clock in
        btnAuthenticate1.setOnClickListener(v -> {
            //Setting the value of each variable to whatever value is derived from the system timestamp
            // and the name the employee enters
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime now = LocalDateTime.now();
            String employee = (txtClockInName.getText().toString().trim());
            String title = (employee + "In" + dtf.format(now));
            String in = (txtStartShift.getText().toString().trim());
            String Break = ("0");
            String EndBreak = ("0");
            String out = ("0");
            String user = (firebaseUser.getEmail());

            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title)) {
                        Toast.makeText(ClockIn_Screen.this, "You have already clocked in today", Toast.LENGTH_LONG).show();
                    } else {
                        HashMap hashMap = new HashMap();
                        hashMap.put("employee", employee);
                        hashMap.put("in", in);
                        hashMap.put("break", Break);
                        hashMap.put("endbreak", EndBreak);
                        hashMap.put("out", out);
                        hashMap.put("user", user);

                        reff.child(title).setValue(hashMap);
                        Toast.makeText(ClockIn_Screen.this, "Clock In Completed", Toast.LENGTH_LONG).show();
                    }
                    btnAuthenticate1.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        //Clock out
        btnAuthenticate2.setOnClickListener(v -> {
            //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime now = LocalDateTime.now();
            String employee = (txtClockInName.getText().toString().trim());
            String title = (employee + "Out" + dtf.format(now));
            String in = ("0");
            String Break = ("0");
            String EndBreak = ("0");
            String out = (txtEndShift.getText().toString().trim());
            String user = (firebaseUser.getEmail());

            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title)) {
                        Toast.makeText(ClockIn_Screen.this, "You have already clocked out today", Toast.LENGTH_LONG).show();
                    } else {
                        HashMap hashMap = new HashMap();
                        hashMap.put("employee", employee);
                        hashMap.put("in", in);
                        hashMap.put("break", Break);
                        hashMap.put("endbreak", EndBreak);
                        hashMap.put("out", out);
                        hashMap.put("user", user);

                        reff.child(title).setValue(hashMap);
                        Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
                    }
                    btnAuthenticate2.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        //Clock out for break
        btnAuthenticate3.setOnClickListener(v -> {
            //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime now = LocalDateTime.now();
            String employee = (txtClockInName.getText().toString().trim());
            String title = (employee + "Break" + dtf.format(now));
            String in = ("0");
            String Break = (txtBreak.getText().toString().trim());
            String EndBreak = ("0");
            String out = ("0");
            String user = (firebaseUser.getEmail());

            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title)) {
                        Toast.makeText(ClockIn_Screen.this, "You have already clocked out for the start of your break today", Toast.LENGTH_LONG).show();
                    } else {

                        HashMap hashMap = new HashMap();
                        hashMap.put("employee", employee);
                        hashMap.put("in", in);
                        hashMap.put("break", Break);
                        hashMap.put("endbreak", EndBreak);
                        hashMap.put("out", out);
                        hashMap.put("user", user);

                        reff.child(title).setValue(hashMap);

                        Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
                    }
                    btnAuthenticate3.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        //Clock in for break
        btnAuthenticate4.setOnClickListener(v -> {
            //Setting the value of each variable to whatever value is derived from the system timestamp and the name the employee enters
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime now = LocalDateTime.now();
            String employee = (txtClockInName.getText().toString().trim());
            String title = (employee + "EndBreak" + dtf.format(now));
            String in = ("0");
            String Break = ("0");
            String EndBreak = (txtEndBreak.getText().toString().trim());
            String out = ("0");
            String user = (firebaseUser.getEmail());

            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title)) {
                        Toast.makeText(ClockIn_Screen.this, "You have already clocked back in from your break today", Toast.LENGTH_LONG).show();
                    } else {

                        HashMap hashMap = new HashMap();
                        hashMap.put("employee", employee);
                        hashMap.put("in", in);
                        hashMap.put("break", Break);
                        hashMap.put("endbreak", EndBreak);
                        hashMap.put("out", out);
                        hashMap.put("user", user);

                        reff.child(title).setValue(hashMap);
                        Toast.makeText(ClockIn_Screen.this, "Data saved", Toast.LENGTH_LONG).show();
                    }
                    btnAuthenticate3.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

    }


    //Navigation bar methods
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
        //Account button
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        }
    }
}
//End
//End