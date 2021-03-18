package com.michelle_condon.is4401_finalyearproject.DisplayPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.MainMenu;
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


        //Assigning values to the variables declared above
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());
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

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
            //Clock In/Out button
            case R.id.btnRefresh:
                finish();
                startActivity(getIntent());
                break;

        }
    }
}
//End