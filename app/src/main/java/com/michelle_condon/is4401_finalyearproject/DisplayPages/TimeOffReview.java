package com.michelle_condon.is4401_finalyearproject.DisplayPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.TimeOffAdapter;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.ManagementMainMenu;
import com.michelle_condon.is4401_finalyearproject.Models.FetchRequests;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class TimeOffReview extends AppCompatActivity implements View.OnClickListener {


    //Code below ia based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0
    //Declare Variables
    List<FetchRequests> fetchRequests;
    RecyclerView recyclerViewRequests;
    TimeOffAdapter timeoffAdapter;
    DatabaseReference databaseReference;
    TextView txtEmployeeEmail, txtReviewDates, txtReviewName, txtStatus;
    Button btnAccount, btnRefresh;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_review);

        //Code for the Navigation Bar is Based on a Tutorial "Bottom Navigation Bar in Android" by Geeks For Geeks which can be found at "https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/"
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_signout) {
                signout();
            } else if (item.getItemId() == R.id.action_home) {
                home();
            } else if (item.getItemId() == R.id.action_employeeInfo) {
                account();
            }
            return true;
        });
        //End

        //Assigning the recycler view by resource id
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());
        txtEmployeeEmail = findViewById(R.id.txtEmployeeEmailReview);
        txtReviewDates = findViewById(R.id.txtReviewDates);
        txtReviewName = findViewById(R.id.txtEmpHolidayName);
        txtStatus = findViewById(R.id.txtStatus);
        btnRefresh = findViewById(R.id.btnRefreshRequests);

        //Assigning the recycler view by resource id
        recyclerViewRequests = findViewById(R.id.recyclerViewReview);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(this));
        fetchRequests = new ArrayList<>();



        //Pulling data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TimeOffRequests");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Calling FetchData class to utilise the getters within the class
                    FetchRequests data = ds.getValue(FetchRequests.class);
                    fetchRequests.add(data);

                }
                //Calls the helper adapter class to manage the layout of the displayed items using a view holder class
                timeoffAdapter = new TimeOffAdapter(fetchRequests);
                recyclerViewRequests.setAdapter(timeoffAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {startActivity(new Intent(this, ManagementMainMenu.class)); }

    private void account() {
        startActivity(new Intent(this, UserProfile.class));
    }



    @Override
    public void onClick(View v) {
        //if statements for if a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnRefreshRequests) {
            finish();
            startActivity(getIntent());
        }
    }
}
