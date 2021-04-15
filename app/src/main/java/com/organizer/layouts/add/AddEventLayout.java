package com.organizer.layouts.add;

import android.content.Context;
import android.widget.TextView;
import com.organizer.layouts.BaseLayout;

public class AddEventLayout extends BaseLayout
{
    public AddEventLayout(Context context)
    {
        super(context);
        TextView textView = new TextView(context);
        textView.setText("event");
        addView(textView);
    }
}
