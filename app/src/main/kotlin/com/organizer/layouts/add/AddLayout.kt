package com.organizer.layouts.add

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.organizer.layouts.Layout
import kotlin.reflect.KClass

class AddLayout : Layout() {

    init {
        val drawable = GradientDrawable()
        drawable.setTint(Color.BLACK)
        drawable.alpha = 125
        background = drawable

        addView(AddTaskLayout())
        addView(AddEventLayout())
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

    fun show(addLayoutClass: KClass<*>) {
        for (i in 0 until childCount) {
            if (getChildAt(i)::class == addLayoutClass) {
                getChildAt(i).visibility = VISIBLE
            }
        }
    }
}
