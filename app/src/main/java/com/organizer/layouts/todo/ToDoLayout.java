package com.organizer.layouts.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.util.TypedValue;
import android.widget.TextView;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class ToDoLayout extends BaseLayout
{
    protected int roundingRadius = MainActivity.getDisplayMetricsController().dpToPx(25);
    protected int widthMargin = MainActivity.getDisplayMetricsController().dpToPx(15);
    protected int heightMargin = MainActivity.getDisplayMetricsController().dpToPx(10);
    protected int textSizeSp = 18;
    protected int textSize;
    protected int[] rowWidths;
    protected int rows;
    private final Rect childRect = new Rect();
    
    public ToDoLayout(Context context)
    {
        super(context);
        setLayoutParams(new LayoutParams(Integer.MAX_VALUE, LayoutParams.MATCH_PARENT));
        
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textSize = (int) textView.getTextSize();
        
        updateTasks();
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        updateTasks();
        
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
    
    public int getMaxWidth()
    {
        int maxWidth = rowWidths[0];
        
        for (int row = 1; row < rowWidths.length; row++)
        {
            if (rowWidths[row] > maxWidth)
            {
                maxWidth = rowWidths[row];
            }
        }
        return maxWidth + widthMargin;
    }
    
    private void updateTasks()
    {
        removeAllViews();
        rows = Integer.max(1, MainActivity.getDisplayMetricsController().getScreenHeight() / (textSize + roundingRadius + heightMargin * 2));
        rowWidths = new int[rows];
        
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        
        int taskCount = preferences.getInt("taskCount", 1);
        for (int i = 0; i < taskCount; i++)
        {
            TaskLayout task = new TaskLayout(context, this, preferences.getString("taskTitle" + i, "Add Tasks"));
            task.setCompleted(preferences.getBoolean("taskCompleted" + i, false));
            addView(task);
            task.title.measure(0, 0);
            
            int minWidthRow = getMinWidthRow();
            task.left = rowWidths[minWidthRow];
            task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
            task.row = minWidthRow;
            rowWidths[minWidthRow] += task.right - task.left;
        }
    }
    
    public TaskLayout addTask(String title)
    {
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        int taskCount = preferences.getInt("taskCount", 0);
        
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("taskTitle" + taskCount, title);
        editor.putInt("taskCount", taskCount + 1);
        editor.apply();
        
        updateTasks();
        return (TaskLayout) getChildAt(taskCount);
    }
}
