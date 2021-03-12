package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    private EditText fullname, emailAddress, phoneNumber, position;
    private Button btnUpdate, btnAccount, btnDelete;
    public TextView employeeId;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_account:
                        account();
                        break;
                    case R.id.action_home:
                        home();
                        break;
                    case R.id.action_signout:
                        signout();
                        break;
                }
                return true;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        //Listening for the users button click for clock in/out
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        Intent i = getIntent();
        String NAME = i.getStringExtra("email");
        String var1 = i.getStringExtra("employeeId");
        String var2 = i.getStringExtra("fullname");
        String var3 = i.getStringExtra("phoneNumber");
        String var4 = i.getStringExtra("position");

        btnUpdate = (Button) findViewById(R.id.btnUpdateProfile);
        fullname = (EditText) findViewById(R.id.txtUpdateFullName);
        fullname.setText(NAME);
        emailAddress = (EditText) findViewById(R.id.txtUpdateEmailAddress);
        emailAddress.setText(var1);
        phoneNumber = (EditText) findViewById(R.id.txtUpdatedPhoneNumber);
        phoneNumber.setText(var2);
        position = (EditText) findViewById(R.id.txtUpdatedPosition);
        position.setText(var3);
        employeeId = (TextView) findViewById(R.id.txtUpdatedEmployeeId);
        employeeId.setText(var4);

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                String updatedName = fullname.getText().toString();
                String updatedEmail = emailAddress.getText().toString();
                String updatedPhone = phoneNumber.getText().toString();
                String updatedPosition = position.getText().toString();
                String updatedEmployeeId = employeeId.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("email", updatedEmail);
                hashMap.put("phoneNumber", updatedPhone);

                reff.child("xE5EaYIsc3ZWBBhl0gO5NPfCYek1").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditProfile.this, "Product Saved to Inventory", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

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
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnDelete){
            //String existingBarcode = barcode.getText().toString();
            reff.child("xE5EaYIsc3ZWBBhl0gO5NPfCYek1").removeValue();
        }
    }
    }
