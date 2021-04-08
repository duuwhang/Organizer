package com.organizer.layouts.calendar;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;

public class DayLayout extends ConstraintLayout
{
    private TextView textView;
    
    public DayLayout(Context context)
    {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        
        textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        addView(textView);
    }
    
    public TextView getTextView()
    {
        return textView;
    }
}
