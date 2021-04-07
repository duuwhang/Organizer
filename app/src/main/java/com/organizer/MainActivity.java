package com.organizer;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import com.organizer.layouts.MainLayout;
import com.organizer.layouts.calendar.CalendarLayout;
import com.organizer.layouts.todo.TasksLayout;

public class MainActivity extends AppCompatActivity
{
    public static DisplayMetricsController displayMetricsController;
    public static DateController dateController;
    private static MainActivity activity;
    
    public MainActivity()
    {
        activity = this;
    }
    
    public static MainActivity getInstance()
    {
        return activity;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        
        displayMetricsController = new DisplayMetricsController(getWindowManager().getDefaultDisplay(), getResources().getDisplayMetrics().density);
        dateController = new DateController();
        
        
        MainLayout mainLayout = new MainLayout(this);
        setContentView(mainLayout);
        
        new Task(this, dateController.buildId(dateController.todayD, dateController.todayM, dateController.todayY), "task", 8, 12);
        new Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "task", 6, 15);
        new Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "task", 23, 24);
        new Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "task", 6, 17);
        new Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "task", 17, 18);
        
        CalendarLayout calendarLayout = new CalendarLayout(this);
        mainLayout.addView(calendarLayout);
        TasksLayout tasksLayout = new TasksLayout(this);
        mainLayout.addView(new Button(this));
    }
}
