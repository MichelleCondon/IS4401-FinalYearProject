package com.michelle_condon.is4401_finalyearproject;

//Import Statements

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Firebase Imports
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Importing the scanResult variable from the BarcodeScanner.java class
import static com.michelle_condon.is4401_finalyearproject.BarcodeScanner.scanResult;


public class AddItems extends AppCompatActivity {
    //Declare Variables
    EditText txtName, txtDescription, txtPrice, txtQuantity;
    TextView barcodeRef;
    Button btnSave;
    DatabaseReference reff;
    Items item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        //Assigning values by resource ID
        txtName = (EditText) findViewById(R.id.txtProductName);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtQuantity = (EditText) findViewById(R.id.txtQuantity);
        barcodeRef = (TextView) findViewById(R.id.barcodeRef);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        btnSave = (Button) findViewById(R.id.search_btn);
        barcodeRef = (TextView) findViewById(R.id.barcodeRef);

        //Setting the text of the barcode text view with the scanned value
        barcodeRef.setText(scanResult);

        item = new Items();
        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting the value of each variable to whatever the user enters on the form
                item.setName(txtName.getText().toString().trim());
                item.setDescription(txtDescription.getText().toString().trim());
                item.setBarcode(barcodeRef.getText().toString().trim());
                item.setPrice(txtPrice.getText().toString().trim());
                item.setQuantity(txtQuantity.getText().toString().trim());
                reff.push().setValue(item);
                Toast.makeText(AddItems.this, "Data saved", Toast.LENGTH_LONG).show();
            }
        });


    }
}
