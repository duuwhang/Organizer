package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import androidx.annotation.NonNull;
import com.organizer.layouts.BaseLayout;

public class AddLayout extends BaseLayout
{
    public AddLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.BLACK);
        drawable.setAlpha(125);
        setBackground(drawable);
        
        AddEventLayout addEventLayout = new AddEventLayout(context);
        addView(addEventLayout);
        
        AddTaskLayout addTaskLayout = new AddTaskLayout(context);
        addView(addTaskLayout);
    }
    
    public void showLayout(Object addLayoutClass)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            if (getChildAt(i).getClass() == addLayoutClass)
            {
                getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }
    
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility)
    {
        if (visibility == INVISIBLE)
        {
            for (int i = 0; i < getChildCount(); i++)
            {
                getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
