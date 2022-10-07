package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class AddEventLayout extends BaseLayout
{
    private Button addButton;
    private final Rect childRect = new Rect();
    
    public AddEventLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.DKGRAY);
        setBackground(drawable);
        
        addButton = new Button(context);
        addButton.setText("Add");
        addButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.getInstance().getLayout().toggleAddLayout(false);
            }
        });
        addView(addButton);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        
        for (int i = 1; i < getChildCount(); i++)
        {
            getChildAt(i).layout(0, 0, width, height);
        }
        
        int margin = MainActivity.getDisplayMetricsController().dpToPx(6);
        childRect.left = width - addButton.getMeasuredWidth() - margin;
        childRect.top = height - addButton.getMeasuredHeight() - margin;
        childRect.right = width - margin;
        childRect.bottom = height - margin;
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
    }
}
