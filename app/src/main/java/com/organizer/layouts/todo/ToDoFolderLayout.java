package com.organizer.layouts.todo;

import android.content.Context;
import android.content.SharedPreferences;
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
        if (folder != null)
        {
            removeAllViews();
            rows = Integer.max(1, MainActivity.getDisplayMetricsController().getScreenHeight() / (textSize + roundingRadius + heightMargin * 2));
            rowWidths = new int[rows];
            
            TaskLayout task = new FolderLayout(folder);
            addView(task);
            task.title.measure(0, 0);
            
            int minWidthRow = getMinWidthRow();
            task.left = rowWidths[minWidthRow];
            task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
            task.row = minWidthRow;
            rowWidths[minWidthRow] += task.right - task.left;
            
            SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
            String[] root = preferences.getString("folder" + folder.id, "Add Tasks;;0").split(";;");
            
            for (int i = 2; i < root.length; i++)
            {
                String[] s = preferences.getString(root[i], "Add Tasks;;0").split(";;");
                if (root[i].startsWith("folder"))
                {
                    task = new FolderLayout(context, this, Integer.parseInt(root[i].substring(6)), s[0]);
                }
                else
                {
                    task = new TaskLayout(context, this, Integer.parseInt(root[i].substring(4)), s[0]);
                }
                task.setCompleted(s[1].equals("1"));
                addView(task);
                task.title.measure(0, 0);
                
                minWidthRow = getMinWidthRow();
                task.left = rowWidths[minWidthRow];
                task.right = task.left + task.title.getMeasuredWidth() + roundingRadius * 2 + widthMargin * 2;
                task.row = minWidthRow;
                rowWidths[minWidthRow] += task.right - task.left;
            }
        }
    }
    
    @Override
    public TaskLayout addTask(String title, boolean isFolder)
    {
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (isFolder)
        {
            int folderCount = preferences.getInt("folderCount", 0);
            editor.putString("folder" + folderCount, title + ";;0");
            editor.putInt("folderCount", folderCount + 1);
            String root = preferences.getString("folder" + folder.id, "Add Tasks;;0");
            editor.putString("folder" + folder.id, root + (root.equals("") ? "" : ";;") + "folder" + folderCount);
        }
        else
        {
            int taskCount = preferences.getInt("taskCount", 0);
            editor.putString("task" + taskCount, title + ";;0");
            editor.putInt("taskCount", taskCount + 1);
            String root = preferences.getString("folder" + folder.id, "Add Tasks;;0");
            editor.putString("folder" + folder.id, root + (root.equals("") ? "" : ";;") + "task" + taskCount);
        }
        editor.apply();
        
        updateTasks();
        return (TaskLayout) getChildAt(getChildCount() - 1);
    }
}
