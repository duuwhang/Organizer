package com.organizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.organizer.layouts.MainLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mainLayout: MainLayout? = null

    companion object {
        private lateinit var activity: MainActivity
        private lateinit var displayMetricsController: DisplayMetricsController
        private lateinit var dateController: DateController

        @JvmStatic
        fun getInstance(): MainActivity {
            return activity
        }

        @JvmStatic
        fun getDisplayMetricsController(): DisplayMetricsController {
            return displayMetricsController
        }

        @JvmStatic
        fun getDateController(): DateController {
            return dateController
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Objects.requireNonNull(this.supportActionBar)?.hide()
        activity= this
        displayMetricsController = DisplayMetricsController(windowManager, resources.displayMetrics.density)
        dateController = DateController()
        mainLayout = MainLayout(this)
        setContentView(mainLayout)
        Task(this, dateController!!.buildId(dateController!!.todayD, dateController!!.todayM, dateController!!.todayY), "Work", 8.0, 12.0)
        Task(this, dateController!!.buildId(dateController!!.todayD + 1, dateController!!.todayM, dateController!!.todayY), "Programming", 6.0, 8.0)
        Task(this, dateController!!.buildId(dateController!!.todayD + 1, dateController!!.todayM, dateController!!.todayY), "Dancing", 9.0, 15.0)
        Task(this, dateController!!.buildId(dateController!!.todayD + 2, dateController!!.todayM, dateController!!.todayY), "Work", 6.0, 17.0)
        Task(this, dateController!!.buildId(dateController!!.todayD + 2, dateController!!.todayM, dateController!!.todayY), "Family Dinner", 17.0, 18.0)
        Task(this, dateController!!.buildId(dateController!!.todayD + 3, dateController!!.todayM, dateController!!.todayY), "Dancing", 6.0, 24.0)
    }

    override fun onResume() {
        super.onResume()
        //mainLayout.getToDoLayout().init();
    }

    fun getLayout(): MainLayout? {
        return mainLayout
    }
}
