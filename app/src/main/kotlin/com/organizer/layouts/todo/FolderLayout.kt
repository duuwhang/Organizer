package com.organizer.layouts.todo

import android.graphics.drawable.PaintDrawable
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.data.Folder
import com.organizer.layouts.MainLayout

class FolderLayout : TaskLayout {

    private val displayMetricsController: DisplayMetricsController by inject()
    private val mainLayout: MainLayout by inject()

    constructor(parentLayout: ToDoLayout, folder: Folder) : super(parentLayout, folder)

    constructor(folder: FolderLayout) : super(folder.parentLayout, folder.task)

    init {
        (background.background as PaintDrawable)
            .setCornerRadius(displayMetricsController.dpToPx(4f).toFloat())
        setOnClickListener { mainLayout.toDoFolderLayout.hide() }
//        (task as Folder).tasks.forEach { it.observeCompleted {  } }
    }
}
