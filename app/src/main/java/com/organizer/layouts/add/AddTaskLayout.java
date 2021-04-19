package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class AddTaskLayout extends BaseLayout
{
    private Button addButton;
    
    public AddTaskLayout(Context context)
    {
        super(context);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setTint(Color.DKGRAY);
        setBackground(drawable);
        
        addButton = new Button(context);
        addButton.setText("Add");
        addButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.getInstance().getLayout().toggleAddLayout(false);
            }
        });
        addView(addButton);
    }
}
