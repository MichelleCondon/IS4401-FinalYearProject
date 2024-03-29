package com.michelle_condon.is4401_finalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle_condon.is4401_finalyearproject.Models.FetchData;
import com.michelle_condon.is4401_finalyearproject.Models.Items;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.UpdatePages.UpdateProducts;

import java.util.List;


public class HelperAdapter extends RecyclerView.Adapter{

    //Code below to display data in view holders from Firebase based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //List Item
    List<FetchData> fetchDataList;
    Items item;;


    public HelperAdapter(List<FetchData> fetchDataList) {
        this.fetchDataList = fetchDataList;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the item_layout.xml to output the data into the view holder within the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        //Setting the hours for every day of the week by pulling the data from Firebase using getters
        FetchData fetchData = fetchDataList.get(position);
        viewHolderClass.name.setText(fetchData.getName());
        viewHolderClass.description.setText(fetchData.getDescription());
        viewHolderClass.quantity.setText(fetchData.getQuantity());
        viewHolderClass.price.setText(fetchData.getPrice());
        viewHolderClass.barcode.setText(fetchData.getBarcode());




    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return fetchDataList.size();
    }


    public static class ViewHolderClass extends RecyclerView.ViewHolder {
        //Declaring variables
        TextView name, description, quantity, price, barcode;




        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            //Assigning values to the variables by resource Id's
            name = itemView.findViewById(R.id.txtProductName);
            description = itemView.findViewById(R.id.txtProductDescription);
            quantity = itemView.findViewById(R.id.txtProductQuantity);
            price = itemView.findViewById(R.id.txtProductPrice);
            barcode = itemView.findViewById(R.id.txtBarcode);

            //Code to transfer data to the next form was found on Stackoverflow at
            // "https://stackoverflow.com/questions/37186805/start-new-activity-with-onclick-in-recyclerview/44898559"

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, UpdateProducts.class);
                    String a = name.getText().toString();
                    String b = description.getText().toString();
                    String c = quantity.getText().toString();
                    String d = price.getText().toString();
                    String e = barcode.getText().toString();

                    intent.putExtra("Name", a);
                    intent.putExtra("Desc", b);
                    intent.putExtra("Quantity", c);
                    intent.putExtra("Price", d);
                    intent.putExtra("Barcode", e);
                    context.startActivity(intent);
                }
            });



        }


    }

}
//End
