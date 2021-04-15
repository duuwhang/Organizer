package com.organizer.layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class AddLayout extends BaseLayout
{
    
    public AddLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.BLACK);
        drawable.setAlpha(125);
        setBackground(drawable);
    }
}
