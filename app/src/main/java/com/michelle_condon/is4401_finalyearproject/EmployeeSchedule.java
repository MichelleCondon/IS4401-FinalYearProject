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



public class EmployeeSchedule extends AppCompatActivity {

    DatabaseReference mref;
    private AutoCompleteTextView txtSearchEmployee;
    RecyclerView recyclerView;
    List<FetchEmployees> fetchEmployees;
    EmployeeAdapter employeeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        mref= FirebaseDatabase.getInstance().getReference("EmployeeRoster");
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchEmployees = new ArrayList<>();
        txtSearchEmployee = (AutoCompleteTextView)findViewById(R.id.txtSearchEmployee);

        ValueEventListener event=new ValueEventListener() {
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

    private void populateSearch(DataSnapshot snapshot){
        ArrayList<String> employeeSearch = new ArrayList<>();
        if(snapshot.exists()){
            for(DataSnapshot ds:snapshot.getChildren()){
                String search =ds.child("employeeName").getValue(String.class);
                employeeSearch.add(search);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,employeeSearch);
            txtSearchEmployee.setAdapter(adapter);
            txtSearchEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String search = txtSearchEmployee.getText().toString();
                    searchEmployee(search);
                }
            });
        }else{
            Log.d("EmployeeRoster","No data found");
        }
    }

    private void searchEmployee(String search) {
        Query query= mref.orderByChild("employeeName").equalTo(search);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Calling FetchData class to utilise the getters within the class
                    FetchEmployees data = ds.getValue(FetchEmployees.class);
                    fetchEmployees.add(data);
                }
                //Calls the helper adapter class to manage the layout of the displayed items using a view holder class
                employeeAdapter = new EmployeeAdapter(fetchEmployees);
                recyclerView.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
