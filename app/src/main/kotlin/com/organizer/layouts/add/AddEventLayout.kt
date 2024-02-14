package com.organizer.layouts.add

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.layouts.Layout
import com.organizer.layouts.MainLayout

class AddEventLayout : Layout() {
    private val displayMetricsController: DisplayMetricsController by inject()
    private val mainLayout: MainLayout by inject()
    private val addButton: Button
    private val childRect = Rect()

    init {
        val drawable = GradientDrawable()
        drawable.setTint(Color.DKGRAY)
        background = drawable
        addButton = Button(context)
        addButton.text = "Add"
        addButton.setOnClickListener { mainLayout.toggleAddLayout(false) }
        addView(addButton)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        for (i in 1 until childCount) {
            getChildAt(i).layout(0, 0, width, height)
        }
        val margin = displayMetricsController.dpToPx(6f)
        childRect.left = width - addButton.measuredWidth - margin
        childRect.top = height - addButton.measuredHeight - margin
        childRect.right = width - margin
        childRect.bottom = height - margin
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
    }
}
