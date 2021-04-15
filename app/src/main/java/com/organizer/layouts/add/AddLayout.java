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
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        int widthMargin = width / 6;
        int childHeight = height / 4;
        
        for (int i = 0; i < getChildCount(); i++)
        {
            getChildAt(i).layout(left + widthMargin / 2, top + childHeight / 2, right - widthMargin / 2, bottom - childHeight / 2);
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
}
