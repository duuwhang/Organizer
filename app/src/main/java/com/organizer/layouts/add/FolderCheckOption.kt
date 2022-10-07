package com.organizer.layouts.add

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.organizer.MainActivity.Companion.inject

class FolderCheckOption : LinearLayout(inject<Context>().value) {
    private val checkBox = CheckBox(context)
    private val folderText = TextView(context)
    private val childRect = Rect()

    init {
        checkBox.setOnCheckedChangeListener { _, _ -> }
        addView(checkBox)

        folderText.text = "Folder"
        folderText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        addView(folderText)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val height = bottom - top
        childRect.setEmpty()
        for (i in 0 until childCount) {
            childRect.left = childRect.right
            childRect.top = height / 2 - getChildAt(i).measuredHeight / 2
            childRect.right = childRect.left + getChildAt(i).measuredWidth
            childRect.bottom = childRect.top + getChildAt(i).measuredHeight
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        }
    }
}
