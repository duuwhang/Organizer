package com.organizer.layouts.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import android.widget.TextView;
import com.organizer.layouts.BaseLayout;
import static com.organizer.R.color.colorPrimaryDark;

public class TaskLayout extends BaseLayout
{
    boolean completed;
    int left;
    int right;
    int row;
    TextView background;
    TextView title;
    ToDoLayout parent;
    private final Rect backgroundRect = new Rect();
    private final Rect childRect = new Rect();
    
    public TaskLayout(Context context, ToDoLayout parent)
    {
        super(context);
        this.parent = parent;
        init();
        this.title.setText("Task");
    }
    
    public TaskLayout(Context context, ToDoLayout parent, String title)
    {
        super(context);
        this.parent = parent;
        init();
        this.title.setText(title);
    }
    
    @SuppressLint("ResourceType")
    private void init()
    {
        PaintDrawable shape = new PaintDrawable(Color.WHITE);
        shape.setCornerRadius(parent.roundingRadius);
        shape.setTint(Color.parseColor(getResources().getString(colorPrimaryDark)));
        
        background = new TextView(context);
        background.setText("");
        background.setBackground(shape);
        addView(background);
        
        title = new TextView(context);
        title.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(title);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        
        int widthMargin = parent.widthMargin / 2;
        int heightMargin = parent.heightMargin / 2;
        
        backgroundRect.left = left == 0 ? widthMargin * 2 : widthMargin;
        backgroundRect.top = heightMargin + (row == 0 ? heightMargin : 0);
        backgroundRect.right = width - widthMargin;
        backgroundRect.bottom = height - heightMargin - (row == 5 ? heightMargin : 0);
        background.layout(backgroundRect.left, backgroundRect.top, backgroundRect.right, backgroundRect.bottom);
        
        width = backgroundRect.right - backgroundRect.left;
        height = backgroundRect.bottom - backgroundRect.top;
        
        childRect.left = backgroundRect.left + width / 2 - title.getMeasuredWidth() / 2;
        childRect.top = backgroundRect.top + height / 2 - title.getMeasuredHeight() / 2;
        childRect.right = childRect.left + title.getMeasuredWidth();
        childRect.bottom = childRect.top + title.getMeasuredHeight();
        title.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
    }
}
