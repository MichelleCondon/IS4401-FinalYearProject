package com.michelle_condon.is4401_finalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michelle_condon.is4401_finalyearproject.Models.FetchRequests;
import com.michelle_condon.is4401_finalyearproject.Models.Items;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.DisplayPages.Review;

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
        viewHolderClass.empHolidayName.setText(fetchRequests.getEmpHolidayName());
        viewHolderClass.status.setText(fetchRequests.getStatus());
    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return fetchRequestsList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder {
        //Declaring variables
        TextView dates, employeeEmail, empHolidayName, status;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            //Assigning values to the variables by resource Id's
            dates = itemView.findViewById(R.id.txtReviewDates);
            employeeEmail = itemView.findViewById(R.id.txtEmployeeEmailReview);
            empHolidayName = itemView.findViewById(R.id.txtEmpHolidayName);
            status = itemView.findViewById(R.id.txtStatus);

            //Code to transfer data to the next form was found on Stackoverflow at
            // "https://stackoverflow.com/questions/37186805/start-new-activity-with-onclick-in-recyclerview/44898559"

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, Review.class);
                    String a = dates.getText().toString();
                    String b = employeeEmail.getText().toString();
                    String c = empHolidayName.getText().toString();
                    String d = status.getText().toString();

                    intent.putExtra("dates", a);
                    intent.putExtra("email", b);
                    intent.putExtra("empHolidayName", c);
                    intent.putExtra("status", c);
                    context.startActivity(intent);
                }
            });


        }

    }}

