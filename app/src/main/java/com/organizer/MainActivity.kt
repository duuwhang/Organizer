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
        inline fun <reified T> inject(): Lazy<T> {
            return KoinJavaComponent.inject(T::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(module {
                single { this@MainActivity }
                single { this@MainActivity.getPreferences(MODE_PRIVATE) }
                single { DisplayMetricsController(windowManager, resources.displayMetrics.density) }
                single { DateController() }
                single { MainLayout() }
            })
        }
        setContentView(inject<MainLayout>().value)
        val dateController = inject<DateController>().value

        Task(this, dateController.buildId(dateController.todayD, dateController.todayM, dateController.todayY), "Work", 8.0, 12.0)
        Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Programming", 6.0, 8.0)
        Task(this, dateController.buildId(dateController.todayD + 1, dateController.todayM, dateController.todayY), "Dancing", 9.0, 15.0)
        Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Work", 6.0, 17.0)
        Task(this, dateController.buildId(dateController.todayD + 2, dateController.todayM, dateController.todayY), "Family Dinner", 17.0, 18.0)
        Task(this, dateController.buildId(dateController.todayD + 3, dateController.todayM, dateController.todayY), "Dancing", 6.0, 24.0)
    }

//    override fun onResume() {
//        super.onResume()
//        mainLayout.getToDoLayout().init();
//    }
}
