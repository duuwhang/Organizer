package com.organizer.layouts.todo

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.PaintDrawable
import android.util.TypedValue
import android.widget.TextView
import com.organizer.MainActivity
import com.organizer.MainActivity.Companion.inject
import com.organizer.R.color
import com.organizer.layouts.BaseLayout
import com.organizer.layouts.MainLayout

@SuppressLint("ViewConstructor")
open class TaskLayout(parent: ToDoLayout, id: Int, displayText: String) : BaseLayout() {

    private val mainActivity: MainActivity by inject()
    private val preferences: SharedPreferences by inject()
    private val mainLayout: MainLayout by inject()

    val parentLayout: ToDoLayout = parent
    val identifier: Int = id
    var done = false
    var leftX = 0
    var rightX = 0
    var row = 0
    val background = TextView(context)
    val title = TextView(context)
    private val backgroundRect = Rect()
    private val childRect = Rect()

    init {
        val shape = PaintDrawable(Color.WHITE)
        parentLayout.roundingRadius.let { shape.setCornerRadius(it.toFloat()) }
        shape.setTint(Color.parseColor(resources.getString(color.colorPrimaryDark)))

        addView(background.apply {
            text = ""
            background = shape
        })

        addView(title.apply {
            setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                parentLayout.textSizeSp.toFloat()
            )
            textAlignment = TEXT_ALIGNMENT_CENTER
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        })
        setOnClickListener {
            setCompleted(!done)
            updateFolderCompletions()
        }
        this.title.text = displayText
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var width = right - left
        var height = bottom - top
        val widthMargin = parentLayout.widthMargin
        val heightMargin = parentLayout.heightMargin / 2
        backgroundRect.left = widthMargin
        backgroundRect.top = heightMargin + if (row == 0) heightMargin else 0
        backgroundRect.right = width
        backgroundRect.bottom =
            height - heightMargin - if (row == parentLayout.rowWidths.size - 1) heightMargin else 0
        background.layout(
            backgroundRect.left,
            backgroundRect.top,
            backgroundRect.right,
            backgroundRect.bottom
        )
        width = backgroundRect.right - backgroundRect.left
        height = backgroundRect.bottom - backgroundRect.top
        childRect.left = backgroundRect.left + width / 2 - title.measuredWidth / 2
        childRect.top = backgroundRect.top + height / 2 - title.measuredHeight / 2
        childRect.right = childRect.left + title.measuredWidth
        childRect.bottom = childRect.top + title.measuredHeight
        title.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
    }

    private fun updateFolderCompletions() {
        val editor = preferences.edit()
        for (i in 0 until preferences.getInt("folderCount", 0)) {
            val folder = preferences.getString("folder$i", "Add Tasks;;0")!!
                .split(";;").toTypedArray()
            if (folder.size > 2) {
                done = true
                for (j in 2 until folder.size) {
                    if ("0" == preferences.getString(folder[j], "")!!.split(";;")
                            .toTypedArray()[1]
                    ) {
                        done = false
                        break
                    }
                }
                folder[1] = if (done) "1" else "0"
                val stringBuilder = StringBuilder()
                for (j in folder.indices) {
                    stringBuilder.append(folder[j]).append(if (j == folder.size - 1) "" else ";;")
                }
                editor.putString("folder$i", stringBuilder.toString())
            }
        }
        editor.apply()
        mainLayout.toDoLayout.updateTasks()
        mainLayout.toDoFolderLayout.updateTasks()
    }

    fun setCompleted(completed: Boolean) {
        this.done = completed
        background.alpha = (if (completed) 0.3f else 1f)
        val editor = preferences.edit()
        val task = preferences.getString("task$identifier", "Add Tasks;;0")!!
            .split(";;").toTypedArray()
        task[1] = if (completed) "1" else "0"
        val stringBuilder = StringBuilder()
        for (i in task.indices) {
            stringBuilder.append(task[i]).append(if (i == task.size - 1) "" else ";;")
        }
        editor.putString("task$identifier", stringBuilder.toString())
        editor.apply()
    }
}
