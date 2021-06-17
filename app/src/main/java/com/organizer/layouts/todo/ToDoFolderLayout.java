package com.organizer.layouts.todo;

import android.content.Context;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import com.organizer.MainActivity;
import com.organizer.R;
import com.organizer.layouts.HorizontalScrollLayout;

public class ToDoFolderLayout extends ToDoLayout
{
    private FolderLayout folder;
    
    public ToDoFolderLayout(Context context)
    {
        super(context);
    }
    
    @Override
    public void init()
    {
    }
    
    public void show(FolderLayout folder)
    {
        this.folder = folder;
        
        HorizontalScrollLayout scrollLayout = MainActivity.getInstance().getLayout().getToDoFolderScrollLayout();
        scrollLayout.setVisibility(VISIBLE);
        
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadein));
        scrollLayout.startAnimation(animation);
        
        updateTasks();
    }
    
    public void hide()
    {
        HorizontalScrollLayout scrollLayout = MainActivity.getInstance().getLayout().getToDoFolderScrollLayout();
        scrollLayout.setVisibility(INVISIBLE);
        
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
        scrollLayout.startAnimation(animation);
    }
    
    @Override
    protected void updateTasks()
    {
        removeAllViews();
        rows = Integer.max(1, MainActivity.getDisplayMetricsController().getScreenHeight() / (textSize + roundingRadius + heightMargin * 2));
        rowWidths = new int[rows];
        
        
        int taskCount = 1;
        
        TaskLayout task = new FolderLayout(context, this, folder);
        addView(task);
        task.title.measure(0, 0);
        
        int minWidthRow = getMinWidthRow();
        task.left = rowWidths[minWidthRow];
        task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
        task.row = minWidthRow;
        rowWidths[minWidthRow] += task.right - task.left;
        /*
        for (int i = 0; i < taskCount; i++)
        {
            TaskLayout task;
            task.setCompleted(preferences.getBoolean("taskCompleted" + i, false));
            addView(task);
            task.title.measure(0, 0);
            
            int minWidthRow = getMinWidthRow();
            task.left = rowWidths[minWidthRow];
            task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
            task.row = minWidthRow;
            rowWidths[minWidthRow] += task.right - task.left;
        }*/
    }
}
