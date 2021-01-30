package com.michelle_condon.is4401_finalyearproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Code below is based on the Youtube Video Login and Registration Android App Tutorial using Firebase Authentication - Login, CodeWithMazn, https://www.youtube.com/watch?v=KB2BIm_m1Os&t=336s (1)
    //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)

    //Declaring Variables
    private TextView register;
    private EditText txtEmail, txtPassword;
    private Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning values to variables with resource Id's - Register TextView
        register = (TextView) findViewById(R.id.lblRegister);
        //Listening for the text view click for register
        register.setOnClickListener(this);

        //Assigning values to variables with resource Id's - Login Button
        signIn = (Button) findViewById(R.id.btnLogin);
        //Listening for the users button click for login
        signIn.setOnClickListener(this);

        //Assigning values to variables with resource Id's - email and password text boxes
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        //Entry point of Firebase authentication
        mAuth = FirebaseAuth.getInstance();
    }
    //End(2)

    //OnClick Method
    @Override
    public void onClick(View v) {
        //Switch Case statement
        switch (v.getId()) {
            //If the register label is clicked the sign up screen opens
            case R.id.lblRegister:
                startActivity(new Intent(this, SignupScreen.class));
                break;
            //If the login button is clicked the userLogin() method is called
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        //Assigns email and password to their associated variable
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        //Validation
        //Ensuring email field is filled
        if (email.isEmpty()) {
            txtEmail.setError("Email Field is Required");
            txtEmail.requestFocus();
            return;
        }
        //Ensuring email address is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Invalid Email Address");
            txtEmail.requestFocus();
            return;
        }
        //Ensuring password field is filled
        if (password.isEmpty()) {
            txtPassword.setError("Password Field is required");
            txtPassword.requestFocus();
            return;
        }
        //Ensuring the password is longer than 6 characters
        if (password.length() < 6) {
            txtPassword.setError("Password needs to be longer than 6 characters");
            txtPassword.requestFocus();
            return;
        }

        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)

        //Authenticating sign in to Firebase using email and password parameters (Inbuilt function)
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //If the login is successful the menu is opened
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, MainMenu.class));

                } else {
                    //If the login fails to authenticate an error message is displayed
                    Toast.makeText(MainActivity.this, "Failed to Login, please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
//End (1)
//End (2)
