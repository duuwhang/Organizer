package com.organizer.layouts.calendar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organizer.DateController
import com.organizer.MainActivity.Companion.inject
import com.organizer.MainActivity.Companion.injectNow
import com.organizer.endless_scroll.DaysAdapter
import com.organizer.endless_scroll.EndlessRecyclerViewScrollListener

class CalendarLayout : RecyclerView(injectNow()) {

    private val dateController: DateController by inject()

    val dayAmount = 3

    init {
        isHorizontalScrollBarEnabled = false // TODO need?
        // Initialize days
        dateController.addDays(dayAmount, 0)
        // Create adapter passing in the sample user data
        val daysAdapter = DaysAdapter()
        // Attach the adapter to the recyclerview to populate items
        this.adapter = daysAdapter
        // Set layout manager to position the items
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.layoutManager = linearLayoutManager

        // Retain an instance so that you can call `resetState()` for fresh searches
        val scrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(linearLayoutManager, dayAmount) {
                override fun onLoadMore(page: Int, view: RecyclerView?) {
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
                    val offset = page * dayAmount

                    // + page / abs(page);
                    /*
                for(int i = offset; i < dayAmount; i++){
                    days.remove(i);
                }*/dateController.addDays(dayAmount, offset)
                    //days.addAll(min(0, offset), Day.createDaysList(dayAmount, offset));
                    view?.swapAdapter(DaysAdapter(), false) // TODO slow???
                }
            }
        addOnScrollListener(scrollListener)
    }
}
