package com.organizer.layouts.todo;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import com.organizer.MainActivity;

public class FolderLayout extends TaskLayout
{
    public FolderLayout(Context context, ToDoLayout parent, int id, String title)
    {
        super(context, parent, id, title);
        ((PaintDrawable) background.getBackground()).setCornerRadius(MainActivity.getDisplayMetricsController().dpToPx(4));
        setOnClickListener(view -> MainActivity.getInstance().getLayout().getToDoFolderLayout().show(this));
    }
    
    public FolderLayout(FolderLayout folder)
    {
        super(folder.context, folder.parent, folder.id, folder.title.getText().toString());
        ((PaintDrawable) background.getBackground()).setCornerRadius(MainActivity.getDisplayMetricsController().dpToPx(4));
        setOnClickListener(view -> MainActivity.getInstance().getLayout().getToDoFolderLayout().hide());
    }
}
