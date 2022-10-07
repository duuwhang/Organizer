package com.organizer.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject

@SuppressLint("ViewConstructor")
open class BaseLayout : ViewGroup(inject<Context>().value) {
    private val displayMetricsController: DisplayMetricsController by inject()

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = Math.max(displayMetricsController.screenWidth, suggestedMinimumWidth)
        val maxHeight = Math.max(maxWidth, suggestedMinimumHeight)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, right - left, bottom - top)
        }
    }
}
