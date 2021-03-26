package com.michelle_condon.is4401_finalyearproject.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.ListAdapter;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Models.VList;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class ExistingListItems extends AppCompatActivity implements View.OnClickListener{

    //Code below ia based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //Declare Variables
    List<VList> vLists;
    RecyclerView recyclerView1;
    ListAdapter listAdapter;
    DatabaseReference databaseReference;
    TextView products;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_list_items);

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

        //Account button based on Firebase login
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());


        //Assigning the recycler view by resource id
        recyclerView1 = findViewById(R.id.recyclerView1);
        products = findViewById(R.id.txtProducts);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        vLists = new ArrayList<>();

        //Pulling data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("List");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Calling FetchData class to utilise the getters within the class
                    VList data = ds.getValue(VList.class);
                    vLists.add(data);
                }
                //Calls the helper adapter class to manage the layout of the displayed items using a view holder class
                listAdapter = new ListAdapter(vLists);
                recyclerView1.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Navigation Bar methods
    private void signout() { startActivity(new Intent(this, MainActivity.class));    }

    private void home() { startActivity(new Intent(this, MainMenu.class));}

    private void account() { startActivity(new Intent(this, AccountMenu.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        }
    }
}
//End