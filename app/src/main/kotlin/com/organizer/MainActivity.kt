package com.organizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.organizer.layouts.MainLayout
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    companion object {
        inline fun <reified T> inject(): Lazy<T> = KoinJavaComponent.inject(T::class.java)

        inline fun <reified T> injectNow(): T = KoinJavaComponent.inject<T>(T::class.java).value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(module {
                single { this@MainActivity }
                single { DisplayMetricsController(windowManager, resources.displayMetrics.density) }
                @Suppress("USELESS_CAST")
                single { PreferenceServiceImpl(getPreferences(MODE_PRIVATE)) as PreferenceService }
                single { DateController() }
                single { MainLayout() }
            })
        }
        setContentView(injectNow<MainLayout>())
        val dateController = injectNow<DateController>()

        CalendarEvent(this, dateController.buildId(dateController.todayD, dateController.todayM, dateController.todayY), "Work", 8.0, 12.0)
        CalendarEvent(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Programming", 6.0, 8.0)
        CalendarEvent(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Dancing", 9.0, 15.0)
        CalendarEvent(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Work", 6.0, 17.0)
        CalendarEvent(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Family Dinner", 17.0, 18.0)
        CalendarEvent(this, dateController.buildId(dateController.todayD + 3, dateController.todayM, dateController.todayY), "Dancing", 6.0, 24.0)
    }

//    override fun onResume() {
//        super.onResume()
//        mainLayout.getToDoLayout().init();
//    }
}
