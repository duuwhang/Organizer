package com.organizer.layouts.todo

import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.organizer.*
import com.organizer.MainActivity.Companion.inject
import com.organizer.data.Folder
import com.organizer.data.Task
import com.organizer.layouts.MainLayout

class ToDoFolderLayout : ToDoLayout() {

    private val preferences: PreferenceService by inject()
    private val mainLayout: MainLayout by inject()
    private val displayMetricsController: DisplayMetricsController by inject()

    private var folderLayout: FolderLayout? = null

    fun show(folderLayout: FolderLayout) {
        this.folderLayout = folderLayout
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
        if (folderLayout == null) return

        removeAllViews()
        val folderLayout = folderLayout!!
        addView(folderLayout)

        folderLayout.title.measure(0, 0)
        rows = Integer.max(
            1,
            displayMetricsController.screenHeight / (textSize + roundingRadius + heightMargin * 2)
        )
        rowWidths = IntArray(rows)
        val minWidthRow = getMinWidthRow()
        folderLayout.leftX = rowWidths[minWidthRow]
        folderLayout.rightX =
            folderLayout.leftX + folderLayout.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
        folderLayout.row = minWidthRow
        rowWidths[minWidthRow] += folderLayout.rightX - folderLayout.leftX

        preferences.restoreFolder(folderLayout.task.identifier)?.tasks?.forEach {
            val layout = when (it) {
                is Folder -> FolderLayout(this, it)
                else -> TaskLayout(this, it)
            }
            addView(layout)
            layout.title.measure(0, 0)
            layout.leftX = rowWidths[minWidthRow]
            layout.rightX =
                layout.leftX + layout.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
            layout.row = minWidthRow
            rowWidths[minWidthRow] += layout.rightX - layout.leftX
        }
    }

    override fun addTask(title: String, isFolder: Boolean): TaskLayout {
        (folderLayout?.task as? Folder)?.let {
            val newTask =
                if (isFolder) Folder(preferences.findFolderIndex(), title)
                else Task(preferences.findTaskIndex(), title)
            it.tasks.add(newTask)
            preferences.storeTasks(listOf(newTask, it))
        }
        updateTasks()
        return getChildAt(childCount - 1) as TaskLayout
    }
}
