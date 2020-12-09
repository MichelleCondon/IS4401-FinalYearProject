package com.michelle_condon.is4401_finalyearproject;

//Import Statements
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    //Declaring Variables
    private TextView clockin, backstock;
    private Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Assigning values by resource Id's - ClockIn/Out button
        clockin = (TextView) findViewById(R.id.clockIn);
        //Listening for the users button click for register
        clockin.setOnClickListener(this);

        //Assigning values by resource Id's
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        //Listening for the users button click for register
        btnAddItem.setOnClickListener(this);

        //Assigning values by resource Id's - ClockIn/Out button
        backstock = (TextView) findViewById(R.id.backstock);
        //Listening for the users button click for register
        backstock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Clock In/Out button
            case R.id.clockIn:
                startActivity(new Intent(this, ClockIn_Screen.class));
                break;
           case R.id.backstock:
                startActivity(new Intent(this, DisplayItems.class));
                break;
            case R.id.btnAddItem:
                startActivity(new Intent(this, BarcodeScanner.class));
                break;

    }
}}