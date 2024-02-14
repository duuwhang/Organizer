package com.organizer.layouts.todo

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.organizer.*
import com.organizer.MainActivity.Companion.inject
import com.organizer.data.Folder
import com.organizer.data.Task
import com.organizer.layouts.Layout
import com.organizer.layouts.MainLayout

open class ToDoLayout : Layout() {

    private val preferences: PreferenceService by inject()
    private val displayMetricsController: DisplayMetricsController by inject()
    private val mainLayout: MainLayout by inject()

    val roundingRadius = displayMetricsController.dpToPx(25f)
    val widthMargin = displayMetricsController.dpToPx(15f)
    val heightMargin = displayMetricsController.dpToPx(10f)
    internal val textSizeSp = 18
    protected val textSize: Int
    var rowWidths = IntArray(1)
    protected var rows = 0
    private val childRect = Rect()

    init {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackground))
        layoutParams = LayoutParams(Int.MAX_VALUE, LayoutParams.MATCH_PARENT)
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        textSize = textView.textSize.toInt()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != VISIBLE) return

        var scrollOffset = rowWidths[getMinWidthRow()]
        children.forEach {
            if (it is TaskLayout && it.task.completed && it.leftX < scrollOffset)
                scrollOffset = it.leftX
        }
        mainLayout.toDoScrollLayout.scrollTo(scrollOffset, 0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        updateTasks()
        for (i in 0 until childCount) {
            val child = getChildAt(i) as TaskLayout
            val childHeight = (bottom - top) / rowWidths.size
            childRect.left = child.leftX
            childRect.top = childHeight * child.row % (bottom - top)
            childRect.right = child.rightX
            childRect.bottom = childRect.top + childHeight
            getChildAt(i).layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        }
    }

    fun getMinWidthRow(): Int {
        var minWidthIndex = 0
        for (row in 1 until rowWidths.size) {
            if (rowWidths[row] < rowWidths[minWidthIndex]) {
                minWidthIndex = row
            }
        }
        return minWidthIndex
    }

    val maxWidth: Int
        get() {
            var maxWidth = rowWidths[0]
            for (row in 1 until rowWidths.size) {
                if (rowWidths[row] > maxWidth) {
                    maxWidth = rowWidths[row]
                }
            }
            return maxWidth + widthMargin
        }

    open fun updateTasks() {
        removeAllViews()
        rows = Integer.max(
            1,
            displayMetricsController.screenHeight / (textSize + roundingRadius + heightMargin * 2)
        )
        rowWidths = IntArray(rows)

        (preferences.restoreFolder("root")?.tasks ?: listOf(Task(-1, DEFAULT_MESSAGE, false)))
            .forEach {
                val layout = when (it) {
                    is Folder -> FolderLayout(this, it)
                    else -> TaskLayout(this, it)
                }
                addView(layout)
                layout.title.measure(0, 0)
                val minWidthRow = getMinWidthRow()
                layout.leftX = rowWidths[minWidthRow]
                layout.rightX =
                    layout.leftX + layout.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
                layout.row = minWidthRow
                rowWidths[minWidthRow] += layout.rightX - layout.leftX
            }
    }

    open fun addTask(title: String, isFolder: Boolean): TaskLayout {
        preferences.restoreFolder("root")?.let {
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
