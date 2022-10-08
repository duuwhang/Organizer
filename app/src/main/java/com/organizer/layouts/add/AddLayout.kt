package com.organizer.layouts.add

import android.graphics.Color
import com.organizer.layouts.BaseLayout
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.organizer.layouts.add.AddTaskLayout
import com.organizer.layouts.add.AddEventLayout

class AddLayout : BaseLayout() {
    init {
        val drawable = GradientDrawable()
        drawable.setTint(Color.BLACK)
        drawable.alpha = 125
        background = drawable
        val addTaskLayout = AddTaskLayout()
        addView(addTaskLayout)
        val addEventLayout = AddEventLayout()
        addView(addEventLayout)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        val ratio = Integer.max(width, height).toFloat() / Integer.min(width, height)
        val widthMargin = (width / (ratio * if (height >= width) 3 else 2)).toInt()
        val heightMargin = (height / (ratio * if (width > height) 3 else 2)).toInt()
        for (i in 0 until childCount) {
            getChildAt(i).layout(
                left + widthMargin / 2,
                top + heightMargin / 2,
                right - widthMargin / 2,
                bottom - heightMargin / 2
            )
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        if (visibility == INVISIBLE) {
            for (i in 0 until childCount) {
                getChildAt(i).visibility = INVISIBLE
            }
        }
    }

    fun show(addLayoutClass: Any) {
        for (i in 0 until childCount) {
            if (getChildAt(i).javaClass == addLayoutClass) {
                getChildAt(i).visibility = VISIBLE
            }
        }
    }
}
