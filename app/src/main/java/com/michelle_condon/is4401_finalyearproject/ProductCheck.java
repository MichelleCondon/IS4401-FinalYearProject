package com.michelle_condon.is4401_finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
//https://www.youtube.com/watch?v=g74E5DpUT-Q

public class ProductCheck extends AppCompatActivity {

DatabaseReference mref;
private ListView listdata;
private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check);

mref= FirebaseDatabase.getInstance().getReference("Items");
listdata = (ListView)findViewById(R.id.listData);
txtSearch = (AutoCompleteTextView)findViewById(R.id.txtSearch);

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
        ArrayList<String> names = new ArrayList<>();
        if(snapshot.exists()){
            for(DataSnapshot ds:snapshot.getChildren()){
                String name=ds.child("name").getValue(String.class);
                names.add(name);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,names);
            txtSearch.setAdapter(adapter);
            txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = txtSearch.getText().toString();
                    searchUser(name);
                }
            });
        }else{
            Log.d("Items","No data found");
        }
    }

    private void searchUser(String name) {
        Query query= mref.orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    ArrayList<String> listItems =  new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        FetchData fetchData = new FetchData(ds.child("name").getValue(String.class),ds.child("description").getValue(String.class),ds.child("price").getValue(String.class),ds.child("quantity").getValue(String.class));
                        listItems.add(fetchData.getName()+"\n"+ fetchData.getDescription() + "\n" + fetchData.getPrice() + "\n" + fetchData.getQuantity());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);
                    listdata.setAdapter(adapter);


                }else{
                    Log.d("Items","No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class FetchData {
        //Declare variables
        String name;
        String description;
        String price;
        String quantity;

        public FetchData() {
        }

        public FetchData(String name, String description, String price, String quantity) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
        }



        //Getter
        public String getName() {
            return name;
        }

        //Setter
        public void setName(String name) {
            this.name = name;
        }

        //Getter
        public String getDescription() {
            return description;
        }

        //Setter
        public void setDescription(String description) {
            this.description = description;
        }

        //Getter
        public String getPrice() {
            return price;
        }

        //Setter
        public void setPrice(String price) {
            this.price = price;
        }

        //Getter
        public String getQuantity() {
            return quantity;
        }

        //Setter
        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}