package com.michelle_condon.is4401_finalyearproject;

//Import Statements
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    //Declaring Variables
    private TextView clockin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Assigning values by resource Id's - ClockIn/Out button
        clockin = (TextView) findViewById(R.id.clockIn);
        //Listening for the users button click for register
        clockin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Register Button
            case R.id.clockIn:
                startActivity(new Intent(this, ClockIn_Screen.class));
                break;
    }
}}