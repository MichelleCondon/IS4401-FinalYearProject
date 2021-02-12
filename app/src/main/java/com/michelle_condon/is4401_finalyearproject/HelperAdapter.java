package com.michelle_condon.is4401_finalyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {

    //Code below to display data in view holders from Firebase based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //List Item
    List<FetchData> fetchDataList;

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
        viewHolderClass.price.setText(fetchData.getPrice());
        viewHolderClass.quantity.setText(fetchData.getQuantity());

    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return fetchDataList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder {
        //Declaring variables
        TextView name, description, price, quantity;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            //Assigning values to the variables by resource Id's
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }
}
//End
