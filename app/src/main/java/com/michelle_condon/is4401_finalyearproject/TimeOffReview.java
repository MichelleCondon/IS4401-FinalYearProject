package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.TimeOffAdapter;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeOffReview extends AppCompatActivity implements View.OnClickListener {


    //Code below ia based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0
    //Declare Variables
    List<FetchRequests> fetchRequests;
    RecyclerView recyclerViewRequests;
    TimeOffAdapter timeoffAdapter;
    DatabaseReference databaseReference;
    TextView txtEmployeeEmail, txtReviewDates;
    Button btnAccept, btnReject, btnResult;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_review);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        txtEmployeeEmail = (TextView) findViewById(R.id.txtEmployeeEmailReview);
        txtReviewDates = (TextView) findViewById(R.id.txtReviewDates);
        btnAccept = (Button) findViewById(R.id.btnApprove);
        btnReject = (Button) findViewById(R.id.btnReject);
        btnResult = (Button) findViewById(R.id.btnResult);
        //Listening for the users button click for clock in/out

        //Assigning the recycler view by resource id
        recyclerViewRequests = findViewById(R.id.recyclerViewReview);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(this));
        fetchRequests = new ArrayList<>();


        //Pulling data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("TimeOffRequests");

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

    @Override
    public void onClick(View v) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TimeOffRequests");
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
            case R.id.btnApprove:

            case R.id.btnReject:


                break;

        }
    }

}