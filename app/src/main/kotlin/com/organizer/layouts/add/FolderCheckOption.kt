package com.organizer.layouts.add

import android.graphics.Rect
import android.util.TypedValue
import android.widget.*
import com.organizer.MainActivity.Companion.injectNow

class FolderCheckOption : LinearLayout(injectNow()) {

    internal val checkBox = CheckBox(context)
    private val folderText = TextView(context)
    private val childRect = Rect()

    init {
        addView(checkBox.apply {
            setOnCheckedChangeListener { _, _ -> }
        })
        addView(folderText.apply {
            text = "Folder"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        })
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
