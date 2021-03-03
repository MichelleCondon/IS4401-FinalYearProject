package com.michelle_condon.is4401_finalyearproject;

//Import Statements

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Firebase Imports
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.Items;

//Importing the scanResult variable from the BarcodeScanner.java class
import java.util.HashMap;

import static com.michelle_condon.is4401_finalyearproject.BarcodeScanner.scanResult;


public class AddItems extends AppCompatActivity implements View.OnClickListener {
    //Code below to insert data into Firebase is based on a YouTube Video, by EducaTree, https://www.youtube.com/watch?v=iy6WexahCdY&t=328

    //Declare Variables
    EditText txtName, txtDescription, txtPrice, txtQuantity;
    TextView barcodeRef;
    Button btnSave, lblAccount;
    DatabaseReference reff;
    Items item;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

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
        //Assigning values by resource ID
        txtName = (EditText) findViewById(R.id.txtName);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtQuantity = (EditText) findViewById(R.id.txtQuantity);
        barcodeRef = (TextView) findViewById(R.id.barcodeRef);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        btnSave = (Button) findViewById(R.id.btnAddToSchedule);
        barcodeRef = (TextView) findViewById(R.id.barcodeRef);

        //https://suragch.medium.com/how-to-add-a-bottom-navigation-bar-in-android-958ed728ef6c
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        lblAccount = (Button) findViewById(R.id.btnAccount);
        //Listening for the users button click for clock in/out
        lblAccount.setOnClickListener(this);
        lblAccount.setText(firebaseUser.getEmail());



        //Removed any wording from within the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        //Setting the text of the barcode text view with the scanned value from the barcode scanner
        barcodeRef.setText(scanResult);

        item = new Items();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String description = txtDescription.getText().toString();
                String barcode = barcodeRef.getText().toString();
                String price = txtPrice.getText().toString();
                String quantity = txtQuantity.getText().toString();


                HashMap hashMap = new HashMap();
                hashMap.put("barcode", barcode);
                hashMap.put("name", name);
                hashMap.put("description", description);
                hashMap.put("price", price);
                hashMap.put("quantity", quantity);

                reff.child(barcode).setValue(hashMap);
                Toast.makeText(AddItems.this, "Product Saved to Inventory", Toast.LENGTH_LONG).show();
            }
        });
        //End


    }

    private void signout() {
        finish();
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
        }
    }
}