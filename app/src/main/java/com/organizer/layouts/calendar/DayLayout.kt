package com.organizer.layouts.calendar

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import com.organizer.MainActivity
import com.organizer.MainActivity.Companion.inject

class DayLayout : ConstraintLayout(inject<Context>().value) {
    val textView = TextView(context)

    init {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        textView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        textView.textAlignment = TEXT_ALIGNMENT_CENTER
        addView(textView)
    }
}
