package com.example.organizer;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.organizer.Functions.*;
import static java.lang.Math.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.layout_main);
        RecyclerView contentLayout = findViewById(R.id.contentLayout);
        contentLayout.setHorizontalScrollBarEnabled(false); // TODO need?
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        actionBarSize = dpToPx(this, 56);
        //CalendarView
/* test :)
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 6, 15);
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 15, 23);
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 23, 24);
*/
        // Initialize days
        addDays(dayAmount, 0);
        // Create adapter passing in the sample user data
        final DaysAdapter adapter = new DaysAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        contentLayout.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        contentLayout.setLayoutManager(linearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                // Append the next page of data into the adapter
                // This method probably sends out a network request and appends new data items to your adapter.
                // Send an API request to retrieve appropriate paginated data
                //  --> Send the request including an offset value (i.e `page`) as a query parameter.
                //  --> Deserialize and construct new model objects from the API response
                //  --> Append the new data objects to the existing set of items inside the array of items
                //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()

                int offset = page * dayAmount;

                // + page / abs(page);
                /*
                for(int i = offset; i < dayAmount; i++){
                    days.remove(i);
                }*/
                addDays(dayAmount, offset);
                //days.addAll(min(0, offset), Day.createDaysList(dayAmount, offset));
                //RecyclerView contentLayout = findViewById(R.id.contentLayout);
                //contentLayout.swapAdapter(new DaysAdapter(MainActivity.this, days), false); // TODO slow???
            }
        };
        contentLayout.addOnScrollListener(scrollListener);
    }
}