package com.organizer.layouts

import android.graphics.Rect
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.animation.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.R
import com.organizer.layouts.add.*
import com.organizer.layouts.calendar.CalendarLayout
import com.organizer.layouts.todo.ToDoFolderLayout
import com.organizer.layouts.todo.ToDoLayout
import kotlin.math.abs

class MainLayout : BaseLayout() {

    private val displayMetricsController: DisplayMetricsController by inject()

    // TODO layouts in koin? factory/simple
    private val scrollChildCount: Int
    private var startingChild = 1
    private var currentChild: Int
    val toDoScrollLayout = HorizontalScrollLayout()
    val toDoFolderScrollLayout = HorizontalScrollLayout()
    val toDoLayout = ToDoLayout()
    val toDoFolderLayout = ToDoFolderLayout()
    val calendarLayout = CalendarLayout()
    private val addButton = FloatingActionButton(context)
    private val addLayout = AddLayout()
    private val childRect = Rect()
    private var touchListener = setTouchListener()
    private var gestureDetector = setGestureListener()

    init {
        addView(toDoScrollLayout.apply {
            addContentView(this@MainLayout.toDoLayout)
        })
        addView(calendarLayout)

        scrollChildCount = childCount
        for (i in 0 until scrollChildCount) {
            getChildAt(i).setOnTouchListener(touchListener)
            getChildAt(i).visibility = INVISIBLE
        }
        if (startingChild < 0 || startingChild >= scrollChildCount) {
            startingChild = 0
        }
        currentChild = startingChild
        getChildAt(startingChild).visibility = VISIBLE

        addView(toDoFolderScrollLayout.apply {
            visibility = INVISIBLE
            addContentView(toDoFolderLayout)
        })
        addView(addLayout.apply {
            visibility = INVISIBLE
        })
        addView(addButton.apply {
            setImageResource(R.drawable.add_button)
            setOnClickListener { toggleAddLayout(addLayout.visibility == INVISIBLE) }
        })
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, width, height)
        }
        val margin = displayMetricsController.dpToPx(16f)

        childRect.left = width - addButton.measuredWidth - margin
        childRect.top = height - addButton.measuredHeight - margin
        childRect.right = width - margin
        childRect.bottom = height - margin
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
    }

    fun toggleAddLayout(visibility: Boolean) {
        if (visibility) {
            toDoScrollLayout.isScrollable = false
            toDoScrollLayout.isClickable = false
            addLayout.visibility = VISIBLE
            if (getChildAt(currentChild) == toDoScrollLayout) {
                addLayout.show(AddTaskLayout::class.java)
            } else if (getChildAt(currentChild) == calendarLayout) {
                addLayout.show(AddEventLayout::class.java)
            }
            val rotate = RotateAnimation(
                0F,
                135F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 200
            rotate.fillAfter = true
            rotate.interpolator = LinearInterpolator()
            addButton.startAnimation(rotate)
        } else {
            toDoScrollLayout.isScrollable = true
            toDoScrollLayout.isClickable = true
            addLayout.visibility = INVISIBLE
            val rotate = RotateAnimation(
                135F,
                0F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 200
            rotate.fillAfter = true
            rotate.interpolator = LinearInterpolator()
            addButton.startAnimation(rotate)
        }
    }

    private fun setTouchListener() = OnTouchListener { view, motionEvent ->
        super.performClick()
        gestureDetector.onTouchEvent(motionEvent) && view.isClickable
    }

    private fun setGestureListener() = GestureDetector(context, object : SimpleOnGestureListener() {
        val SWIPE_THRESHOLD = 100
        val SWIPE_VELOCITY_THRESHOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD && abs(
                        diffY
                    ) > abs(diffX)
                ) {
                    if (diffY > 0) {
                        onSwipeDown()
                    } else {
                        onSwipeUp()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

        fun onSwipeUp() {
            if (currentChild < scrollChildCount - 1) {
                val animation = AnimationSet(false)
                val child1 = getChildAt(currentChild)
                val child2 = getChildAt(currentChild + 1)
                animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeoutup))
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        child1.visibility = INVISIBLE
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        child2.visibility = VISIBLE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                child1.startAnimation(animation)
                currentChild++
            }
        }

        fun onSwipeDown() {
            if (currentChild > 0) {
                val animation = AnimationSet(false)
                val child1 = getChildAt(currentChild)
                val child2 = getChildAt(currentChild - 1)
                animation.addAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.fadeoutdown
                    )
                )
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        child1.visibility = INVISIBLE
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        child2.visibility = VISIBLE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                child1.startAnimation(animation)
                currentChild--
            }
        }
    })
}
