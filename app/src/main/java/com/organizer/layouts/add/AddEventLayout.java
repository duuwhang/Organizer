package com.organizer.layouts.add;

import android.content.Context;
import android.widget.Button;
import com.organizer.layouts.BaseLayout;

public class AddEventLayout extends BaseLayout
{
    public AddEventLayout(Context context)
    {
        super(context);
        Button button = new Button(context);
        button.setText("event");
        addView(button);
    }
}
