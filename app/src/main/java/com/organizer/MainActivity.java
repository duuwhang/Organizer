package com.organizer;

import java.util.Objects;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;
import static com.organizer.Functions.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        actionBarSize = dpToPx(context, context.getResources().getInteger(R.integer.actionBarSize));
        MainLayout mainLayout = new MainLayout(context);
        setContentView(mainLayout);

        new Task(context, buildId(todayD,todayM,todayY), "task", 8, 12);
        new Task(context, buildId(todayD+1,todayM,todayY), "task", 6, 15);
        new Task(context, buildId(todayD+1,todayM,todayY), "task", 23, 24);
        new Task(context, buildId(todayD+2,todayM,todayY), "task", 6, 17);
        new Task(context, buildId(todayD+2,todayM,todayY), "task", 17, 18);

        CalendarLayout calendarLayout = new CalendarLayout(context);
        mainLayout.addView(calendarLayout);
        TasksLayout tasksLayout = new TasksLayout(context);
        mainLayout.addView(tasksLayout);
    }
}
