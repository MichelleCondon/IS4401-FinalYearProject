package com.michelle_condon.is4401_finalyearproject.DisplayPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.HelperAdapter;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.FetchData;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayItems extends AppCompatActivity implements View.OnClickListener {

    //Code below ia based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0
    //Declare Variables
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button btnAccount, btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_items);

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


        //account button in the toolbar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //assigning values by resource id
        btnRefresh = findViewById(R.id.btnRefresh);
        recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();


        //Pulling data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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

    //Navigation bar methods
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
        //if statement for when a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnRefresh) {
            finish();
            startActivity(getIntent());
        }
    }
}

