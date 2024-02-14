package com.organizer.layouts.todo

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.PaintDrawable
import android.util.TypedValue
import android.widget.TextView
import com.organizer.MainActivity.Companion.inject
import com.organizer.PreferenceService
import com.organizer.R.color
import com.organizer.data.Task
import com.organizer.layouts.Layout
import com.organizer.layouts.MainLayout

open class TaskLayout(val parentLayout: ToDoLayout, val task: Task) : Layout() {

    private val preferences: PreferenceService by inject()
    private val mainLayout: MainLayout by inject()

    private val observers: MutableSet<(Boolean) -> Unit> = mutableSetOf()
    var leftX = 0
    var rightX = 0
    var row = 0
    val background = TextView(context)
    val title = TextView(context)
    private val backgroundRect = Rect()
    private val childRect = Rect()

    init {
        run {
            val shape = PaintDrawable(Color.WHITE).apply {
                setCornerRadius(parentLayout.roundingRadius.toFloat())
                setTint(Color.parseColor(resources.getString(color.colorPrimaryDark)))
            }

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
                setCompleted(!task.completed)
                updateFolderCompletions()
            }
        }
        title.text = task.name
        background.alpha = if (task.completed) 0.3f else 1f
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

    // after changing completed, check all folders if their children are all completed
    private fun updateFolderCompletions() {
//        val editor = preferences.edit()
//        for (i in 0 until preferences.getInt("folderCount", 0)) {
//            val folder =
//                preferences.getString("folder$i", "Add Tasks;;0")!!.split(";;").toTypedArray()
//            if (folder.size > 2) {
//                done = true
//                for (j in 2 until folder.size) {
//                    if ("0" == preferences.getString(folder[j], "")!!.split(";;")
//                            .toTypedArray()[1]
//                    ) {
//                        done = false
//                        break
//                    }
//                }
//                folder[1] = if (done) "1" else "0"
//                val stringBuilder = StringBuilder()
//                for (j in folder.indices) {
//                    stringBuilder.append(folder[j]).append(if (j == folder.size - 1) "" else ";;")
//                }
//                editor.putString("folder$i", stringBuilder.toString())
//            }
//        }
//        editor.apply()
//        mainLayout.toDoLayout.updateTasks()
//        mainLayout.toDoFolderLayout.updateTasks()
    }
//
//    private fun updateFolderCompletions2() {
//
//        preferences.restoreFolders()
//
//
//        val editor = preferences.edit()
//        // for each folder
//        for (i in 0 until preferences.getInt("folderCount", 0)) {
//            val folder =
//                preferences.getString("folder$i", "Add Tasks;;0")!!.split(";;").toTypedArray()
//            if (folder.size > 2) { // has tasks
//                done = true
//                // for each task in folder -> if any is not completed then done = false
//                for (j in 2 until folder.size) {
//                    // if is not completed
//                    if ("0" == preferences.getString(folder[j], "")!!.split(";;")
//                            .toTypedArray()[1]
//                    ) {
//                        done = false
//                        break
//                    }
//                }
//                folder[1] = if (done) "1" else "0"
//                val stringBuilder = StringBuilder()
//                for (j in folder.indices) {
//                    stringBuilder.append(folder[j]).append(if (j == folder.size - 1) "" else ";;")
//                }
//                // write updated folder back
//                editor.putString("folder$i", stringBuilder.toString())
//            }
//        }
//        editor.apply()
//
//
//        mainLayout.toDoLayout.updateTasks()
//        mainLayout.toDoFolderLayout.updateTasks()
//    }

    fun observeCompleted(observer: (Boolean) -> Unit) {
        observers.add(observer)
    }

    fun setCompleted(completed: Boolean) {
        task.completed = completed
        observers.forEach { it.invoke(completed) }
        background.alpha = if (completed) 0.3f else 1f
        preferences.storeTask(task)
    }
}
