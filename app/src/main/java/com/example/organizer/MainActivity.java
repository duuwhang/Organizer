package com.example.organizer;

import android.animation.AnimatorSet;
import android.animation.*;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.LinearLayout;
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
        final Context context = this;
        this.getSupportActionBar().hide();
        setContentView(R.layout.layout_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        actionBarSize = dpToPx(this, 56);



        new Task(context, buildId(todayD,todayM,todayY), "task", 8, 12);
        new Task(context, buildId(todayD+1,todayM,todayY), "task", 6, 15);
        new Task(context, buildId(todayD+1,todayM,todayY), "task", 23, 24);
        new Task(context, buildId(todayD+2,todayM,todayY), "task", 6, 17);
        new Task(context, buildId(todayD+2,todayM,todayY), "task", 17, 18);
/*


        final RecyclerView tasksLayout = findViewById(R.id.tasksLayout);
        final RecyclerView calendarLayout = findViewById(R.id.calendarLayout);
        calendarLayout.setHorizontalScrollBarEnabled(false); // TODO need?
        // Initialize days
        addDays(dayAmount, 0);
        // Create adapter passing in the sample user data
        final DaysAdapter daysAdapter = new DaysAdapter(getApplicationContext());
        // Attach the adapter to the recyclerview to populate items
        calendarLayout.setAdapter(daysAdapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        calendarLayout.setLayoutManager(linearLayoutManager);
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
                }*//*
                addDays(dayAmount, offset);
                //days.addAll(min(0, offset), Day.createDaysList(dayAmount, offset));
                //RecyclerView contentLayout = findViewById(R.id.contentLayout);
                //contentLayout.swapAdapter(new DaysAdapter(context, days), false); // TODO slow???
            }
        };
        calendarLayout.addOnScrollListener(scrollListener);
*/


        MainLayout mainLayout = new MainLayout(context);
        mainLayout = findViewById(R.id.mainLayout);
        //mainLayout.addView(calendarLayout);

        //mainLayout.addView(calendarLayout);
/*
        final GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
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
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(AnimationUtils.loadAnimation(context,R.anim.fadeoutdown));
                animationSet.addAnimation(AnimationUtils.loadAnimation(context,R.anim.slideup));

                View layout = calendarLayout;
                layout.startAnimation(animationSet);

                //calendarLayout.startAnimation(animationSet);
            }
            void onSwipeRight() {

            }
            void onSwipeLeft() {

            }
            void onSwipeDown() {
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(AnimationUtils.loadAnimation(context,R.anim.fadeindown));
                animationSet.addAnimation(AnimationUtils.loadAnimation(context,R.anim.slidedown));

                View layout = calendarLayout;
                layout.startAnimation(animationSet);

                //calendarLayout.startAnimation(animationSet);
            }
        });
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        };
        mainLayout.setOnTouchListener(touchListener);*/
    }

}