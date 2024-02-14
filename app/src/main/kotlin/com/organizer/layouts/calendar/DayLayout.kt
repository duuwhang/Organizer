package com.organizer.layouts.calendar

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.organizer.MainActivity.Companion.injectNow

class DayLayout : ConstraintLayout(injectNow()) {

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
