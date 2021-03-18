package com.michelle_condon.is4401_finalyearproject.ProductCheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.michelle_condon.is4401_finalyearproject.Adapters.HelperAdapter;
import com.michelle_condon.is4401_finalyearproject.BarcodeScanner.BarcodeScanner2;
import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.FetchData;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;


public class ProductCheck extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on the Youtube video "4- Search data in Firebase using Android Application | Firebase+Android Tutorials", Coding Tutorials, https://www.youtube.com/watch?v=g74E5DpUT-Q
    //Declare Variables
    DatabaseReference mref;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    private AutoCompleteTextView txtSearch;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button btnAccount, btnCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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

        //Assigning values to the previously declared variables
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());
        btnCamera = (Button) findViewById(R.id.btnCamera);
        recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);

        //Connection to the object Items in Firebase
        mref = FirebaseDatabase.getInstance().getReference("Items");

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

    //Populating the search field based off Firebase data
    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String name = ds.child("name").getValue(String.class);
                names.add(name);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
            txtSearch.setAdapter(adapter);
            txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = txtSearch.getText().toString();
                    searchProduct(name);
                }
            });
        } else {
            Log.d("Items", "No data found");
        }
    }

    //Searching for an product based off the name from Firebase
    private void searchProduct(String name) {
        Query query = mref.orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Calling FetchData class to utilise the getters within the class
                    FetchData data = ds.getValue(FetchData.class);
                    fetchData.add(data);
                }
                //Calls the helper adapter class to manage the layout of the displayed items using a view holder class
                helperAdapter = new HelperAdapter(fetchData);
                recyclerView.setAdapter(helperAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }


    @Override
    public void onClick(View v) {
        //Java switch statement
        //Account button
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
            case R.id.btnCamera:
                startActivity(new Intent(this, BarcodeScanner2.class));
                break;

        }
    }
}
//End