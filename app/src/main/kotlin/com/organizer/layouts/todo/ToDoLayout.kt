package com.organizer.layouts.todo

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.R.color
import com.organizer.layouts.BaseLayout
import com.organizer.layouts.MainLayout
import java.lang.Integer.max

open class ToDoLayout : BaseLayout() {

    private val preferences: SharedPreferences by inject()
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

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            var scrollOffset = rowWidths[getMinWidthRow()]
            for (i in 0 until childCount) {
                val task = getChildAt(i) as TaskLayout
                if (!task.done && task.leftX < scrollOffset) {
                    scrollOffset = task.leftX
                }
            }
            mainLayout.toDoScrollLayout.scrollTo(scrollOffset, 0)
        }
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
        rows = max(
            1,
            displayMetricsController.screenHeight / (textSize + roundingRadius + heightMargin * 2)
        )
        rowWidths = IntArray(rows)
        val root = preferences.getString("root", "")!!.split(";;").toTypedArray()
        var i = 0
        while (i < root.size || i == 0) {
            val s = preferences.getString(root[i], "Add Tasks;;0")!!
                .split(";;").toTypedArray()
            val task: TaskLayout = if (root[i].startsWith("folder")) {
                FolderLayout(
                    this,
                    if (root[i] == "") 0 else root[i].substring(6).toInt(),
                    s[0]
                )
            } else {
                TaskLayout(
                    this,
                    if (root[i] == "") 0 else root[i].substring(4).toInt(),
                    s[0]
                )
            }
            task.setCompleted(s[1] == "1")
            addView(task)
            task.title.measure(0, 0)
            val minWidthRow = getMinWidthRow()
            task.leftX = rowWidths[minWidthRow]
            task.rightX =
                task.leftX + task.title.measuredWidth + roundingRadius * 2 + widthMargin * 2
            task.row = minWidthRow
            rowWidths[minWidthRow] += task.rightX - task.leftX
            i++
        }
    }

    open fun addTask(title: String, isFolder: Boolean): TaskLayout {
        val editor = preferences.edit()
        if (isFolder) {
            val folderCount = preferences.getInt("folderCount", 0)
            editor.putString("folder$folderCount", "$title;;0")
            editor.putInt("folderCount", folderCount + 1)
            val root = preferences.getString("root", "")
            editor.putString("root", "$root${if (root == "") "" else ";;"}folder$folderCount")
        } else {
            val taskCount = preferences.getInt("taskCount", 0)
            editor.putString("task$taskCount", "$title;;0")
            editor.putInt("taskCount", taskCount + 1)
            val root = preferences.getString("root", "")
            editor.putString("root", "$root${if (root == "") "" else ";;"}task$taskCount")
        }
        editor.apply()
        updateTasks()
        return getChildAt(childCount - 1) as TaskLayout
    }

    init {
        setBackgroundColor(Color.parseColor(resources.getString(color.colorBackground)))
        layoutParams =
            LayoutParams(Int.MAX_VALUE, LayoutParams.MATCH_PARENT)
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        textSize = textView.textSize.toInt()
    }
}
