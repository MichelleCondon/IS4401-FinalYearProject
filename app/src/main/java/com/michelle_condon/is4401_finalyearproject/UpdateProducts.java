package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.databind.Module;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProducts extends AppCompatActivity {

    private EditText  name, description, quantity, price;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        Intent i = getIntent();
        String var = i.getStringExtra("Name");

        name = (EditText) findViewById(R.id.txtUpdateProductName);
        name.setText(var);
        description = (EditText) findViewById(R.id.txtUpdateProductDescription);
        quantity = (EditText) findViewById(R.id.txtUpdateQuantity);
        price = (EditText) findViewById(R.id.txtUpdatePrice);
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");




    }
}