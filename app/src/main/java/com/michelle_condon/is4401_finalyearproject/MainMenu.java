package com.michelle_condon.is4401_finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.GoogleMaps.MapsActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    //Declaring Variables
    private TextView clockin;
    private Button btnAddItem, btnViewSchedule, btnCreateList, inventory, productCheck, lblAccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        lblAccount = (Button) findViewById(R.id.btnAccount);
        //Listening for the users button click for clock in/out
        lblAccount.setOnClickListener(this);
        lblAccount.setText(firebaseUser.getEmail());

        //Assigning values by resource Id's - ClockIn/Out button
        clockin = (TextView) findViewById(R.id.btnClockIn);
        //Listening for the users button click for clock in/out
        clockin.setOnClickListener(this);

        //Assigning values by resource Id's - Add Product button
        btnAddItem = (Button) findViewById(R.id.btnAddProduct);
        //Listening for the users button click for add product
        btnAddItem.setOnClickListener(this);

        //Assigning values by resource Id's - Create List button
        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        //Listening for the users button click for create list
        btnCreateList.setOnClickListener(this);

        //Assigning values by resource Id's - View Schedule button
        btnViewSchedule = (Button) findViewById(R.id.btnViewSchedule);
        //Listening for the users button click for view schedule
        btnViewSchedule.setOnClickListener(this);

        //Assigning values by resource Id's - View Inventory button
        inventory = (Button) findViewById(R.id.btnViewInventory);
        //Listening for the users button click for view inventory
        inventory.setOnClickListener(this);

        //Assigning values by resource Id's - Product Check button
        productCheck = (Button) findViewById(R.id.btnProductCheck);
        //Listening for the users button click for product check
        productCheck.setOnClickListener(this);
    }

    @Override
    //OnClick method
    public void onClick(View v) {
        //Java switch statement
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
            //Clock In/Out button
            case R.id.btnClockIn:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            //View inventory button
            case R.id.btnViewInventory:
                startActivity(new Intent(this, DisplayItems.class));
                break;
            //Add product button
            case R.id.btnAddProduct:
                startActivity(new Intent(this, BarcodeScanner.class));
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