package com.organizer.layouts.calendar;

import android.content.Context;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DayLayout extends ConstraintLayout
{
    private TextView textView;
    
    public DayLayout(Context context)
    {
        super(context);
        setLayoutParams(new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT));
        
        textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        addView(textView);
    }
    
    public TextView getTextView()
    {
        return textView;
    }
}
