package com.michelle_condon.is4401_finalyearproject.UpdatePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class UpdateProducts extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube Video Android Firebase - 10 - How Update Data in Firebase Realtime Database, Technical Skillz, "https://www.youtube.com/watch?v=0HLyJNuyhSo"

    private EditText name, description, quantity, price;
    public TextView barcode;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);

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

        //Account button in the toolbar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Data that was passed from the display item form
        Intent i = getIntent();
        String NAME = i.getStringExtra("Name");
        String var1 = i.getStringExtra("Desc");
        String var2 = i.getStringExtra("Quantity");
        String var3 = i.getStringExtra("Price");
        String var4 = i.getStringExtra("Barcode");

        //Assigning values by resource id and setting text
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnEdit = findViewById(R.id.btnUpdateInventory);
        name = findViewById(R.id.txtUpdateProductName);
        name.setText(NAME);
        description = findViewById(R.id.txtUpdateProductDescription);
        description.setText(var1);
        quantity = findViewById(R.id.txtUpdateQuantity);
        quantity.setText(var2);
        price = findViewById(R.id.txtUpdatePrice);
        price.setText(var3);
        barcode = findViewById(R.id.txtBarcodes);
        barcode.setText(var4);

        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        btnEdit.setOnClickListener(v -> {
            String productName = name.getText().toString();
            String productDescription = description.getText().toString();
            String productQuantity = quantity.getText().toString();
            String productPrice = price.getText().toString();

            //Validation
            if (productName.isEmpty()) {
                name.setError("Product Name is Required");
                name.requestFocus();
                return;
            }
            if (productDescription.isEmpty()) {
                name.setError("Product Description is Required");
                name.requestFocus();
                return;
            }
            if (productQuantity.isEmpty()) {
                name.setError("Product Quantity is Required");
                name.requestFocus();
                return;
            }
            if (productPrice.isEmpty()) {
                name.setError("Product Price is Required");
                name.requestFocus();
                return;
            }
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

            reff.child(existingBarcode).updateChildren(hashMap).addOnSuccessListener(o -> Toast.makeText(UpdateProducts.this, "Product Saved to Inventory", Toast.LENGTH_LONG).show());
        });

    }

    //Navigation bar method
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
        //If statement for when a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnDelete) {
            String existingBarcode = barcode.getText().toString();
            reff.child(existingBarcode).removeValue();
            Toast.makeText(UpdateProducts.this, "Product Deleted from Inventory", Toast.LENGTH_LONG).show();
        }
    }
}
//End
