package com.organizer.layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class AddTaskLayout extends BaseLayout
{
    
    public AddTaskLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.BLACK);
        drawable.setAlpha(60);
        setBackground(drawable);
    }
}
