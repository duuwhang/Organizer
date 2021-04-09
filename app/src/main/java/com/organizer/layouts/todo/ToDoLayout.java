package com.organizer.layouts.todo;

import android.content.Context;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import com.organizer.layouts.BaseLayout;

public class ToDoLayout extends BaseLayout
{
    private List<ToDoElement> elements = new ArrayList<>();
    
    public ToDoLayout(Context context)
    {
        super(context);
        for (int i = 0; i < 10; i++)
        {
            Button b = new Button(context);
            b.setText(Integer.toString(i));
            addView(b);
        }
        
        setLayoutParams(new LayoutParams(
            getChildCount() * 300,
            LayoutParams.MATCH_PARENT));
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            getChildAt(i).layout(left + (300 * i), top, left + (300 * i) + 300, bottom);
        }
    }
}

class ToDoElement
{
    public String description;
}
