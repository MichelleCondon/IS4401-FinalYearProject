package com.michelle_condon.is4401_finalyearproject.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.SignupScreen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube Video Login and Registration Android App Tutorial using Firebase Authentication - Login, CodeWithMazn, https://www.youtube.com/watch?v=KB2BIm_m1Os&t=336s (1)
    private EditText txtEmail, txtPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning values to variables with resource Id's
        TextView register = findViewById(R.id.lblRegister);
        register.setOnClickListener(this);

        Button signIn = findViewById(R.id.btnLogin);
        signIn.setOnClickListener(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        //Code below is based on the website Firebase Documentation, Google Firebase, https://firebase.google.com/docs/auth/android/password-auth (2)
        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
    }
    //End(2)

    @Override
    public void onClick(View v) {
        //If statement for when when buttons or text views are clicked
        if (v.getId() == R.id.lblRegister) {
            startActivity(new Intent(this, SignupScreen.class));
        } else if (v.getId() == R.id.btnLogin) {
            userLogin();
        }
    }

    private void userLogin() {
        //Assigns email and password to their associated variable
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            //If the login is successful the management menu is opened
            if (email.equals("admin@gmail.com") && (password.equals("admin1"))) {
                startActivity(new Intent(MainActivity.this, ManagementMainMenu.class));
            } else if (task.isSuccessful()) {
                startActivity(new Intent(MainActivity.this, MainMenu.class));
            } else {
                //If the login fails to authenticate an error message is displayed
                Toast.makeText(MainActivity.this, "Failed to login, please check your credentials", Toast.LENGTH_LONG).show();
            }
        });

    }
}
//End (1)
//End (2)
