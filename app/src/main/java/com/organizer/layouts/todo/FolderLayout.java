package com.organizer.layouts.todo;

import android.content.Context;

public class FolderLayout extends TaskLayout
{
    public FolderLayout(Context context, ToDoLayout parent, String title)
    {
        super(context, parent, title);
        setOnClickListener(view -> open());
    }
    
    private void open()
    {
    
    }
}
