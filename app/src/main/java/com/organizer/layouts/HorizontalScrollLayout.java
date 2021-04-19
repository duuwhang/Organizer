package com.organizer.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.organizer.MainActivity;

public class HorizontalScrollLayout extends HorizontalScrollView
{
    private boolean scrollable = true;
    private Context context;
    LinearLayout linearLayout;
    
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
        setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        
        linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        addView(linearLayout);
    }
    
    public void addContentView(View contentView)
    {
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
            return super.onTouchEvent(ev);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        int width = MainActivity.getInstance().getLayout().getToDoLayout().getMaxWidth() - MainActivity.getDisplayMetricsController().getScreenWidth();
        if (l > width)
        {
            scrollTo(width, 0);
        }
    }
}
