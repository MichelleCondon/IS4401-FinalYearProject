package com.michelle_condon.is4401_finalyearproject.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michelle_condon.is4401_finalyearproject.AddPages.AddItems;
import com.michelle_condon.is4401_finalyearproject.LoginScreen.MainActivity;
import com.michelle_condon.is4401_finalyearproject.Menus.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.VList;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class VirtualList extends AppCompatActivity implements View.OnClickListener {

    //Code below is based on a YouTube Video, by Ben O'Brien, https://www.youtube.com/channel/UCIMduWsoyJxVuDbZ3TPhzew
    //Declare Variables
    private ArrayList<String> list;
    private ArrayAdapter<String> listAdapter;
    private ListView listView;
    private EditText txtListName;
    DatabaseReference reff;
    VList vList;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_list);

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

        //Account button in the toolbar
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Button btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());


        //Assign values by resource id
        listView = findViewById(R.id.listView);
        Button btnAddList = findViewById(R.id.btnAddList);
        txtListName = findViewById(R.id.txtListName);
        txtListName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        Button btnViewList = findViewById(R.id.btnViewList);
        btnViewList.setOnClickListener(this);


        vList = new VList();
        //Initialise connection to Firebase
        reff = FirebaseDatabase.getInstance().getReference().child("List");
        btnAddList.setOnClickListener(view -> {
            String product = txtListName.getText().toString();

            //Validation
            if (product.isEmpty()) {
                txtListName.setError("Please add an item to the text box on screen in order to add it to the list");
                txtListName.requestFocus();
            } else {
                //Code for pushing data to Firebase using a hashmap is fro a Youtube Video by Technical Skillz whihc can be found at "https://www.youtube.com/watch?v=SGiY_AitrN0"
                addItem();
                HashMap hashMap = new HashMap();
                hashMap.put("product", product);
                reff.child(product).setValue(hashMap);
                txtListName.setText("");
                Toast.makeText(VirtualList.this, "Item saved to inventory", Toast.LENGTH_LONG).show();
            }
//End
        });


        list = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        setUpListViewListener();


    }

    //Navigation bar methods
    private void signout() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void home() {
        startActivity(new Intent(this, MainMenu.class));
    }

    private void account() {
        startActivity(new Intent(this, AccountMenu.class));
    }

    //View listener if user holds on screen the product is deleted from the screen
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Context context = getApplicationContext();
            Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

            list.remove(i);
            listAdapter.notifyDataSetChanged();
            return true;
        });
    }

    //Adding item to list on screen
    private void addItem() {
        EditText input = findViewById(R.id.txtListName);
        String itemText = input.getText().toString();

        if (txtListName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please type an item into the text box to add it to the list", Toast.LENGTH_LONG).show();
        } else if (!(itemText.equals(""))) {
            listAdapter.add(itemText);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text..", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        //If statement for when a button is clicked
        if (v.getId() == R.id.btnAccount) {
            startActivity(new Intent(this, AccountMenu.class));
        } else if (v.getId() == R.id.btnViewList) {
            startActivity(new Intent(this, ExistingListItems.class));
        }
    }
}
//End
