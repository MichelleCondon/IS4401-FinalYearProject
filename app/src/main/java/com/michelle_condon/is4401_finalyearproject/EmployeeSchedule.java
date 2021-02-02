package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

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
    private ListView listdataEmployee;
    private AutoCompleteTextView txtSearchEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

        mref= FirebaseDatabase.getInstance().getReference("EmployeeRoster");
        listdataEmployee = (ListView)findViewById(R.id.listDataEmployee);
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
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    ArrayList<String> listEmployees =  new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        EmployeeSchedule.FetchEmployees fetchEmployees = new FetchEmployees(ds.child("employeeName").getValue(String.class),ds.child("monday").getValue(String.class),ds.child("tuesday").getValue(String.class),ds.child("wednesday").getValue(String.class),ds.child("thursday").getValue(String.class),ds.child("friday").getValue(String.class));
                        listEmployees.add(fetchEmployees.getEmployeeName()+"\n"+ fetchEmployees.getMonday() + "\n" + fetchEmployees.getTuesday() + "\n" + fetchEmployees.getWednesday()+ "\n" + fetchEmployees.getThursday()+ "\n" + fetchEmployees.getFriday());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listEmployees);
                    listdataEmployee.setAdapter(adapter);


                }else{
                    Log.d("EmployeeRoster","No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class FetchEmployees {
        //Declare variables
        String employeeName;
        String monday;
        String tuesday;
        String wednesday;
        String thursday;
        String friday;

        public FetchEmployees() {
        }

        public FetchEmployees(String employeeName, String monday, String tuesday, String wednesday, String thursday, String friday) {
            this.employeeName = employeeName;
            this.monday = monday;
            this.tuesday = tuesday;
            this.wednesday = wednesday;
            this.thursday = thursday;
            this.friday = friday;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeId) {
            this.employeeName = employeeName;
        }

        public String getMonday() {
            return monday;
        }

        public void setMonday(String monday) {
            this.monday = monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        public String getFriday() {
            return friday;
        }

        public void setFriday(String friday) {
            this.friday = friday;
        }
    }
}