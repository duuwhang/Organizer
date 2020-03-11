package com.example.organizer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainLayout extends ViewGroup {
    Context context;
    GestureDetector gestureDetector = null;

    private enum Direction {
        None, Up, Left, Down, Right
    }

    private Direction direction = Direction.None;
    private MotionEvent down;
    private int startingChild = 0;
    private int currentChild = startingChild;
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    };
    boolean init = true;

    public MainLayout(Context context) {
        super(context);
        this.context = context;
        setGestureListener();
    }

    public MainLayout(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
        this.context = context;
        setGestureListener();
    }

    public MainLayout(Context context, AttributeSet attributes, int defStyle) {
        super(context, attributes, defStyle);
        this.context = context;
        setGestureListener();
    }

    /* TODO try this out
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
    */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (init) {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).layout(l, t, r, b);
                getChildAt(i).setOnTouchListener(touchListener);
                getChildAt(i).setVisibility(View.GONE); // TODO: gone
            }
            if (startingChild < 0 || startingChild >= getChildCount()) {
                startingChild = 0;
            }
            getChildAt(startingChild).setVisibility(View.VISIBLE);
            getChildAt(0).setVisibility(View.VISIBLE);
            init = false;
        }
        //Toast.makeText(context, "onLayout", Toast.LENGTH_SHORT).show();
    }

    private void setGestureListener() {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD && Math.abs(diffX) < Math.abs(diffY)) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }

            void onSwipeUp() {
                if (currentChild < getChildCount() - 1) {
                    AnimationSet animationSet;

                    animationSet = new AnimationSet(false);
                    animationSet.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeoutup));
                    getChildAt(currentChild).startAnimation(animationSet);

                    currentChild++;
                    getChildAt(currentChild).setVisibility(View.VISIBLE);
                    animationSet = new AnimationSet(false);
                    animationSet.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeinup));
                    getChildAt(currentChild).startAnimation(animationSet);
                    getChildAt(currentChild - 1).setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "too little elements", Toast.LENGTH_SHORT).show();
                }
            }

            void onSwipeDown() {
                if (currentChild > 0) {
                    AnimationSet animationSet;

                    animationSet = new AnimationSet(false);
                    animationSet.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeoutdown));
                    getChildAt(currentChild).startAnimation(animationSet);
                    getChildAt(currentChild).setVisibility(View.GONE);

                    currentChild--;
                    getChildAt(currentChild).setVisibility(View.VISIBLE);
                    animationSet = new AnimationSet(false);
                    animationSet.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeindown));
                    getChildAt(currentChild).startAnimation(animationSet);
                } else {
                    Toast.makeText(context, "arrived at top", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
