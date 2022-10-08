package com.organizer.layouts.todo

import android.annotation.SuppressLint
import android.graphics.drawable.PaintDrawable
import com.organizer.DisplayMetricsController
import com.organizer.MainActivity.Companion.inject
import com.organizer.layouts.MainLayout

@SuppressLint("ViewConstructor")
class FolderLayout : TaskLayout {
    private val displayMetricsController: DisplayMetricsController by inject()
    private val mainLayout: MainLayout by inject()

    constructor(
        parentLayout: ToDoLayout?,
        id: Int,
        title: String
    ) : super(parentLayout!!, id, title)

    constructor(folder: FolderLayout) : super(
        folder.parentLayout,
        folder.identifier,
        folder.title.text.toString()
    )

    init {
        (background.background as PaintDrawable).setCornerRadius(
            displayMetricsController.dpToPx(
                4f
            ).toFloat()
        )
        setOnClickListener { mainLayout.toDoFolderLayout.hide() }
    }
}
