package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.Models.Hours;

public class HoursRequest extends AppCompatActivity {

    //Declare Variables
    EditText txtDay, txtHours;
    DatabaseReference reff;
    Hours hour;
    Spinner spinnerDay, spinnerMonth;
    Button btnAccount;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_request);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        //Assigning values by resource ID
        Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        txtDay = (EditText) findViewById(R.id.txtDay);
        txtHours = (EditText) findViewById(R.id.txtHours);
        Button btnRequestHours = (Button) findViewById(R.id.btnRequestChange);
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setText(firebaseUser.getEmail());

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HoursRequest.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.months));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(HoursRequest.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.days));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(myAdapter2);

        //Referencing the hours class
        hour = new Hours();

        //Accessing data from Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("HourChangeRequests");

        btnRequestHours.setOnClickListener(new View.OnClickListener() {
            //Alert Dialog prompt will appear when the request change button is clicked
            //Code below is based on the website Stack Overflow, Aman Alam, https://stackoverflow.com/questions/4850493/how-to-open-a-dialog-when-i-click-a-button

            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(HoursRequest.this).create(); //Read Update
                alertDialog.setTitle("Confirm Request");
                alertDialog.setMessage("You have requested a modification to your hours");

                alertDialog.setButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        hour.setEmail(btnAccount.getText().toString());
                        String date = spinnerDay.getSelectedItem().toString();
                        hour.setDate(date);
                        String month = spinnerMonth.getSelectedItem().toString();
                        hour.setMonth(month);
                        hour.setDay(txtDay.getText().toString().trim());
                        hour.setHours(txtHours.getText().toString().trim());
                        reff.push().setValue(hour);
                        Toast.makeText(HoursRequest.this, "Change Request has been Submitted", Toast.LENGTH_LONG).show();

                    }
                });
                alertDialog.show();
            }
            //End
        });

    }

}