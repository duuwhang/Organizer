package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.widget.TextView;
import java.util.Random;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class ToDoLayout extends BaseLayout
{
    protected int roundingRadius = MainActivity.getDisplayMetricsController().dpToPx(4000);
    protected int widthMargin = MainActivity.getDisplayMetricsController().dpToPx(2400);
    protected int heightMargin = MainActivity.getDisplayMetricsController().dpToPx(1600);
    protected int textSizeSp = 18;
    protected final int[] rowWidths;
    private final Rect childRect = new Rect();
    
    public ToDoLayout(Context context)
    {
        super(context);
        
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        rowWidths = new int[(int) (Integer.min(MainActivity.getDisplayMetricsController().getScreenWidth(), MainActivity.getDisplayMetricsController().getScreenHeight()) / (textView.getTextSize() + roundingRadius + heightMargin * 2))];
        
        for (int i = 0; i < 20; i++)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Task ").append(i + 1);
            for (int o = 0; o < new Random().nextInt(30); o++)
            {
                stringBuilder.append("A");
            }
            TaskLayout task = new TaskLayout(context, this, stringBuilder.toString());
            addView(task);
            task.title.measure(0, 0);
            
            int minWidthRow = getMinWidthRow();
            task.left = rowWidths[minWidthRow];
            task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
            task.row = minWidthRow;
            rowWidths[minWidthRow] += task.right - task.left;
        }
        
        setLayoutParams(new LayoutParams(getMaxWidth(), LayoutParams.MATCH_PARENT));
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            TaskLayout child = (TaskLayout) getChildAt(i);
            int childHeight = (bottom - top) / rowWidths.length;
            
            childRect.left = child.left;
            childRect.top = childHeight * child.row % (bottom - top);
            childRect.right = child.right;
            childRect.bottom = childRect.top + childHeight;
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }
    
    private int getMinWidthRow()
    {
        int minWidthIndex = 0;
        
        for (int row = 1; row < rowWidths.length; row++)
        {
            if (rowWidths[row] < rowWidths[minWidthIndex])
            {
                minWidthIndex = row;
            }
        }
        return minWidthIndex;
    }
    
    private int getMaxWidth()
    {
        int maxWidth = rowWidths[0];
        
        for (int row = 1; row < rowWidths.length; row++)
        {
            if (rowWidths[row] > maxWidth)
            {
                maxWidth = rowWidths[row];
            }
        }
        return maxWidth;
    }
}
