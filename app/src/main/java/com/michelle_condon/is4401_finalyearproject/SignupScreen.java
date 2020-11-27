package com.michelle_condon.is4401_finalyearproject;

//Import Statements
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignupScreen extends AppCompatActivity implements View.OnClickListener{
    //Code below is based on the Youtube video "Login and Registration Android App Tutorial Using Firebase Authentication - Create User", CodeWithMazn,	https://www.youtube.com/watch?v=Z-RE1QuUWPg (1)
    //Declaring Variables
    private FirebaseAuth mAuth;
    private TextView registerUser;
    private TextView editTextFullName, editTextEmployeeId, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //End (2)

        //Assigning values by resource Id - register
        registerUser = (Button) findViewById(R.id.registerUser);
        //Listening for the users button click - register
        registerUser.setOnClickListener(this);

        //Assigning Values by resourceId
        editTextFullName = (EditText) findViewById(R.id.fullName); //Full Name
        editTextEmployeeId = (EditText) findViewById(R.id.EmployeeId); //EmployeeId
        editTextEmail = (EditText) findViewById(R.id.txtemail); //Email
        editTextPassword = (EditText) findViewById(R.id.txtPassword); //Password
        progressBar = (ProgressBar) findViewById(R.id.progressBar); //Progress Bar
    }

    @Override
    public void onClick(View v) {
        //Java Switch case statement
        switch (v.getId()) {
            //Registration button
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        //Getting text from the below textboxes
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String fullName = editTextFullName.getText().toString().trim();
        final String employeeId = editTextEmployeeId.getText().toString().trim();

        //Ensuring the name field is filled
        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name required");
            editTextFullName.requestFocus();
            return;
        }
        //Ensuring the employee id is filled
        if (employeeId.isEmpty()) {
            editTextEmployeeId.setError("Age Required");
            editTextEmployeeId.requestFocus();
            return;
        }
        //Ensuring the email field is filled
        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        //Ensuring a valid email is entered
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        //Ensuring a password is provided
        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }
        //Ensuring the password provided is bigger than 6 characters
        if (password.length() < 6) {
            editTextPassword.setError("Password greater than 6 characters required");
            editTextPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, employeeId, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Successful Registration
                                        Toast.makeText(SignupScreen.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignupScreen.this, MainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        //Failure to register
                                        Toast.makeText(SignupScreen.this, "Failed to register", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            //Failure to register
                            Toast.makeText(SignupScreen.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        //End (1)
                        //End (2)
                    }
                });
    }
}


