package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import com.organizer.layouts.BaseLayout;

public class AddEventLayout extends BaseLayout
{
    public AddEventLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.DKGRAY);
        setBackground(drawable);
    }
}
