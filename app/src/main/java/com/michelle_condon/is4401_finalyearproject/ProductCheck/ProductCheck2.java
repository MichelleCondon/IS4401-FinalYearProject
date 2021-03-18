package com.michelle_condon.is4401_finalyearproject.ProductCheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.michelle_condon.is4401_finalyearproject.Adapters.HelperAdapter;
import com.michelle_condon.is4401_finalyearproject.BarcodeScanner.BarcodeScanner2;
import com.michelle_condon.is4401_finalyearproject.MainActivity;
import com.michelle_condon.is4401_finalyearproject.MainMenu;
import com.michelle_condon.is4401_finalyearproject.Menus.AccountMenu;
import com.michelle_condon.is4401_finalyearproject.Models.FetchData;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.michelle_condon.is4401_finalyearproject.BarcodeScanner.BarcodeScanner2.scanResult;

public class ProductCheck2 extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    DatabaseReference mref;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    TextView txtSearch;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button btnAccount, btnCamera;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check2);

        //Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_account:
                        account();
                        break;
                    case R.id.action_home:
                        home();
                        break;
                    case R.id.action_signout:
                        signout();
                        break;
                }
                return true;
            }
        });

        //Assigning values to the variables declared above
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        btnAccount.setText(firebaseUser.getEmail());
        btnCamera = (Button) findViewById(R.id.btnCamera);
        //Connecion to the Firebase object "Items"
        mref = FirebaseDatabase.getInstance().getReference("Items");
        recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();
        result = scanResult;
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtSearch.setText(result);
        searchProduct(result);


        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mref.addListenerForSingleValueEvent(event);

    }


    //Searching for an product based off the barcode from Firebase using the camera
    private void searchProduct(String result) {
        Query query = mref.orderByChild("barcode").equalTo(result);
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
        //Java switch statement
        //Account button
        switch (v.getId()) {
            //Account button
            case R.id.btnAccount:
                startActivity(new Intent(this, AccountMenu.class));
                break;
            case R.id.btnCamera:
                startActivity(new Intent(this, BarcodeScanner2.class));
                break;

        }
    }
}
