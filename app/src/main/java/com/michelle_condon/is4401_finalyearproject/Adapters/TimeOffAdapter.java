package com.michelle_condon.is4401_finalyearproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michelle_condon.is4401_finalyearproject.Models.FetchRequests;
import com.michelle_condon.is4401_finalyearproject.Models.Items;
import com.michelle_condon.is4401_finalyearproject.R;

import java.util.List;

public class TimeOffAdapter extends RecyclerView.Adapter {

    //Code below to display data in view holders from Firebase based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //List Item
    List<FetchRequests> fetchRequestsList;
    Items item;


    public TimeOffAdapter(List<FetchRequests> fetchRequestsList) {
        this.fetchRequestsList = fetchRequestsList;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the item_layout.xml to output the data into the view holder within the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_5, parent, false);
        TimeOffAdapter.ViewHolderClass viewHolderClass = new TimeOffAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TimeOffAdapter.ViewHolderClass viewHolderClass = (TimeOffAdapter.ViewHolderClass) holder;
        //Setting the hours for every day of the week by pulling the data from Firebase using getters
        FetchRequests fetchRequests = fetchRequestsList.get(position);
        viewHolderClass.dates.setText(fetchRequests.getDates());
        viewHolderClass.employeeEmail.setText(fetchRequests.getEmployeeEmail());

    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return fetchRequestsList.size();
    }


    public static class ViewHolderClass extends RecyclerView.ViewHolder {
        //Declaring variables
        TextView dates, employeeEmail;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            //Assigning values to the variables by resource Id's
            dates = itemView.findViewById(R.id.txtReviewDates);
            employeeEmail = itemView.findViewById(R.id.txtEmployeeEmailReview);


        }


    }

}
