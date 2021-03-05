package com.michelle_condon.is4401_finalyearproject;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.Items;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UpdateProducts extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube Video Android Firebase - 9 - Read & Write Data in key Value Pairs | Hashmap & Map Data Read Write, Technical Skillz, "https://www.youtube.com/watch?v=SGiY_AitrN0"
    //Code below is based on the Youtube Video Android Firebase - 10 - How Update Data in Firebase Realtime Database, Technical Skillz, "https://www.youtube.com/watch?v=0HLyJNuyhSo"

    private EditText name, description, quantity, price;
    private Button btnEdit, btnAccount;
    public TextView barcode;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);

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
        //Listening for the users button click for clock in/out
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        Intent i = getIntent();
        String NAME = i.getStringExtra("Name");
        String var1 = i.getStringExtra("Desc");
        String var2 = i.getStringExtra("Quantity");
        String var3 = i.getStringExtra("Price");
        String var4 = i.getStringExtra("Barcode");

        btnEdit = (Button) findViewById(R.id.btnUpdateInventory);
        name = (EditText) findViewById(R.id.txtUpdateProductName);
        name.setText(NAME);
        description = (EditText) findViewById(R.id.txtUpdateProductDescription);
        description.setText(var1);
        quantity = (EditText) findViewById(R.id.txtUpdateQuantity);
        quantity.setText(var2);
        price = (EditText) findViewById(R.id.txtUpdatePrice);
        price.setText(var3);
        barcode = (TextView) findViewById(R.id.txtBarcodes);
        barcode.setText(var4);

        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                String updatedName = name.getText().toString();
                String updatedDescription = description.getText().toString();
                String updatedPrice = price.getText().toString();
                String updatedQuantity = quantity.getText().toString();
                String existingBarcode = barcode.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("name", updatedName);
                hashMap.put("description", updatedDescription);
                hashMap.put("price", updatedPrice);
                hashMap.put("quantity", updatedQuantity);

                reff.child(existingBarcode).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateProducts.this, "Product Saved to Inventory", Toast.LENGTH_LONG).show();
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
        }
    }
}
//End
