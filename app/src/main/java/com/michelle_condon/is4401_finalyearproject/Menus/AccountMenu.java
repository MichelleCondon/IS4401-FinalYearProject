package com.michelle_condon.is4401_finalyearproject.Menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.HoursRequest;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.TimeOffRequest;

public class AccountMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btnRequestHoursChange, btnRequestTimeOff, btnAccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Assigning values by resource Id's - Account Button
        btnRequestHoursChange = (Button) findViewById(R.id.btnRequestHoursChange);
        //Listening for the users button click for clock in/out
        btnRequestHoursChange.setOnClickListener(this);
        //Assigning values by resource Id's - Account Button
        btnRequestTimeOff = (Button) findViewById(R.id.btnRequestTimeOff);
        //Listening for the users button click for clock in/out
        btnRequestTimeOff.setOnClickListener(this);
        btnAccount = (Button) findViewById(R.id.btnAccount);
        //Listening for the users button click for clock in/out
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();


    }

    @Override
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Account button
            case R.id.btnRequestHoursChange:
                startActivity(new Intent(this, HoursRequest.class));
                break;
            case R.id.btnRequestTimeOff:
                startActivity(new Intent(this, TimeOffRequest.class));
                break;

        }
    }
}