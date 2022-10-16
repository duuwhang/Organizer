package com.organizer.layouts.calendar

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.organizer.MainActivity.Companion.inject

class DayLayout : ConstraintLayout(inject<Context>().value) {

    val textView = TextView(context)

    init {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        addView(textView.apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            textAlignment = TEXT_ALIGNMENT_CENTER
        })
    }
}
