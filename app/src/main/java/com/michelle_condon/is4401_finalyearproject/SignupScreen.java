package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private FirebaseAuth mAuth;
    private EditText emailAddress, employeeId, passwordText;
    private TextView signupUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signupUser = (Button) findViewById(R.id.signupButton2);
        signupUser.setOnClickListener(this);

        emailAddress = (EditText) findViewById(R.id.emailAddress);
        employeeId = (EditText) findViewById(R.id.employeeId);
        passwordText = (EditText) findViewById(R.id.passwordText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.signupButton2:
        signupUser();
        break;
    }
    }

    private void signupUser() {
        final String email = emailAddress.getText().toString().trim();
        final String employeeid = employeeId.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(email.isEmpty()){
            emailAddress.setError("Email Address is Required");
            emailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("A Valid Email Address is Required");
            emailAddress.requestFocus();
            return;
        }
        if(employeeid.isEmpty()){
            employeeId.setError("Employee Id is Required");
            employeeId.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordText.setError("Password is Required");
            passwordText.requestFocus();
            return;
        }
        if(password.length() <6){
            passwordText.setError("Password must be more than 6 characters long");
            passwordText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email, employeeid);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupScreen.this, "User has been successfully registered", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(SignupScreen.this, "Failed to register", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(SignupScreen.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}

