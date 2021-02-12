package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//https://www.youtube.com/watch?v=g74E5DpUT-Q

public class ProductCheck extends AppCompatActivity {

    DatabaseReference mref;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        mref = FirebaseDatabase.getInstance().getReference("Items");
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);

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
                    searchUser(name);
                }
            });
        } else {
            Log.d("Items", "No data found");
        }
    }

    private void searchUser(String name) {
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
}