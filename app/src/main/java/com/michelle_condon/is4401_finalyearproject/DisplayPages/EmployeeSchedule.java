package com.michelle_condon.is4401_finalyearproject.DisplayPages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.EmployeeAdapter;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Models.FetchEmployees;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
public class EmployeeSchedule extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube video "4- Search data in Firebase using Android Application | Firebase+Android Tutorials", Coding Tutorials, "https://www.youtube.com/watch?v=g74E5DpUT-Q"
    //Declaring Variables
    private AutoCompleteTextView txtSearchEmployee;
    DatabaseReference mref;
    RecyclerView recyclerView;
    List<FetchEmployees> fetchEmployees;
    EmployeeAdapter employeeAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

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

        //Account button in the toolbar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Initialise access to firebase
        mref = FirebaseDatabase.getInstance().getReference("EmployeeRoster");

        //Assigning values by resourceId
        recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchEmployees = new ArrayList<>();
        txtSearchEmployee = findViewById(R.id.txtSearchEmployee);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mref.addListenerForSingleValueEvent(event);

    }

    //Methods for the navigation bar
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    //Populating the search field based off Firebase data
    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> employeeSearch = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String search = ds.child("employeeName").getValue(String.class);
                employeeSearch.add(search);
            }
            //Displaying results in a simple list item format
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, employeeSearch);
            txtSearchEmployee.setAdapter(adapter);
            txtSearchEmployee.setOnItemClickListener((parent, view, position, id) -> {
                String search = txtSearchEmployee.getText().toString();
                searchEmployee(search);

            });
        } else {
            Log.d("EmployeeRoster", "No data found");
        }
    }

    //Searching for an employee based off their name from Firebase
    private void searchEmployee(String search) {

        Query query = mref.orderByChild("employeeName").equalTo(search);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Calling FetchData class to utilise the getters within the class
                    FetchEmployees data = ds.getValue(FetchEmployees.class);
                    fetchEmployees.add(data);
                }
                //Calls the helper adapter class to manage the layout of the displayed items using a view holder class
                employeeAdapter = new EmployeeAdapter(fetchEmployees);
                recyclerView.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        //If statement for if a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        }
    }
}
//End