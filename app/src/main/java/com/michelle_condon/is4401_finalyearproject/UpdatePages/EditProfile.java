package com.michelle_condon.is4401_finalyearproject.UpdatePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    private EditText emailAddress, phoneNumber;
    public TextView employeeId, fullname, position;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        //Account Button using the logged in email from Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource Id's
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        //Based off code from the Android Developers website which can be found at "https://developer.android.com/guide/components/intents-filters"
        //Data transfer from the user profile page
        Intent i = getIntent();
        String NAME = i.getStringExtra("email");
        String var1 = i.getStringExtra("employeeId");
        String var2 = i.getStringExtra("fullName");
        String var3 = i.getStringExtra("phoneNumber");
        String var4 = i.getStringExtra("position");

        //Setting the text on the page
        Button btnUpdate = findViewById(R.id.btnUpdateProfile);
        fullname = findViewById(R.id.txtUpdateFullName);
        fullname.setText(var2);
        emailAddress = findViewById(R.id.txtUpdateEmailAddress);
        emailAddress.setText(NAME);
        phoneNumber = findViewById(R.id.txtUpdatedPhoneNumber);
        phoneNumber.setText(var3);
        position = findViewById(R.id.txtUpdatedPosition);
        position.setText(var4);
        employeeId = findViewById(R.id.txtUpdatedEmployeeId);
        employeeId.setText(var1);

        //Making the delete button visible if it's the admin who is logged in
        if (Objects.equals(firebaseUser.getEmail(), "admin@gmail.com")) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
        }

        //Update button actions
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        btnUpdate.setOnClickListener(v -> {
            String updatedEmail = emailAddress.getText().toString();
            String updatedPhone = phoneNumber.getText().toString();
            //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz whihc can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
            HashMap hashMap = new HashMap();
            hashMap.put("email", updatedEmail);
            hashMap.put("phoneNumber", updatedPhone);

            reff.child(fullname.getText().toString()).updateChildren(hashMap).addOnSuccessListener(o -> Toast.makeText(EditProfile.this, "User Details Updated", Toast.LENGTH_LONG).show());
            //End
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
        //If statement for when buttons are clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnDelete) {
            String name = fullname.getText().toString();
            reff.child(name).removeValue();
        }
    }
}
