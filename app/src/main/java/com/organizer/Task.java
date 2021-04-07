package com.organizer;

import android.content.Context;
import android.widget.Button;
import java.util.ArrayList;

public class Task
{
    Context context;
    int id;
    String location = "home";
    double start = 6;
    double end = start + 1;
    public Button button;
    
    Task(Context context, int id, String location, double start, double end)
    {
        this.context = context;
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = end;
        initTask();
    }
    
    Task(Context context, int id, String location, double start)
    {
        this.context = context;
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = start + 1;
        initTask();
    }
    
    Task(Context context, int id, String location)
    {
        this.context = context;
        this.id = id;
        this.location = location;
        initTask();
    }
    
    Task(Context context, int id, double start, double end)
    {
        this.context = context;
        this.id = id;
        this.start = start;
        this.end = end;
        initTask();
    }
    
    Task(Context context, int id, double start)
    {
        this.context = context;
        this.id = id;
        this.start = start;
        this.end = start + 1;
        initTask();
    }
    
    Task(Context context, int id)
    {
        this.context = context;
        this.id = id;
        initTask();
    }
    
    private void initTask()
    {
        DateController dateController = MainActivity.getDateController();
        if (!dateController.dictionary.containsKey(id))
        {
            dateController.dictionary.put(id, new ArrayList<>());
        }
        dateController.dictionary.get(id).add(this);
        
        button = new Button(context);
        button.setText(location);
        button.setY(dateController.calculateY(start));
        button.setHeight(dateController.calculateHeight(start, end));
    }
}
