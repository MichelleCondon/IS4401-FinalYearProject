package com.michelle_condon.is4401_finalyearproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.User;

import java.util.HashMap;


public class SignupScreen extends AppCompatActivity implements View.OnClickListener {
    //Code below is based on the Youtube video "Login and Registration Android App Tutorial Using Firebase Authentication - Create User", CodeWithMazn,	https://www.youtube.com/watch?v=Z-RE1QuUWPg (1)

    //Declaring Variables
    private FirebaseAuth mAuth;
    private Button registerUser;
    private EditText txtFullName, txtEmployeeId, txtEmail, txtPassword, txtPhoneNumber, txtPosition;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //End (2)

        //Assigning values to variables with resource Id's - Register button
        registerUser = (Button) findViewById(R.id.btnRegister);
        //Listening for the button click for register
        registerUser.setOnClickListener(this);

        //Assigning Values by resourceId
        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtEmployeeId = (EditText) findViewById(R.id.txtEmployeeId);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPosition = (EditText) findViewById(R.id.txtPosition);
        txtPhoneNumber = (EditText) findViewById(R.id.txtPhone);

    }

    //OnClick Method
    @Override
    public void onClick(View v) {
        //Switch case statement
        switch (v.getId()) {
            //Calls the registerUser method whe the register button is clicked
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    //RegisterUser Method
    private void registerUser() {
        //Assigning values from text boxes to variables - 'Final' means the value can only be assigned once
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        final String fullName = txtFullName.getText().toString().trim();
        final String employeeId = txtEmployeeId.getText().toString().trim();
        final String position = txtPosition.getText().toString().trim();
        final String phoneNumber = txtPhoneNumber.getText().toString().trim();

        //Validation
        //Ensuring the name field is filled
        if (fullName.isEmpty()) {
            txtFullName.setError("Full name is required");
            txtFullName.requestFocus();
            return;
        }
        //Ensuring the employee id is filled
        if (employeeId.isEmpty()) {
            txtEmployeeId.setError("EmployeeID is Required");
            txtEmployeeId.requestFocus();
            return;
        }
        //Ensuring the email address field is filled
        if (email.isEmpty()) {
            txtEmail.setError("Email Address is Required");
            txtEmail.requestFocus();
            return;
        }
        //Ensuring a valid email is entered
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Invalid Email Address, Please Check Credentials");
            txtEmail.requestFocus();
            return;
        }
        //Ensuring the password field is filled
        if (password.isEmpty()) {
            txtPassword.setError("Password is Required");
            txtPassword.requestFocus();
            return;
        }
        //Ensuring the password provided is longer than 6 characters
        if (password.length() < 6) {
            txtPassword.setError("A Password Longer than 6 Characters is Required");
            txtPassword.requestFocus();
            return;
        }

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        //Create user with email and password in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If registration is successful a new user is created
                        if (task.isSuccessful()) {
                            String email = txtEmail.getText().toString();
                            String employeeId = txtEmployeeId.getText().toString();
                            String fullName = txtFullName.getText().toString();
                            String password = txtPassword.getText().toString();
                            String position = txtPosition.getText().toString();
                            String phoneNumber = txtPhoneNumber.getText().toString();

                            HashMap hashMap = new HashMap();
                            hashMap.put("email", email);
                            hashMap.put("employeeId", employeeId);
                            hashMap.put("fullName", fullName);
                            hashMap.put("password", password);
                            hashMap.put("position", position);
                            hashMap.put("phoneNumber", phoneNumber);

                            reff.child(fullName).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //If registration is successful a new user is created
                                    if (task.isSuccessful()) {
                                        //Success Toast appears and the login activity opens
                                        Toast.makeText(SignupScreen.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignupScreen.this, MainActivity.class));

                                    } else {
                                        //Failure toast appears
                                        Toast.makeText(SignupScreen.this, "Failed to Register", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            //Failure toast appears
                            Toast.makeText(SignupScreen.this, "Failed to Register", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
}
//End (1)
//End (2)


