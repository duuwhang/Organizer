package com.organizer;

import android.content.Context;
import android.widget.Button;
import java.util.ArrayList;
import static com.organizer.Functions.*;

public class Task  {
    Context context;
    int id = today;
    String location = "home";
    double start = 6;
    double end = start + 1;
    Button button;
    Task(Context context, int id, String location, double start, double end){
        this.context = context;
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = end;
        initTask();
    }
    Task(Context context, int id, String location, double start){
        this.context = context;
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = start + 1;
        initTask();
    }
    Task(Context context, int id, String location){
        this.context = context;
        this.id = id;
        this.location = location;
        initTask();
    }
    Task(Context context, int id, double start, double end){
        this.context = context;
        this.id = id;
        this.start = start;
        this.end = end;
        initTask();
    }
    Task(Context context, int id, double start){
        this.context = context;
        this.id = id;
        this.start = start;
        this.end = start + 1;
        initTask();
    }
    Task(Context context, int id){
        this.context = context;
        this.id = id;
        initTask();
    }
    private void initTask(){
        if(!dictionary.containsKey(id)){
            dictionary.put(id, new ArrayList<Task>());
        }
        dictionary.get(id).add(this);

        button = new Button(context);
        button.setText(location);
        button.setY(calculateY(start));
        button.setHeight(calculateHeight(start, end));
    }
}