package com.organizer

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

class DisplayMetricsController(
    private val windowManager: WindowManager,
    private val pixelDensity: Float
) {

    fun dpToPx(dp: Float): Int {
        return (dp * pixelDensity).toInt()
    }

    @Suppress("DEPRECATION")
    val screenHeight: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            windowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }

    @Suppress("DEPRECATION")
    val screenWidth: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
}
