package com.organizer

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import com.organizer.MainActivity.Companion.inject

class CalendarEvent {
    private val dateController: DateController by inject()
    var context: Context
    var id: Int
    var location = "home"
    var start = 6.0
    var end = start + 1
    var button: Button? = null

    internal constructor(context: Context, id: Int, location: String, start: Double, end: Double) {
        this.context = context
        this.id = id
        this.location = location
        this.start = start
        this.end = end
        initEvent()
    }

    internal constructor(context: Context, id: Int, location: String, start: Double) {
        this.context = context
        this.id = id
        this.location = location
        this.start = start
        end = start + 1
        initEvent()
    }

    internal constructor(context: Context, id: Int, location: String) {
        this.context = context
        this.id = id
        this.location = location
        initEvent()
    }

    internal constructor(context: Context, id: Int, start: Double, end: Double) {
        this.context = context
        this.id = id
        this.start = start
        this.end = end
        initEvent()
    }

    internal constructor(context: Context, id: Int, start: Double) {
        this.context = context
        this.id = id
        this.start = start
        end = start + 1
        initEvent()
    }

    internal constructor(context: Context, id: Int) {
        this.context = context
        this.id = id
        initEvent()
    }

    private fun initEvent() {
        if (!dateController.dictionary.containsKey(id)) {
            dateController.dictionary[id] = ArrayList()
        }
        dateController.dictionary[id]?.add(this)
        button = Button(context)
        button!!.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button!!.text = location
        button!!.y = dateController.calculateY(start).toFloat()
        button!!.height = dateController.calculateHeight(start, end)
    }

    fun generateButton() {
        button = Button(context)
        button!!.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button!!.text = location
        button!!.y = dateController.calculateY(start).toFloat()
        button!!.height = dateController.calculateHeight(start, end)
    }
}
