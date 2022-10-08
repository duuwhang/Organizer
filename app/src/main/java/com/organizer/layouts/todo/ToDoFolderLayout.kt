package com.organizer.layouts.todo

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.R
import com.organizer.layouts.MainLayout

@SuppressLint("ViewConstructor")
class ToDoFolderLayout : ToDoLayout() {

    private val preferences: SharedPreferences by inject()
    private val mainLayout: MainLayout by inject()
    private val displayMetricsController: DisplayMetricsController by inject()

    private var folder: FolderLayout? = null

    fun show(folder: FolderLayout?) {
        this.folder = folder
        val scrollLayout = mainLayout.toDoFolderScrollLayout
        scrollLayout.visibility = VISIBLE
        val animation = AnimationSet(false)
        animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadein))
        scrollLayout.startAnimation(animation)
        updateTasks()
    }

    fun hide() {
        val scrollLayout = mainLayout.toDoFolderScrollLayout
        scrollLayout.visibility = INVISIBLE
        val animation = AnimationSet(false)
        animation.addAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout))
        scrollLayout.startAnimation(animation)
    }

    override fun updateTasks() {
        folder?.let {
            removeAllViews()
            rows = Integer.max(
                1,
                displayMetricsController.screenHeight / (textSize + roundingRadius + heightMargin * 2)
            )
            rowWidths = IntArray(rows)
            var task: TaskLayout = FolderLayout(it)
            addView(task)
            task.title.measure(0, 0)
            val minWidthRow = getMinWidthRow()
            task.leftX = rowWidths[minWidthRow]
            task.rightX =
                task.leftX + task.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
            task.row = minWidthRow
            rowWidths[minWidthRow] += task.rightX - task.leftX
            val root = preferences.getString("folder${it.id}", "Add Tasks;;0")!!
                .split(";;").toTypedArray()
            for (i in 2 until root.size) {
                val s = preferences.getString(root[i], "Add Tasks;;0")!!
                    .split(";;").toTypedArray()
                task = if (root[i].startsWith("folder")) {
                    FolderLayout(this, root[i].substring(6).toInt(), s[0])
                } else {
                    TaskLayout(this, root[i].substring(4).toInt(), s[0])
                }
                task.setCompleted(s[1] == "1")
                addView(task)
                task.title.measure(0, 0)
                task.leftX = rowWidths[minWidthRow]
                task.rightX =
                    task.leftX + task.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
                task.row = minWidthRow
                rowWidths[minWidthRow] += task.rightX - task.leftX
            }
        }
    }

    override fun addTask(title: String, isFolder: Boolean): TaskLayout {
        val editor = preferences.edit()
        if (isFolder) {
            val folderCount = preferences.getInt("folderCount", 0)
            editor.putString("folder$folderCount", "$title;;0")
            editor.putInt("folderCount", folderCount + 1)
            val root = preferences.getString("folder" + folder!!.id, "Add Tasks;;0")
            editor.putString(
                "folder${folder!!.id}",
                "$root${if (root == "") "" else ";;"}folder$folderCount"
            )
        } else {
            val taskCount = preferences.getInt("taskCount", 0)
            editor.putString("task$taskCount", "$title;;0")
            editor.putInt("taskCount", taskCount + 1)
            val root = preferences.getString("folder${folder!!.id}", "Add Tasks;;0")
            editor.putString(
                "folder${folder!!.id}",
                "$root${if (root == "") "" else ";;"}task$taskCount"
            )
        }
        editor.apply()
        updateTasks()
        return getChildAt(childCount - 1) as TaskLayout
    }
}
