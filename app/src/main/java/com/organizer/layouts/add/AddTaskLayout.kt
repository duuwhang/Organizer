package com.organizer.layouts.add

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.R
import com.organizer.layouts.BaseLayout
import com.organizer.layouts.MainLayout

class AddTaskLayout : BaseLayout() {
    private val displayMetricsController: DisplayMetricsController by inject()
    private val mainLayout: MainLayout by inject()
    private val addButton = Button(context)
    private val displayText = TextView(context)
    private val titleEditText = EditText(context)
    private val folderCheckOption = FolderCheckOption()
    private val defaultHintColor = titleEditText.hintTextColors.defaultColor;
    private val childRect = Rect()
    private val optionsRect = Rect()

    init {
        val drawable = GradientDrawable()
        drawable.setTint(Color.DKGRAY)
        background = drawable
        addButton.text = "Add"
        addButton.setOnClickListener {
            if (titleEditText.text.toString() != "") {
                var toDoLayout = mainLayout.toDoLayout
                if (mainLayout.toDoFolderScrollLayout.visibility == VISIBLE) {
                    toDoLayout = mainLayout.toDoFolderLayout
                }
                val task = toDoLayout.addTask(
                    titleEditText.text.toString(),
                    (folderCheckOption.getChildAt(0) as CheckBox).isChecked
                )
                mainLayout.toggleAddLayout(false)
                titleEditText.setText("")
                titleEditText.setHintTextColor(defaultHintColor)
                titleEditText.clearFocus()
                mainLayout.toDoScrollLayout.scrollTo(task!!.leftX, 0)
            } else {
                titleEditText.setHintTextColor(resources.getColor(R.color.colorAccent))
            }
        }
        addView(addButton)
        displayText.text = "Add a new Task"
        displayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        addView(displayText)
        titleEditText.hint = "Title"
        titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                titleEditText.setHintTextColor(defaultHintColor)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        addView(titleEditText)
        addView(folderCheckOption)
        addView(Button(context))
        addView(Button(context))
        addView(Button(context))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        var margin = displayMetricsController.dpToPx(6f)
        childRect.left = width - addButton.measuredWidth - margin
        childRect.top = height - addButton.measuredHeight - margin
        childRect.right = width - margin
        childRect.bottom = height - margin
        addButton.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        margin = displayMetricsController.dpToPx(8f)
        childRect.left = width / 2 - displayText.measuredWidth / 2
        childRect.top = margin
        childRect.right = childRect.left + displayText.measuredWidth
        childRect.bottom = margin + displayText.measuredHeight
        displayText.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        optionsRect.left = margin
        optionsRect.top = childRect.bottom + margin
        optionsRect.right = width - margin
        optionsRect.bottom = addButton.top - margin
        var currentTop = optionsRect.top
        for (i in 2 until childCount) {
            childRect.left = optionsRect.left
            childRect.top = currentTop
            childRect.right = optionsRect.right
            currentTop += getChildAt(i).measuredHeight
            childRect.bottom = Integer.min(optionsRect.bottom, currentTop)
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        }
    }
}
