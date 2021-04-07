package com.organizer.layouts.todo;

import android.content.Context;
import android.widget.TextView;
import com.organizer.layouts.BaseLayout;

public class TasksLayout extends BaseLayout
{
    public TasksLayout(Context context)
    {
        super(context);
        
        TextView textView = new TextView(context);
        textView.setText("ToDo");
        addView(textView);
    }
}
