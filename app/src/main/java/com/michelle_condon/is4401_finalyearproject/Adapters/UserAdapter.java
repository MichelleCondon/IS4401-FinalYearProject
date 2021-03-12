package com.michelle_condon.is4401_finalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.michelle_condon.is4401_finalyearproject.EditProfile;
import com.michelle_condon.is4401_finalyearproject.ListAdapter;
import com.michelle_condon.is4401_finalyearproject.Models.User;
import com.michelle_condon.is4401_finalyearproject.R;
import com.michelle_condon.is4401_finalyearproject.UpdateProducts;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {

        //Code below to display data in view holders from Firebase based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

        //List Item
        List<User> userData;

public UserAdapter(List<User> userData) {
        this.userData = userData;
        }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the item_layout3.xml to output the data into the view holder within the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout6, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    UserAdapter.ViewHolderClass viewHolderClass = (UserAdapter.ViewHolderClass) holder;
        //Setting the hours for every day of the week by pulling the data from Firebase using getters
        User vUsers = userData.get(position);
        viewHolderClass.fullname.setText(vUsers.getFullName());
        viewHolderClass.employeeId.setText(vUsers.getEmployeeId());
        viewHolderClass.position.setText(vUsers.getPosition());
        viewHolderClass.phonenumber.setText(String.valueOf(vUsers.getPhoneNumber()));
        viewHolderClass.email.setText(vUsers.getEmail());



        }

@Override
public int getItemCount() {
        //Returns the size of the fetchData List
        return userData.size();
        }

public static class ViewHolderClass extends RecyclerView.ViewHolder {
    //Declaring variables
    TextView fullname, email, employeeId, position, phonenumber;

    public ViewHolderClass(@NonNull View itemView) {
        super(itemView);
        //Assigning values to the variables by resource Id's
        fullname = itemView.findViewById(R.id.userFullName);
        employeeId = itemView.findViewById(R.id.userEmployeeId);
        position = itemView.findViewById(R.id.userPosition);
        phonenumber = itemView.findViewById(R.id.userPhoneNumber);
        email = itemView.findViewById(R.id.userEmailAddress);

        //https://stackoverflow.com/questions/37186805/start-new-activity-with-onclick-in-recyclerview/44898559
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditProfile.class);
                String a = fullname.getText().toString();
                String b = employeeId.getText().toString();
                String c = phonenumber.getText().toString();
                String d = position.getText().toString();
                String e = email.getText().toString();
                intent.putExtra("fullName", a);
                intent.putExtra("employeeId", b);
                intent.putExtra("phoneNumber", c);
                intent.putExtra("position", d);
                intent.putExtra("email", e);
                context.startActivity(intent);
            }
        });

    }


}
}
