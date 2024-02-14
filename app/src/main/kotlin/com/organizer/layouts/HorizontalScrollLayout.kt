package com.organizer.layouts

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.organizer.*
import com.organizer.MainActivity.Companion.inject
import com.organizer.MainActivity.Companion.injectNow
import com.organizer.layouts.todo.ToDoLayout

class HorizontalScrollLayout : HorizontalScrollView(injectNow()) {

    private val displayMetricsController: DisplayMetricsController by inject()

    var isScrollable = true
    private val linearLayout = LinearLayout(context)
    private var toDoLayout: ToDoLayout? = null

    init {
        isHorizontalScrollBarEnabled = false
        overScrollMode = OVER_SCROLL_NEVER
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        addView(linearLayout.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
    }

    fun addContentView(contentView: ToDoLayout) {
        toDoLayout = contentView
        linearLayout.addView(contentView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (isScrollable) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isScrollable) {
            super.performClick()
            super.onTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onScrollChanged(left: Int, top: Int, oldLeft: Int, oldTop: Int) {
        super.onScrollChanged(left, top, oldLeft, oldTop)
        toDoLayout?.let {
            val width = it.maxWidth - displayMetricsController.screenWidth
            if (left > width) {
                scrollTo(width, 0)
            }
        }
    }
}
