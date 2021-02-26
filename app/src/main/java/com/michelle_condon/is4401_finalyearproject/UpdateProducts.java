package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.Items;

import java.util.HashMap;
import java.util.Map;

public class UpdateProducts extends AppCompatActivity {

    private EditText name, description, quantity, price;
    String NAME, DESC, QUANTITY, PRICE;
    private Button btnEdit;
    public Map<String, Boolean> stars = new HashMap<>();
    Items item;

    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


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
        String NAME = i.getStringExtra("Name");
        String var1 = i.getStringExtra("Desc");
        String var2 = i.getStringExtra("Quantity");
        String var3 = i.getStringExtra("Price");

        btnEdit = (Button) findViewById(R.id.btnEditStock);
        name = (EditText) findViewById(R.id.txtUpdateProductName);
        name.setText(NAME);
        description = (EditText) findViewById(R.id.txtUpdateProductDescription);
        description.setText(var1);
        quantity = (EditText) findViewById(R.id.txtUpdateQuantity);
        quantity.setText(var2);
        price = (EditText) findViewById(R.id.txtUpdatePrice);
        price.setText(var3);
        //databaseReference = FirebaseDatabase.getInstance().getReference("Items");


    }

}

