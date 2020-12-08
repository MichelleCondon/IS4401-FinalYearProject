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
EditText txtName;
TextView barcodeRef;
Button btnSave;
DatabaseReference reff;
Items item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        txtName = (EditText)findViewById(R.id.txtProductName);
        btnSave=(Button)findViewById(R.id.btnSave);
        barcodeRef = (TextView)findViewById(R.id.barcodeRef);

        barcodeRef.setText(scanResult);

        item = new Items();
        reff= FirebaseDatabase.getInstance().getReference().child("Items");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            item.setName(txtName.getText().toString().trim());
            reff.push().setValue(item);
                Toast.makeText(AddItems.this,"Data saved", Toast.LENGTH_LONG).show();
            }
        });


            }
        }

