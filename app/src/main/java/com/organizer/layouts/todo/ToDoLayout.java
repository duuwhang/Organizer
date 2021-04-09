package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class ToDoLayout extends BaseLayout
{
    private List<String> elements = new ArrayList<>();
    private final Rect childRect = new Rect();
    
    public ToDoLayout(Context context)
    {
        super(context);
        
        elements.addAll(Arrays.asList("Task1", "Task2", "Task3", "Task4", "Task5", "Task6", "Task7"));
        
        for (int i = 0; i < elements.size(); i++)
        {
            Button button = new Button(context);
            button.setText(elements.get(i));
            button.setWidth(new Random().nextInt(500) + 500);
            addView(button);
        }
        
        setLayoutParams(new LayoutParams(
            3000,
            LayoutParams.MATCH_PARENT));
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int[] rowWidths = new int[4];
        for (int i = 0; i < getChildCount(); i++)
        {
            View child = getChildAt(i);
            int minWidthIndex = 0;
            for (int row = 1; row < rowWidths.length; row++)
            {
                if (rowWidths[row] < rowWidths[minWidthIndex])
                {
                    minWidthIndex = row;
                }
            }
            int childHeight = MainActivity.getDisplayMetricsController().getScreenHeight() / rowWidths.length;
            childRect.left = rowWidths[minWidthIndex];
            childRect.top = childHeight * minWidthIndex % MainActivity.getDisplayMetricsController().getScreenHeight();
            childRect.right = childRect.left + child.getMeasuredWidth();
            childRect.bottom = childRect.top + childHeight;
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
            rowWidths[minWidthIndex] += child.getMeasuredWidth();
        }
    }
}

class ToDoElement
{
    public String description;
}
