package com.organizer

import com.organizer.MainActivity.Companion.inject

@Suppress("unused")
class DateController {

    private val displayController: DisplayMetricsController by inject()

    var dictionary = HashMap<Int, MutableList<CalendarEvent>>()
    var days = HashMap<Int, Day>()
    var todayD = 7
    var todayM = 4
    var todayY = 2021
    var today = buildId(todayD, todayM, todayY)
    var textViewSize = 0

    enum class Weekdays {
        SU, MO, TU, WE, TH, FR, SA
    }

    fun addDays(numDays: Int, offset: Int) {
        for (i in offset until numDays + offset) {
            days[i] = Day(buildId(todayD + i, todayM, todayY)) // TODO offset rip
        }
    }

    fun buildId(d: Int, m: Int, y: Int): Int {
        return y * 10000 + m * 100 + d
    }

    fun buildD(id: Int): Int {
        return id - buildM(id) * 100 - buildY(id) * 10000
    }

    fun buildM(id: Int): Int {
        return id / 100 - buildY(id) * 100
    }

    fun buildY(id: Int): Int {
        return id / 10000
    }

    fun idExists(id: Int): Boolean {
        val day = Day(id)
        return day.dayExists()
    }

    fun idDebug(id: Int) {
        val day = Day(id)
        day.dayDebug()
    }

    fun calculateHeight(start: Double, end: Double): Int {
        val dayStart = 6.0
        val dayEnd = 24.0
        val contentHeight = (displayController.screenHeight - textViewSize).toDouble()
        return (contentHeight * ((end - start) / (dayEnd - dayStart))).toInt()
    }

    fun calculateY(start: Double): Int {
        val dayStart = 6.0
        val dayEnd = 24.0
        val contentHeight = (displayController.screenHeight - textViewSize).toDouble()
        return (textViewSize + contentHeight * ((start - dayStart) / (dayEnd - dayStart))).toInt()
    }
}
