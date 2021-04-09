package com.organizer.layouts;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.organizer.R;
import com.organizer.layouts.calendar.CalendarLayout;
import com.organizer.layouts.todo.ToDoLayout;

public class MainLayout extends BaseLayout
{
    boolean init = true;
    private int startingChild = 1;
    private int currentChild = startingChild;
    ToDoLayout toDoLayout;
    CalendarLayout calendarLayout;
    GestureDetector gestureDetector = null;
    View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    };
    
    public MainLayout(Context context)
    {
        super(context);
        setGestureListener();
        
        toDoLayout = new ToDoLayout(context);
        
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT));
        linearLayout.addView(toDoLayout);
        
        HorizontalScrollView scrollLayout = new HorizontalScrollView(context);
        scrollLayout.setHorizontalScrollBarEnabled(false);
        scrollLayout.setLayoutParams(new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT));
        scrollLayout.addView(linearLayout);
        addView(scrollLayout);
        
        scrollLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                scrollLayout.scrollTo(0, 0);
            }
        });
        
        calendarLayout = new CalendarLayout(context);
        addView(calendarLayout);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        if (init)
        {
            for (int i = 0; i < getChildCount(); i++)
            {
                getChildAt(i).layout(0, 0, right - left, bottom - top);
                getChildAt(i).setOnTouchListener(touchListener);
                getChildAt(i).setVisibility(View.INVISIBLE);
            }
            if (startingChild < 0 || startingChild >= getChildCount())
            {
                startingChild = 0;
            }
            getChildAt(startingChild).setVisibility(View.VISIBLE);
            init = false;
        }
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
                if (currentChild < getChildCount() - 1)
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
