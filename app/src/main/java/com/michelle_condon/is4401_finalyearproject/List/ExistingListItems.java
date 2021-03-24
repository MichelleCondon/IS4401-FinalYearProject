package com.michelle_condon.is4401_finalyearproject.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.ListAdapter;
import com.michelle_condon.is4401_finalyearproject.Models.VList;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class ExistingListItems extends AppCompatActivity {

    //Code below ia based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //Declare Variables
    List<VList> vLists;
    RecyclerView recyclerView1;
    ListAdapter listAdapter;
    DatabaseReference databaseReference;
    TextView products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_list_items);

        //Removed any words from within the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }


        //Assigning the recycler view by resource id
        recyclerView1 = findViewById(R.id.recyclerView1);
        products = findViewById(R.id.products);
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
}
//End