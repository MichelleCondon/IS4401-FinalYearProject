package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
import com.michelle_condon.is4401_finalyearproject.Adapters.MgtEmployeeAdapter;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;

import java.util.ArrayList;
import java.util.List;

public class ManagementViewEmployeeSchedule extends AppCompatActivity implements View.OnClickListener{

    //Declaring Variables
    private AutoCompleteTextView txtSearchEmployee;
    DatabaseReference mref;
    RecyclerView ListRecyclerViewMgtSchedule;
    List<FetchEmployees> fetchEmployees;
    MgtEmployeeAdapter mgtEmployeeAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_view_employee_schedule);

        //Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_account:
                        account();
                        break;
                    case R.id.action_home:
                        home();
                        break;
                    case R.id.action_signout:
                        signout();
                        break;
                }
                return true;
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Buttons on the menu
        //Assigning values by resource Id's - Account Button
        Button btnAccount = (Button) findViewById(R.id.btnAccount);
        //Listening for the users button click for clock in/out
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Initialise access to firebase
        mref = FirebaseDatabase.getInstance().getReference("EmployeeRoster");

        //Assigning values by resourceId
        ListRecyclerViewMgtSchedule = findViewById(R.id.ListRecyclerViewMgtSchedule);
        ListRecyclerViewMgtSchedule.setLayoutManager(new LinearLayoutManager(this));
        fetchEmployees = new ArrayList<>();
        txtSearchEmployee = (AutoCompleteTextView) findViewById(R.id.txtSearchEmployee);

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

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, ManagementMainMenu.class));
    }

    private void account() {   }

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
            txtSearchEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String search = txtSearchEmployee.getText().toString();
                    searchEmployee(search);

                }
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
                mgtEmployeeAdapter = new MgtEmployeeAdapter(fetchEmployees);
                ListRecyclerViewMgtSchedule.setAdapter(mgtEmployeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



    @Override
    public void onClick(View v) {

    }

    }
