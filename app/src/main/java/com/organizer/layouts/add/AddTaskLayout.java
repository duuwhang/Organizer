package com.organizer.layouts.add;

import android.content.Context;
import android.widget.TextView;
import com.organizer.layouts.BaseLayout;

public class AddTaskLayout extends BaseLayout
{
    public AddTaskLayout(Context context)
    {
        super(context);
        TextView textView = new TextView(context);
        textView.setText("task");
        addView(textView);
    }
}
