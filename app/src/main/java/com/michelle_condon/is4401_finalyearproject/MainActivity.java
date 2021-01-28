package com.michelle_condon.is4401_finalyearproject;

//Import Statements
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning values by resource Id's - Register button
        register = (TextView) findViewById(R.id.register);
        //Listening for the users button click for register
        register.setOnClickListener(this);

        //Assigning values by resource Id's
        signIn = (Button) findViewById(R.id.signIn);
        //Listening for the users button click for register
        signIn.setOnClickListener(this);



        //Assigning values by resource Id's - Login page email and password text fields
        editTextEmail = (EditText) findViewById(R.id.txtemail);
        editTextPassword = (EditText) findViewById(R.id.txtPassword);


        mAuth = FirebaseAuth.getInstance();
    }
    //End(2)

    @Override
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Register Button
            case R.id.register:
                startActivity(new Intent(this, SignupScreen.class));
                break;
            //Sign in button
            case R.id.signIn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        //Email and password strings
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //Validation
        //Ensuring Email field is filled
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        //Ensuring email address is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is Incorrect, Please Check your Credentials");
            editTextEmail.requestFocus();
            return;
        }
        //Ensuring password field is filled
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        //Ensuring the password is longer than 6 characters
        if (password.length() < 6) {
            editTextPassword.setError("Password should be greater than 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        //Authenticating sign in to Firebase using email and password parameters
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //If the sign is a success
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, MainMenu.class));

                } else {
                    //If the sign in fails
                    Toast.makeText(MainActivity.this, "Login Failed Please Check Your Credentials!", Toast.LENGTH_LONG).show();
                }
                //End (1)
                //End (2)
            }
        });

    }
}
