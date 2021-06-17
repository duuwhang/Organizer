package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import com.organizer.MainActivity;

public class FolderLayout extends TaskLayout
{
    public int id;
    
    public FolderLayout(Context context, ToDoLayout parent, int id, String title)
    {
        super(context, parent, title);
        this.id = id;
        ((PaintDrawable) background.getBackground()).setCornerRadius(MainActivity.getDisplayMetricsController().dpToPx(4));
        setOnClickListener(view -> open());
    }
    
    public FolderLayout(FolderLayout folder)
    {
        super(folder.context, folder.parent, folder.title.getText().toString());
        id = folder.id;
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
