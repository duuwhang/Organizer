package com.organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.organizer.Functions.*;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private Context context;

    // Pass in the contact array into the constructor
    public DaysAdapter(Context context) {
        this.context = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View dayView = inflater.inflate(R.layout.layout_day, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(dayView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DaysAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        //int realPos = position % days.size();
        Day day = days.get(position);// + Integer.MAX_VALUE / 2);

        ConstraintLayout dayLayout = viewHolder.dayLayout;
        dayLayout.getLayoutParams().width = screenWidth / dayAmount;
        // Set item views based on your views and data model
        TextView textView = viewHolder.textView;
        textView.setText((day.weekday + " " + day.d + "." + day.m + "."));

        if(day.id == today){
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            textViewSize = textView.getLineHeight();
        }else{
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }

        List<Task> taskList = dictionary.get(day.id);
        if(taskList != null) {
            for (Task task : taskList) {
                dayLayout.addView(task.button);
            }
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return (days == null) ? 0 : Integer.MAX_VALUE;
        //return days.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ConstraintLayout dayLayout;
        public TextView textView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dayLayout = itemView.findViewById(R.id.dayLayout);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}