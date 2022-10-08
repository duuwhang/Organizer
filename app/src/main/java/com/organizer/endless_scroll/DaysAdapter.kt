package com.organizer.endless_scroll

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.organizer.*
import com.organizer.MainActivity.Companion.inject
import com.organizer.layouts.MainLayout
import com.organizer.layouts.calendar.DayLayout

// Pass in the contact array into the constructor
// Store a member variable for the contacts
class DaysAdapter : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    private val context: Context by inject()
    private val mainLayout: MainLayout by inject()
    private val displayController: DisplayMetricsController by inject()
    private val dateController: DateController by inject()

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        //View dayView = inflater.inflate(R.layout.layout_day, parent, false);

        // Return a new holder instance
        return ViewHolder(DayLayout())
        //return new ViewHolder(dayView);
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        //int realPos = position % days.size();
        val day = dateController.days[position]!! // + Integer.MAX_VALUE / 2);
        val dayLayout = viewHolder.dayLayout
        dayLayout.layoutParams.width =
            displayController.screenWidth / mainLayout.calendarLayout.dayAmount
        // Set item views based on your views and data model
        val textView = viewHolder.textView
        textView.text = "${day.weekday} ${day.d}.${day.m}."
        if (day.id == dateController.today) {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            dateController.textViewSize = textView.lineHeight
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        }
        dateController.dictionary[day.id]?.forEach {
            it.generateButton()
            dayLayout.addView(it.button)
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
        //return MainActivity.getDateController().days.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var dayLayout: DayLayout
        var textView: TextView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            dayLayout = itemView as DayLayout
            textView = dayLayout.textView
        }
    }
}
