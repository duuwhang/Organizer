package com.example.organizer;

import android.animation.AnimatorSet;
import android.animation.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.Toast;

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
        final RecyclerView contentLayout = findViewById(R.id.contentLayout);
        contentLayout.setHorizontalScrollBarEnabled(false); // TODO need?
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        actionBarSize = dpToPx(this, 56);
        //CalendarView
/* test:)
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 6, 15);
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 15, 23);
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 23, 24);
*/
        //Toast.makeText(getApplicationContext(),"Scrolled", Toast.LENGTH_SHORT).show();
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 15, 23);
        new Task(this, buildId(todayD+1,todayM,todayY), "task", 23, 24);
        new Task(this, buildId(todayD+2,todayM,todayY), "task", 6, 17);
        new Task(this, buildId(todayD+2,todayM,todayY), "task", 17, 18);
        new Task(this, buildId(todayD,todayM,todayY), "task", 8, 12);

        new Task(this, buildId(todayD+1,todayM,todayY), "task", 6, 15);

        final GestureDetector mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
        {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }

            void onSwipeUp() {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);
                contentLayout.startAnimation(animation);
            }
            void onSwipeRight() {

            }
            void onSwipeLeft() {

            }
            void onSwipeDown() {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadein);
                contentLayout.startAnimation(animation);
            }
        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        };
        contentLayout.setOnTouchListener(touchListener);

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