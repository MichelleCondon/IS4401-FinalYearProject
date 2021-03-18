package com.michelle_condon.is4401_finalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michelle_condon.is4401_finalyearproject.Models.FetchEmployees;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.UpdatePages.UpdateSchedule;

import java.util.List;

public class MgtEmployeeAdapter extends RecyclerView.Adapter {

    //Code below to display data in view holders from Firebase based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    //List Employees
    List<FetchEmployees> fetchEmployeesList;

    public MgtEmployeeAdapter(List<FetchEmployees> fetchEmployeesList) {
        this.fetchEmployeesList = fetchEmployeesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the item_layout2.xml to output the data into the view holder within the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout7, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        //Setting the hours for every day of the week by pulling the data from Firebase using getters
        FetchEmployees fetchEmployees = fetchEmployeesList.get(position);
        viewHolderClass.weekNumber.setText(fetchEmployees.getWeekNumber());
        viewHolderClass.employeeName.setText(fetchEmployees.getEmployeeName());
        viewHolderClass.monday.setText(fetchEmployees.getMonday());
        viewHolderClass.tuesday.setText(fetchEmployees.getTuesday());
        viewHolderClass.wednesday.setText(fetchEmployees.getWednesday());
        viewHolderClass.thursday.setText(fetchEmployees.getThursday());
        viewHolderClass.friday.setText(fetchEmployees.getFriday());
        viewHolderClass.saturday.setText(fetchEmployees.getSaturday());
        viewHolderClass.sunday.setText(fetchEmployees.getSunday());

    }

    @Override
    public int getItemCount() {
        //Returns the size of the fetchData List
        return fetchEmployeesList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder {
        //Declaring variables
        TextView weekNumber, employeeName, monday, tuesday, wednesday, thursday, friday, saturday, sunday;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            //Assigning values to the variables by resource Id's
            weekNumber = itemView.findViewById(R.id.weekNumber);
            employeeName = itemView.findViewById(R.id.employeeName);
            monday = itemView.findViewById(R.id.monday);
            tuesday = itemView.findViewById(R.id.tuesday);
            wednesday = itemView.findViewById(R.id.wednesday);
            thursday = itemView.findViewById(R.id.thursday);
            friday = itemView.findViewById(R.id.friday);
            saturday = itemView.findViewById(R.id.saturday);
            sunday = itemView.findViewById(R.id.sunday);

            //https://stackoverflow.com/questions/37186805/start-new-activity-with-onclick-in-recyclerview/44898559
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, UpdateSchedule.class);
                    String a = weekNumber.getText().toString();
                    String b = monday.getText().toString();
                    String c = tuesday.getText().toString();
                    String d = wednesday.getText().toString();
                    String e = thursday.getText().toString();
                    String f = friday.getText().toString();
                    String g = saturday.getText().toString();
                    String h = sunday.getText().toString();
                    String i = employeeName.getText().toString();
                    intent.putExtra("employeeName", i);
                    intent.putExtra("weekNumber", a);
                    intent.putExtra("monday", b);
                    intent.putExtra("tuesday", c);
                    intent.putExtra("wednesday", d);
                    intent.putExtra("thursday", e);
                    intent.putExtra("friday", f);
                    intent.putExtra("saturday", g);
                    intent.putExtra("sunday", h);
                    context.startActivity(intent);
                }
            });

        }
    }
}
//End
