package com.organizer.layouts.todo;

import android.content.Context;
import android.widget.TextView;
import com.organizer.layouts.BaseLayout;

public class ToDoLayout extends BaseLayout
{
    public ToDoLayout(Context context)
    {
        super(context);
        
        TextView textView = new TextView(context);
        textView.setText("ToDo");
        addView(textView);
    }
}
