package com.organizer.layouts.calendar;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.organizer.MainActivity;
import com.organizer.endless_scroll.DaysAdapter;
import com.organizer.endless_scroll.EndlessRecyclerViewScrollListener;

public class CalendarLayout extends RecyclerView
{
    private int dayAmount = 3;
    
    public CalendarLayout(Context context)
    {
        super(context);
        this.setHorizontalScrollBarEnabled(false); // TODO need?
        // Initialize days
        MainActivity.getDateController().addDays(dayAmount, 0);
        // Create adapter passing in the sample user data
        final DaysAdapter daysAdapter = new DaysAdapter(context);
        // Attach the adapter to the recyclerview to populate items
        this.setAdapter(daysAdapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        this.setLayoutManager(linearLayoutManager);
        
        // Retain an instance so that you can call `resetState()` for fresh searches
        
        
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, dayAmount)
        {
            @Override
            public void onLoadMore(int page, RecyclerView view)
            {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                // Append the next page of data into the adapter
                // This method probably sends out a network request and appends new data items to your adapter.
                // Send an API request to retrieve appropriate paginated data
                //  --> Send the request including an offset value (i.e `page`) as a query parameter.
                //  --> Deserialize and construct new model objects from the API response
                //  --> Append the new data objects to the existing set of items inside the array of items
                //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()
                
                //Toast.makeText(getContext(), "onLoadMore", Toast.LENGTH_SHORT).show();
                
                int offset = page * dayAmount;
                
                // + page / abs(page);
                /*
                for(int i = offset; i < dayAmount; i++){
                    days.remove(i);
                }*/
                MainActivity.getDateController().addDays(dayAmount, offset);
                //days.addAll(min(0, offset), Day.createDaysList(dayAmount, offset));
                //RecyclerView contentLayout = findViewById(R.id.contentLayout);
                //contentLayout.swapAdapter(new DaysAdapter(getApplicationContext(), days), false); // TODO slow???
            }
        };
        this.addOnScrollListener(scrollListener);
    }
    
    public int getDayAmount()
    {
        return dayAmount;
    }
}
