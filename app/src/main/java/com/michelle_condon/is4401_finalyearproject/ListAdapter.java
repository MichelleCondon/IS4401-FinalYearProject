package com.michelle_condon.is4401_finalyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter{

    //List Item
    List<VList> vDataList;

    public ListAdapter(List<VList> vDataList){
        this.vDataList = vDataList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the item_layout.xml to output the data into the view holder within the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout3,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListAdapter.ViewHolderClass viewHolderClass=(ListAdapter.ViewHolderClass)holder;
        VList vLists=vDataList.get(position);
        viewHolderClass.products.setText(vLists.getProducts());


    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return vDataList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder{
        //Declaring variables
        TextView products;

        public ViewHolderClass(@NonNull View itemView){
            super(itemView);
            //Assigning values to the variables by resource Id's
            products = itemView.findViewById(R.id.products);


        }
    }
}
