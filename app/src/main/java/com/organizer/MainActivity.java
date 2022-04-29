package com.organizer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import com.organizer.layouts.MainLayout;

public class MainActivity extends AppCompatActivity
{
    private static MainActivity activity;
    private static DisplayMetricsController displayMetricsController;
    private static DateController dateController;
    private MainLayout mainLayout;
    
    public MainActivity()
    {
        activity = this;
    }
    
    public static MainActivity getInstance()
    {
        return activity;
    }
    
    public static DisplayMetricsController getDisplayMetricsController()
    {
        return displayMetricsController;
    }
    
    public static DateController getDateController()
    {
        return dateController;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        
        displayMetricsController = new DisplayMetricsController(getWindowManager(), getResources().getDisplayMetrics().density);
        dateController = new DateController();
        
        mainLayout = new MainLayout(this);
        setContentView(mainLayout);
        
        new Task(this, dateController.buildId(dateController.todayD, dateController.todayM, dateController.todayY), "Work", 8, 12);
        new Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Programming", 6, 8);
        new Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Dancing", 9, 15);
        new Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Work", 6, 17);
        new Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Family Dinner", 17, 18);
        new Task(this, dateController.buildId(dateController.todayD + 3, dateController.todayM, dateController.todayY), "Dancing", 6, 24);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        //mainLayout.getToDoLayout().init();
    }
    
    public MainLayout getLayout()
    {
        return mainLayout;
    }
}
