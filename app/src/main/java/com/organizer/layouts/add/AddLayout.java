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
        
        AddTaskLayout addTaskLayout = new AddTaskLayout(context);
        addView(addTaskLayout);
        
        AddEventLayout addEventLayout = new AddEventLayout(context);
        addView(addEventLayout);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        float ratio = (float) Integer.max(width, height) / Integer.min(width, height);
        int widthMargin = ((int) (width / (ratio * (height >= width ? 3 : 2))));
        int heightMargin = ((int) (height / (ratio * (width > height ? 3 : 2))));
        
        for (int i = 0; i < getChildCount(); i++)
        {
            getChildAt(i).layout(left + widthMargin / 2, top + heightMargin / 2, right - widthMargin / 2, bottom - heightMargin / 2);
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
    
    public void show(Object addLayoutClass)
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
