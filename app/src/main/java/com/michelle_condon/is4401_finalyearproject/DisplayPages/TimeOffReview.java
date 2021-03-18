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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.TimeOffAdapter;
import com.michelle_condon.is4401_finalyearproject.ManagementViewEmployeeSchedule;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
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

    @SuppressLint("IntentReset")
    protected void sendEmailApproval() {
        txtEmployeeEmail = (TextView) findViewById(R.id.txtEmployeeEmailReview);
        String user = (txtEmployeeEmail.getText().toString());
        Log.i("Send email", "");
        String[] TO = {user};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Approved");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your Time Off Request Has Been Approved and Your Roster will now be updated" + "\n" + "Thanks" + "\n" + "Management");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeOffReview.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendEmailDenial() {
        txtEmployeeEmail = (TextView) findViewById(R.id.txtEmployeeEmailReview);
        String user = String.valueOf(txtEmployeeEmail.getText().toString());
        Log.i("Send email", "");
        String[] TO = {user};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Time Off Rejected");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Unfortunately your Time Off Request Has Been Rejected by Management" + "\n" + "Thanks" + "\n" + "Management");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeOffReview.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
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
                startActivity(new Intent(this, ManagementViewEmployeeSchedule.class));
                sendEmailApproval();

                break;
            case R.id.btnReject:
                sendEmailDenial();
                break;

        }
    }

}