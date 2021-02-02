package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    //Declaring Variables
    private TextView clockin, inventory, productCheck;
    private Button btnAddItem, view, btnCreateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Buttons on the menu

        //Assigning values by resource Id's - ClockIn/Out button
        clockin = (TextView) findViewById(R.id.btnClock);
        //Listening for the users button click for clock in/out
        clockin.setOnClickListener(this);

        //Assigning values by resource Id's - Add Product button
        btnAddItem = (Button) findViewById(R.id.btnAdd);
        //Listening for the users button click for add product
        btnAddItem.setOnClickListener(this);

        //Assigning values by resource Id's - Create List button
        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        //Listening for the users button click for create list
        btnCreateList.setOnClickListener(this);

        //Assigning values by resource Id's - View Schedule button
        view = (Button) findViewById(R.id.btnView);
        //Listening for the users button click for view schedule
        view.setOnClickListener(this);

        //Assigning values by resource Id's - View Inventory button
        inventory = (TextView) findViewById(R.id.btnInventory);
        //Listening for the users button click for view inventory
        inventory.setOnClickListener(this);

        //Assigning values by resource Id's - Product Check button
        productCheck = (TextView) findViewById(R.id.btnProductCheck);
        //Listening for the users button click for product check
        productCheck.setOnClickListener(this);
    }

    @Override
    //OnClick method
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Clock In/Out button
            //case R.id.btnClock:
                //startActivity(new Intent(this, EmployeeSchedule.class));
                //break;
            //View inventory button
            case R.id.btnInventory:
                startActivity(new Intent(this, DisplayItems.class));
                break;
            //Add product button
            case R.id.btnAdd:
                startActivity(new Intent(this, BarcodeScanner.class));
                break;
            //View schedule button
            case R.id.btnView:
                startActivity(new Intent(this, EmployeeSchedule.class));
                break;
            //Product check button
            case R.id.btnProductCheck:
                startActivity(new Intent(this, ProductCheck.class));
                break;
            //Create list button
            case R.id.btnCreateList:
                startActivity(new Intent(this, VirtualList.class));
                break;

        }
    }
}