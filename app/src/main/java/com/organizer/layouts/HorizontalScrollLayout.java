package com.organizer.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.organizer.MainActivity;
import com.organizer.layouts.todo.ToDoLayout;

public class HorizontalScrollLayout extends HorizontalScrollView
{
    private boolean scrollable = true;
    private Context context;
    LinearLayout linearLayout;
    ToDoLayout toDoLayout;
    
    public boolean isScrollable()
    {
        return scrollable;
    }
    
    public void setScrollable(boolean scrollable)
    {
        this.scrollable = scrollable;
    }
    
    public HorizontalScrollLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }
    
    public HorizontalScrollLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }
    
    public HorizontalScrollLayout(Context context)
    {
        super(context);
        this.context = context;
        init();
    }
    
    private void init()
    {
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        
        linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        addView(linearLayout);
    }
    
    public void addContentView(ToDoLayout contentView)
    {
        toDoLayout = contentView;
        linearLayout.addView(contentView);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (isScrollable())
        {
            return super.onInterceptTouchEvent(ev);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (isScrollable())
        {
            super.performClick();
            return super.onTouchEvent(ev);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean performClick()
    {
        return super.performClick();
    }
    
    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop)
    {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        int width = toDoLayout.getMaxWidth() - MainActivity.getDisplayMetricsController().getScreenWidth();
        if (left > width)
        {
            scrollTo(width, 0);
        }
    }
}
