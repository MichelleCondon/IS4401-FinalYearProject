package com.michelle_condon.is4401_finalyearproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.UserProfile;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.ManagementMainMenu;

import java.util.HashMap;
import java.util.Objects;


@SuppressWarnings("unchecked")
public class SignupScreen extends AppCompatActivity implements View.OnClickListener {
    //Code below is based on the Youtube video "Login and Registration Android App Tutorial Using Firebase Authentication - Create User", CodeWithMazn,	https://www.youtube.com/watch?v=Z-RE1QuUWPg (1)

    //Declaring Variables
    private FirebaseAuth mAuth;
    private EditText txtFullName, txtEmployeeId, txtEmail, txtPassword, txtPhoneNumber, txtPosition;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //End (2)

        //Assigning values to variables with resource Id's - Register button
        Button registerUser = findViewById(R.id.btnRegister);
        //Listening for the button click for register
        registerUser.setOnClickListener(this);

        //Assigning Values by resourceId
        txtFullName = findViewById(R.id.txtFullName);
        txtEmployeeId = findViewById(R.id.txtEmployeeId);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtPosition = findViewById(R.id.txtPosition);
        txtPhoneNumber = findViewById(R.id.txtPhone);

    }

    @Override
    public void onClick(View v) {
        //Calls the registerUser method whe the register button is clicked
        if (v.getId() == R.id.btnRegister) {
            registerUser();
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
        if (position.isEmpty()) {
            txtPosition.setError("Position is required");
            txtPosition.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            txtPhoneNumber.setError("Phone Number is required");
            txtPhoneNumber.requestFocus();
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
                .addOnCompleteListener(task -> {
                    //If registration is successful a new user is created
                    if (task.isSuccessful()) {
                        String email1 = txtEmail.getText().toString();
                        String employeeId1 = txtEmployeeId.getText().toString();
                        String fullName1 = txtFullName.getText().toString();
                        String password1 = txtPassword.getText().toString();
                        String position1 = txtPosition.getText().toString();
                        String phoneNumber1 = txtPhoneNumber.getText().toString();

                        HashMap hashMap = new HashMap();
                        hashMap.put("email", email1);
                        hashMap.put("employeeId", employeeId1);
                        hashMap.put("fullName", fullName1);
                        hashMap.put("password", password1);
                        hashMap.put("position", position1);
                        hashMap.put("phoneNumber", phoneNumber1);

                        reff.child(fullName1).setValue(hashMap).addOnCompleteListener(task1 -> {
                            //If registration is successful a new user is created
                            if (task1.isSuccessful()) {
                                //Success Toast appears and the login activity opens
                                Toast.makeText(SignupScreen.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupScreen.this, MainActivity.class));

                            } else {
                                //Failure toast appears
                                Toast.makeText(SignupScreen.this, "Failed to Register", Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        //Failure toast appears
                        Toast.makeText(SignupScreen.this, "Failed to Register", Toast.LENGTH_LONG).show();

                    }

                });
    }
}
//End (1)
//End (2)


