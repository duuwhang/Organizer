package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import android.widget.Button;
import com.organizer.MainActivity;
import com.organizer.R;
import com.organizer.layouts.BaseLayout;

public class TaskLayout extends BaseLayout
{
    boolean completed;
    int left;
    int right;
    int row;
    String title = "Task";
    Button button;
    private final Rect childRect = new Rect();
    
    public TaskLayout(Context context)
    {
        super(context);
        init();
    }
    
    public TaskLayout(Context context, String title)
    {
        super(context);
        this.title = title;
        init();
    }
    
    private void init()
    {
        PaintDrawable shape = new PaintDrawable(Color.WHITE);
        shape.setCornerRadius(75);
        shape.setTint(Color.parseColor(getResources().getString(R.color.colorPrimaryDark)));
        
        button = new Button(context);
        button.setText(title);
        button.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        button.setBackground(shape);
        button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(button);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        for (int i = 0; i < getChildCount(); i++)
        {
            int widthMargin = MainActivity.getDisplayMetricsController().dpToPx(1200);
            int heightMargin = MainActivity.getDisplayMetricsController().dpToPx(800);
            
            childRect.left = left == 0 ? widthMargin * 2 : widthMargin;
            childRect.top = heightMargin + (row == 0 ? heightMargin : 0);
            childRect.right = width - widthMargin;
            childRect.bottom = height - heightMargin - (row == 5 ? heightMargin : 0);
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }
}
