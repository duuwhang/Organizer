package com.organizer.layouts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.organizer.MainActivity;
import com.organizer.R;
import com.organizer.layouts.BaseLayout;
import com.organizer.layouts.todo.TaskLayout;

public class AddTaskLayout extends BaseLayout
{
    private final int defaultHintColor;
    private Button addButton;
    private TextView displayText;
    private EditText titleEditText;
    private final Rect childRect = new Rect();
    
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
                if (!titleEditText.getText().toString().equals(""))
                {
                    TaskLayout task = MainActivity.getInstance().getLayout().getToDoLayout().addTask(titleEditText.getText().toString());
                    MainActivity.getInstance().getLayout().toggleAddLayout(false);
                    MainActivity.getInstance().getLayout().getScrollLayout().scrollTo(task.left, 0);
                    titleEditText.setText("");
                    titleEditText.setHintTextColor(defaultHintColor);
                    titleEditText.clearFocus();
                }
                else
                {
                    titleEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        addView(addButton);
        
        displayText = new TextView(context);
        displayText.setText("Add a new Task");
        displayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        displayText.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        displayText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(displayText);
        
        titleEditText = new EditText(context);
        defaultHintColor = titleEditText.getHintTextColors().getDefaultColor();
        titleEditText.setHint("Title");
        titleEditText.setCursorVisible(false);
        titleEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                titleEditText.setHintTextColor(defaultHintColor);
            }
            
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        addView(titleEditText);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        
        int margin = MainActivity.getDisplayMetricsController().dpToPx(6);
        childRect.left = width - addButton.getMeasuredWidth() - margin;
        childRect.top = height - addButton.getMeasuredHeight() - margin;
        childRect.right = width - margin;
        childRect.bottom = height - margin;
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        
        height = childRect.top;
        int currentTop = margin;
        for (int i = 1; i < getChildCount(); i++)
        {
            childRect.left = margin;
            childRect.top = currentTop;
            childRect.right = width - margin;
            currentTop += getChildAt(i).getMeasuredHeight();
            childRect.bottom = Integer.min(height, currentTop);
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }
}
