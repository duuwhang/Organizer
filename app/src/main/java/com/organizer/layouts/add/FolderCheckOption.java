package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FolderCheckOption extends LinearLayout
{
    protected final Context context;
    private CheckBox checkBox;
    private TextView folderText;
    private final Rect childRect = new Rect();
    
    public FolderCheckOption(Context context)
    {
        super(context);
        this.context = context;
        
        checkBox = new CheckBox(context);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
            }
        });
        addView(checkBox);
        
        folderText = new TextView(context);
        folderText.setText("Folder");
        folderText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        addView(folderText);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        int height = bottom - top;
        childRect.setEmpty();
        
        for (int i = 0; i < getChildCount(); i++)
        {
            childRect.left = childRect.right;
            childRect.top = height / 2 - getChildAt(i).getMeasuredHeight() / 2;
            childRect.right = childRect.left + getChildAt(i).getMeasuredWidth();
            childRect.bottom = childRect.top + getChildAt(i).getMeasuredHeight();
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }
}
