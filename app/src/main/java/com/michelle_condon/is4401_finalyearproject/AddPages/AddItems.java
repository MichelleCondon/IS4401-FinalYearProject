package com.michelle_condon.is4401_finalyearproject.AddPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.Items;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.HashMap;

import static com.michelle_condon.is4401_finalyearproject.BarcodeScanner.BarcodeScanner.scanResult;


@SuppressWarnings("unchecked")
public class AddItems extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    EditText txtName, txtDescription, txtPrice, txtQuantity;
    TextView barcodeRef;
    Button btnSave, btnAccount;
    DatabaseReference reff;
    Items item;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

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


        //Product Name Text Box
        txtName = findViewById(R.id.txtName);
        txtName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        //Price Text Box
        txtPrice = findViewById(R.id.txtPrice);
        //Description Text Box
        txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        //Quantity Text Box
        txtQuantity = findViewById(R.id.txtQuantity);
        //Barcode TextView
        barcodeRef = findViewById(R.id.barcodeRef);
        //Save Button
        btnSave = findViewById(R.id.btnAddToSchedule);

        //Account Button
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Setting the text of the barcode text view with the scanned value from the barcode scanner
        barcodeRef.setText(scanResult);

        item = new Items();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        btnSave.setOnClickListener(v -> {
            String productName = txtName.getText().toString();
            String productDescription = txtDescription.getText().toString();
            String productQuantity = txtQuantity.getText().toString();
            String productPrice = txtPrice.getText().toString();

            //Validation to ensure fields are filled
            if (productName.isEmpty()) {
                txtName.setError("Product Name is Required");
                txtName.requestFocus();
                return;
            }
            if (productDescription.isEmpty()) {
                txtDescription.setError("Product Description is Required");
                txtDescription.requestFocus();
                return;
            }
            if (productQuantity.isEmpty()) {
                txtQuantity.setError("Product Quantity is Required");
                txtQuantity.requestFocus();
                return;
            }
            if (productPrice.isEmpty()) {
                txtPrice.setError("Product Price is Required");
                txtPrice.requestFocus();
                return;
            }

            //Checking to see if the barcode already exists in the database so it's not added twice
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(scanResult)) {
                        Toast.makeText(AddItems.this, "Barcode already exists in the inventory, please search for it to edit", Toast.LENGTH_LONG).show();
                    } else {
                        String name = txtName.getText().toString();
                        String description = txtDescription.getText().toString();
                        String barcode = barcodeRef.getText().toString();
                        String price = txtPrice.getText().toString();
                        String quantity = txtQuantity.getText().toString();

                        //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz which can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
                        //Pushing data to Firebase using a Hashmap
                        HashMap hashMap = new HashMap();
                        hashMap.put("barcode", barcode);
                        hashMap.put("name", name);
                        hashMap.put("description", description);
                        hashMap.put("price", price);
                        hashMap.put("quantity", quantity);

                        reff.child(barcode).setValue(hashMap);
                        Toast.makeText(AddItems.this, "Product Saved to Inventory", Toast.LENGTH_LONG).show();
                        //End
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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
        //if statement if a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        }
    }
}