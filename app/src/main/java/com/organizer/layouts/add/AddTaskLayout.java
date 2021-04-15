package com.organizer.layouts.add;

import android.content.Context;
import android.widget.Button;
import com.organizer.layouts.BaseLayout;

public class AddTaskLayout extends BaseLayout
{
    public AddTaskLayout(Context context)
    {
        super(context);
        Button button = new Button(context);
        button.setText("task");
        addView(button);
    }
}
