package com.organizer

import com.organizer.MainActivity.Companion.inject

class Day(var id: Int) {
    private val dateController: DateController by inject()

    var d: Int = 0
    var m: Int = 0
    var y: Int = 0
    var weekday: String? = null
    var monthdays = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private fun initDay() {
        d = dateController.buildD(id)
        m = dateController.buildM(id)
        y = dateController.buildY(id)

        dayDebug()
        val t = intArrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
        var yy = 2000 + y
        if (m < 3) {
            yy -= 1
        }
        weekday =
            DateController.Weekdays.values()[(yy + yy / 4 - yy / 100 + yy / 400 + t[m - 1] + d) % 7].toString()
    }

    fun addDays(days: Int) {
        d += days
        dayDebug()
    }

    fun addMonths(months: Int) {
        m += months
        dayDebug()
    }

    fun addYears(years: Int) {
        y += years
        dayDebug()
    }

    fun dayExists(): Boolean {
        if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) {
            monthdays[1] = 29
        }
        return !(d > monthdays[m - 1] || d < 1 || m > 12 || m < 1)
    }

    fun dayDebug() {
        while (!dayExists()) {
            if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) {
                monthdays[1] = 29
            }
            val dd = d
            d -= Math.max(1, d / (monthdays[m - 1] + 1)) * monthdays[m - 1]
            m += Math.max(1, dd / (monthdays[m - 1] + 1))
            y += m % 12 * (m / 12)
            m -= m % 12 * (m / 12) * 12
        }
        id = dateController.buildId(d, m, y)
    }
}
