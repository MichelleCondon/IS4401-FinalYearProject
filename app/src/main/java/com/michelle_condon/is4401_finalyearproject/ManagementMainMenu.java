package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.michelle_condon.is4401_finalyearproject.List.VirtualList;

public class ManagementMainMenu extends AppCompatActivity implements View.OnClickListener {

    //Declaring Variables
    private TextView clockin, inventory, productCheck;
    private Button btnAddEmployee, view, btnCreateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_main_menu);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }
        //Buttons on the menu

        //Assigning values by resource Id's - ClockIn/Out button
        clockin = (TextView) findViewById(R.id.btnClockIn);
        //Listening for the users button click for clock in/out
        clockin.setOnClickListener(this);

        //Assigning values by resource Id's - Add Product button
        btnAddEmployee = (Button) findViewById(R.id.btnAddProduct);
        //Listening for the users button click for add product
        btnAddEmployee.setOnClickListener(this);

        //Assigning values by resource Id's - Create List button
        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        //Listening for the users button click for create list
        btnCreateList.setOnClickListener(this);

        //Assigning values by resource Id's - View Schedule button
        view = (Button) findViewById(R.id.btnViewSchedule);
        //Listening for the users button click for view schedule
        view.setOnClickListener(this);

        //Assigning values by resource Id's - View Inventory button
        inventory = (TextView) findViewById(R.id.btnViewInventory);
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
            case R.id.btnClockIn:
                startActivity(new Intent(this, AddSchedule.class));
                break;
            //View inventory button
            case R.id.btnViewInventory:
                startActivity(new Intent(this, ManagementEditEmployee.class));
                break;
            //Add product button
            case R.id.btnAddProduct:
                startActivity(new Intent(this, SignupScreen.class));
                break;
            //View schedule button
            case R.id.btnViewSchedule:
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
