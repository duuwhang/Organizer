package com.organizer.endless_scroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.organizer.DateController;
import com.organizer.Day;
import com.organizer.MainActivity;
import com.organizer.R;
import com.organizer.Task;
import com.organizer.layouts.calendar.DayLayout;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder>
{
    // Store a member variable for the contacts
    private Context context;
    
    // Pass in the contact array into the constructor
    public DaysAdapter(Context context)
    {
        this.context = context;
    }
    
    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //LayoutInflater inflater = LayoutInflater.from(context);
        
        // Inflate the custom layout
        //View dayView = inflater.inflate(R.layout.layout_day, parent, false);
        
        // Return a new holder instance
        return new ViewHolder(new DayLayout(context));
        //return new ViewHolder(dayView);
    }
    
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DaysAdapter.ViewHolder viewHolder, int position)
    {
        DateController dateController = MainActivity.getDateController();
        // Get the data model based on position
        //int realPos = position % days.size();
        Day day = dateController.days.get(position);// + Integer.MAX_VALUE / 2);
        
        DayLayout dayLayout = viewHolder.dayLayout;
        dayLayout.getLayoutParams().width = MainActivity.getDisplayMetricsController().getScreenWidth() / MainActivity.getInstance().getLayout().getCalendarLayout().getDayAmount();
        // Set item views based on your views and data model
        TextView textView = viewHolder.textView;
        textView.setText((day.weekday + " " + day.d + "." + day.m + "."));
        
        if (day.id == dateController.today)
        {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            dateController.textViewSize = textView.getLineHeight();
        }
        else
        {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        
        List<Task> taskList = dateController.dictionary.get(day.id);
        if (taskList != null)
        {
            for (Task task : taskList)
            {
                task.generateButton();
                dayLayout.addView(task.button);
            }
        }
    }
    
    // Returns the total count of items in the list
    @Override
    public int getItemCount()
    {
        return (MainActivity.getDateController().days == null) ? 0 : Integer.MAX_VALUE;
        //return MainActivity.getDateController().days.size();
    }
    
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public DayLayout dayLayout;
        public TextView textView;
        
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView)
        {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            
            dayLayout = (DayLayout) itemView;
            textView = dayLayout.getTextView();
        }
    }
}
