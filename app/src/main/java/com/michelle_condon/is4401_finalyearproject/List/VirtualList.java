package com.michelle_condon.is4401_finalyearproject.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.ExistingListItems;
import com.michelle_condon.is4401_finalyearproject.Models.VList;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;

public class VirtualList extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on a YouTube Video, by Ben O'Brien, https://www.youtube.com/channel/UCIMduWsoyJxVuDbZ3TPhzew
    //Code below to insert data into Firebase is based on a YouTube Video, by EducaTree, https://www.youtube.com/watch?v=iy6WexahCdY&t=328

    //Declare Variables
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button btnAddList, btnViewList;
    private EditText txtListName;
    DatabaseReference reff;
    VList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_list);

        //Removed any wording in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        //Assign values by resource id
        listView = findViewById(R.id.listView);
        btnAddList = findViewById(R.id.btnAddList);
        txtListName = findViewById(R.id.txtListName);
        btnViewList = findViewById(R.id.btnViewList);
        btnViewList.setOnClickListener(this);


        list = new VList();
        //Initialise connection to Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("List");
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Setting the value of each variable to whatever the user enters on the form
                list.setProducts(txtListName.getText().toString().trim());
                reff.push().setValue(list);
                Toast.makeText(VirtualList.this, "Data saved", Toast.LENGTH_LONG).show();
                addItem(view);
            }

        });


        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();


    }

    //View listener if user holds on screen the product is deleted from the screen
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Items Removed", Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //Adding item to list on screen
    private void addItem(View view) {
        EditText input = findViewById(R.id.txtListName);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text..", Toast.LENGTH_LONG).show();
        }

    }

    //Button functionality
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //If the register label is clicked the sign up screen opens
            case R.id.btnViewList:
                startActivity(new Intent(this, ExistingListItems.class));
                break;
        }
    }
}
//End
//End