package com.organizer.layouts.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;
import static com.organizer.R.color.colorBackground;

public class ToDoLayout extends BaseLayout
{
    protected int roundingRadius = MainActivity.getDisplayMetricsController().dpToPx(25);
    protected int widthMargin = MainActivity.getDisplayMetricsController().dpToPx(15);
    protected int heightMargin = MainActivity.getDisplayMetricsController().dpToPx(10);
    protected int textSizeSp = 18;
    protected int textSize;
    protected int[] rowWidths = new int[1];
    protected int rows;
    private final Rect childRect = new Rect();
    
    @SuppressLint("ResourceType")
    public ToDoLayout(Context context)
    {
        super(context);
        setBackgroundColor(Color.parseColor(getResources().getString(colorBackground)));
        setLayoutParams(new LayoutParams(Integer.MAX_VALUE, LayoutParams.MATCH_PARENT));
        
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textSize = (int) textView.getTextSize();
    }
    
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        
        if (visibility == VISIBLE)
        {
            int scrollOffset = rowWidths[getMinWidthRow()];
            for (int i = 0; i < getChildCount(); i++)
            {
                TaskLayout task = (TaskLayout) getChildAt(i);
                if (!task.completed && task.left < scrollOffset)
                {
                    scrollOffset = task.left;
                }
            }
            MainActivity.getInstance().getLayout().getToDoScrollLayout().scrollTo(scrollOffset, 0);
        }
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
    
    protected int getMinWidthRow()
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
    
    protected void updateTasks()
    {
        removeAllViews();
        rows = Integer.max(1, MainActivity.getDisplayMetricsController().getScreenHeight() / (textSize + roundingRadius + heightMargin * 2));
        rowWidths = new int[rows];
        
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        String[] root = preferences.getString("root", "").split(";;");
        for (int i = 0; i < root.length || i == 0; i++)
        {
            String[] s = preferences.getString(root[i], "Add Tasks;;0").split(";;");
            TaskLayout task;
            if (root[i].startsWith("folder"))
            {
                task = new FolderLayout(context, this, Integer.parseInt(root[i].substring(6)), s[0]);
            }
            else
            {
                task = new TaskLayout(context, this, s[0]);
            }
            task.setCompleted(s[1].equals("1"));
            addView(task);
            task.title.measure(0, 0);
            
            int minWidthRow = getMinWidthRow();
            task.left = rowWidths[minWidthRow];
            task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
            task.row = minWidthRow;
            rowWidths[minWidthRow] += task.right - task.left;
        }
    }
    
    public TaskLayout addTask(String title, boolean isFolder)
    {
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (isFolder)
        {
            int folderCount = preferences.getInt("folderCount", 0);
            editor.putString("folder" + folderCount, title + ";;0");
            editor.putInt("folderCount", folderCount + 1);
            String root = preferences.getString("root", "");
            editor.putString("root", root + (root.equals("") ? "" : ";;") + "folder" + folderCount);
        }
        else
        {
            int taskCount = preferences.getInt("taskCount", 0);
            editor.putString("task" + taskCount, title + ";;0");
            editor.putInt("taskCount", taskCount + 1);
            String root = preferences.getString("root", "");
            editor.putString("root", root + (root.equals("") ? "" : ";;") + "task" + taskCount);
        }
        editor.apply();
        
        updateTasks();
        return (TaskLayout) getChildAt(getChildCount() - 1);
    }
}
