package com.organizer.layouts;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.organizer.MainActivity;
import com.organizer.R;
import com.organizer.layouts.add.AddEventLayout;
import com.organizer.layouts.add.AddLayout;
import com.organizer.layouts.add.AddTaskLayout;
import com.organizer.layouts.calendar.CalendarLayout;
import com.organizer.layouts.todo.ToDoFolderLayout;
import com.organizer.layouts.todo.ToDoLayout;

public class MainLayout extends BaseLayout
{
    int scrollChildCount;
    private int startingChild = 1;
    private int currentChild;
    HorizontalScrollLayout toDoScrollLayout;
    HorizontalScrollLayout toDoFolderScrollLayout;
    ToDoLayout toDoLayout;
    ToDoFolderLayout toDoFolderLayout;
    CalendarLayout calendarLayout;
    FloatingActionButton addButton;
    AddLayout addLayout;
    private final Rect childRect = new Rect();
    GestureDetector gestureDetector = null;
    View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            return gestureDetector.onTouchEvent(motionEvent) && view.isClickable();
        }
    };
    
    public MainLayout(Context context)
    {
        super(context);
        setGestureListener();
        
        toDoLayout = new ToDoLayout(context);
        
        toDoScrollLayout = new HorizontalScrollLayout(context);
        toDoScrollLayout.addContentView(toDoLayout);
        addView(toDoScrollLayout);
        
        calendarLayout = new CalendarLayout(context);
        addView(calendarLayout);
        
        scrollChildCount = getChildCount();
        for (int i = 0; i < scrollChildCount; i++)
        {
            getChildAt(i).setOnTouchListener(touchListener);
            getChildAt(i).setVisibility(View.INVISIBLE);
        }
        if (startingChild < 0 || startingChild >= scrollChildCount)
        {
            startingChild = 0;
        }
        currentChild = startingChild;
        getChildAt(startingChild).setVisibility(View.VISIBLE);
        
        toDoFolderLayout = new ToDoFolderLayout(context);
        
        toDoFolderScrollLayout = new HorizontalScrollLayout(context);
        toDoFolderScrollLayout.setVisibility(INVISIBLE);
        toDoFolderScrollLayout.addContentView(toDoFolderLayout);
        addView(toDoFolderScrollLayout);
        
        addButton = new FloatingActionButton(context);
        addButton.setImageResource(R.drawable.add_button);
        addButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleAddLayout(addLayout.getVisibility() == INVISIBLE);
            }
        });
        addView(addButton);
        
        addLayout = new AddLayout(context);
        addLayout.setVisibility(INVISIBLE);
        addView(addLayout);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int width = right - left;
        int height = bottom - top;
        
        for (int i = 0; i < getChildCount(); i++)
        {
            getChildAt(i).layout(0, 0, width, height);
        }
        
        int margin = MainActivity.getDisplayMetricsController().dpToPx(16);
        childRect.left = width - addButton.getMeasuredWidth() - margin;
        childRect.top = height - addButton.getMeasuredHeight() - margin;
        childRect.right = width - margin;
        childRect.bottom = height - margin;
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
    }
    
    public void toggleAddLayout(boolean visibility)
    {
        if (visibility)
        {
            toDoScrollLayout.setScrollable(false);
            toDoScrollLayout.setClickable(false);
            
            addLayout.setVisibility(VISIBLE);
            if (getChildAt(currentChild).equals(toDoScrollLayout))
            {
                addLayout.show(AddTaskLayout.class);
            }
            else if (getChildAt(currentChild).equals(calendarLayout))
            {
                addLayout.show(AddEventLayout.class);
            }
            
            RotateAnimation rotate = new RotateAnimation(0, 135, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(200);
            rotate.setFillAfter(true);
            rotate.setInterpolator(new LinearInterpolator());
            addButton.startAnimation(rotate);
        }
        else
        {
            toDoScrollLayout.setScrollable(true);
            toDoScrollLayout.setClickable(true);
            
            addLayout.setVisibility(INVISIBLE);
            
            RotateAnimation rotate = new RotateAnimation(135, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(200);
            rotate.setFillAfter(true);
            rotate.setInterpolator(new LinearInterpolator());
            addButton.startAnimation(rotate);
        }
    }
    
    public HorizontalScrollLayout getToDoScrollLayout()
    {
        return toDoScrollLayout;
    }
    
    public HorizontalScrollLayout getToDoFolderScrollLayout()
    {
        return toDoFolderScrollLayout;
    }
    
    public ToDoLayout getToDoLayout()
    {
        return toDoLayout;
    }
    
    public ToDoFolderLayout getToDoFolderLayout()
    {
        return toDoFolderLayout;
    }
    
    public CalendarLayout getCalendarLayout()
    {
        return calendarLayout;
    }
    
    private void setGestureListener()
    {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            
            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }
            
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
            {
                boolean result = false;
                try
                {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD && Math.abs(diffY) > Math.abs(diffX))
                    {
                        if (diffY > 0)
                        {
                            onSwipeDown();
                        }
                        else
                        {
                            onSwipeUp();
                        }
                        result = true;
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                return result;
            }
            
            void onSwipeUp()
            {
                if (currentChild < scrollChildCount - 1)
                {
                    AnimationSet animation = new AnimationSet(false);
                    final View child1 = getChildAt(currentChild);
                    final View child2 = getChildAt(currentChild + 1);
                    
                    animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeoutup));
                    animation.setAnimationListener(new Animation.AnimationListener()
                    {
                        @Override
                        public void onAnimationStart(Animation animation)
                        {
                            child1.setVisibility(View.INVISIBLE);
                        }
                        
                        @Override
                        public void onAnimationEnd(Animation animation)
                        {
                            child2.setVisibility(View.VISIBLE);
                        }
                        
                        @Override
                        public void onAnimationRepeat(Animation animation)
                        {
                        }
                    });
                    child1.startAnimation(animation);
                    
                    currentChild++;
                }
            }
            
            void onSwipeDown()
            {
                if (currentChild > 0)
                {
                    AnimationSet animation = new AnimationSet(false);
                    final View child1 = getChildAt(currentChild);
                    final View child2 = getChildAt(currentChild - 1);
                    
                    animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeoutdown));
                    animation.setAnimationListener(new Animation.AnimationListener()
                    {
                        @Override
                        public void onAnimationStart(Animation animation)
                        {
                            child1.setVisibility(View.INVISIBLE);
                        }
                        
                        @Override
                        public void onAnimationEnd(Animation animation)
                        {
                            child2.setVisibility(View.VISIBLE);
                        }
                        
                        @Override
                        public void onAnimationRepeat(Animation animation)
                        {
                        }
                    });
                    child1.startAnimation(animation);
                    
                    currentChild--;
                }
            }
        });
    }
}
