package com.organizer.layouts.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import android.util.TypedValue;
import android.widget.TextView;
import com.organizer.MainActivity;
import com.organizer.layouts.BaseLayout;
import static com.organizer.R.color.colorPrimaryDark;

public class TaskLayout extends BaseLayout
{
    boolean completed;
    public int id;
    public int left;
    public int right;
    public int row;
    TextView background;
    TextView title;
    ToDoLayout parent;
    private final Rect backgroundRect = new Rect();
    private final Rect childRect = new Rect();
    
    public TaskLayout(Context context, ToDoLayout parent, int id, String title)
    {
        super(context);
        this.parent = parent;
        this.id = id;
        init();
        this.title.setText(title);
    }
    
    @SuppressLint("ResourceType")
    private void init()
    {
        PaintDrawable shape = new PaintDrawable(Color.WHITE);
        shape.setCornerRadius(parent.roundingRadius);
        shape.setTint(Color.parseColor(getResources().getString(colorPrimaryDark)));
        
        background = new TextView(context);
        background.setText("");
        background.setBackground(shape);
        addView(background);
        
        title = new TextView(context);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, parent.textSizeSp);
        title.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(title);
        
        setOnClickListener(view ->
        {
            setCompleted(!completed);
            updateFolderCompletions();
        });
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        
        int widthMargin = parent.widthMargin;
        int heightMargin = parent.heightMargin / 2;
        
        backgroundRect.left = widthMargin;
        backgroundRect.top = heightMargin + (row == 0 ? heightMargin : 0);
        backgroundRect.right = width;
        backgroundRect.bottom = height - heightMargin - (row == parent.rowWidths.length - 1 ? heightMargin : 0);
        background.layout(backgroundRect.left, backgroundRect.top, backgroundRect.right, backgroundRect.bottom);
        
        width = backgroundRect.right - backgroundRect.left;
        height = backgroundRect.bottom - backgroundRect.top;
        
        childRect.left = backgroundRect.left + width / 2 - title.getMeasuredWidth() / 2;
        childRect.top = backgroundRect.top + height / 2 - title.getMeasuredHeight() / 2;
        childRect.right = childRect.left + title.getMeasuredWidth();
        childRect.bottom = childRect.top + title.getMeasuredHeight();
        title.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
    }
    
    private void updateFolderCompletions()
    {
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < preferences.getInt("folderCount", 0); i++)
        {
            String[] folder = preferences.getString("folder" + i, "Add Tasks;;0").split(";;");
            if (folder.length > 2)
            {
                completed = true;
                for (int j = 2; j < folder.length; j++)
                {
                    if ("0".equals(preferences.getString(folder[j], "").split(";;")[1]))
                    {
                        completed = false;
                        break;
                    }
                }
                
                folder[1] = completed ? "1" : "0";
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < folder.length; j++)
                {
                    stringBuilder.append(folder[j]).append(j == folder.length - 1 ? "" : ";;");
                }
                
                editor.putString("folder" + i, stringBuilder.toString());
            }
        }
        editor.apply();
        
        MainActivity.getInstance().getLayout().getToDoLayout().updateTasks();
        MainActivity.getInstance().getLayout().getToDoFolderLayout().updateTasks();
    }
    
    public void setCompleted(boolean completed)
    {
        this.completed = completed;
        background.setAlpha(completed ? 0.3f : 1);
        
        SharedPreferences preferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String[] task = preferences.getString("task" + id, "Add Tasks;;0").split(";;");
        task[1] = completed ? "1" : "0";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < task.length; i++)
        {
            stringBuilder.append(task[i]).append(i == task.length - 1 ? "" : ";;");
        }
        editor.putString("task" + id, stringBuilder.toString());
        editor.apply();
    }
}
