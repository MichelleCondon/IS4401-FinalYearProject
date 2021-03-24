package com.michelle_condon.is4401_finalyearproject.DisplayPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.UserAdapter;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Models.User;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    List<User> userData;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button btnAccount, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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

        //Account Button
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());

        //Refresh Button
        btnRefresh = findViewById(R.id.btnRefreshUser);
        btnRefresh.setOnClickListener(this);


        //Code below ia based on a YouTube Video "How to retrieve data from Firebase in RecyclerView | Android Tutorial - Quick + Easy" by Learn with Deeksha, "https://www.youtube.com/watch?v=lJaPdBMdPy0"
        //Assigning the recycler view by resource id
        recyclerView = findViewById(R.id.recyclerViewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = new ArrayList<>();

        //Pulling data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Calling User class to utilise the getters within the class
                    User user = ds.getValue(User.class);
                    userData.add(user);

                }
                //Calls the user adapter class to manage the layout of the displayed items using a view holder class
                userAdapter = new UserAdapter(userData);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    //End

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
        //Account button
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
            //Refresh Button
        } else if (v.getId() == R.id.btnRefreshUser) {
            finish();
            startActivity(getIntent());
        }
    }
}
