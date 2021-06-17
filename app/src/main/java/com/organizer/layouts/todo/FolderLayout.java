package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import com.organizer.MainActivity;
import com.organizer.Task;

public class FolderLayout extends TaskLayout
{
    private Task[] tasks;
    
    public FolderLayout(Context context, ToDoLayout parent, String title)
    {
        super(context, parent, title);
        ((PaintDrawable) background.getBackground()).setCornerRadius(MainActivity.getDisplayMetricsController().dpToPx(4));
        setOnClickListener(view -> open());
    }
    
    public FolderLayout(Context context, ToDoLayout parent, FolderLayout folder)
    {
        super(context, parent, folder.title.getText().toString());
        ((PaintDrawable) background.getBackground()).setCornerRadius(MainActivity.getDisplayMetricsController().dpToPx(4));
        setOnClickListener(view -> close());
    }
    
    private void open()
    {
        MainActivity.getInstance().getLayout().getToDoFolderLayout().show(this);
    }
    
    private void close()
    {
        MainActivity.getInstance().getLayout().getToDoFolderLayout().hide();
    }
}
