package com.example.organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TasksLayout extends LinearLayout {
    public TasksLayout(@NonNull Context context) {
        super(context);

        Toast.makeText(context, "tasklayout", Toast.LENGTH_SHORT).show();





        this.addView(new Button(context));
    }
}
