package com.organizer;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class TasksLayout extends LinearLayout {
    public TasksLayout(@NonNull Context context) {
        super(context);

        //Toast.makeText(context, "tasklayout", Toast.LENGTH_SHORT).show();





        this.addView(new Button(context));
    }
}
