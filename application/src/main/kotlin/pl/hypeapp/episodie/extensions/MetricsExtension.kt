package pl.hypeapp.episodie.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import pl.hypeapp.episodie.R

fun Resources.convertPixelsToDp(pixels: Float): Float =
        pixels / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Context.getAppUsableScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun Context.getRealScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getRealSize(size)
    return size
}

fun Context.getNavigationBarSize(): Point {
    val appUsableSize = this.getAppUsableScreenSize()
    val realScreenSize = this.getRealScreenSize()
    // navigation bar on the right
    if (appUsableSize.x < realScreenSize.x) {
        return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
    }
    // navigation bar at the bottom
    if (appUsableSize.y < realScreenSize.y) {
        return Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
    }
    // navigation bar is not present
    return Point()
}

fun Context.isNavigationBarLandscape(): Boolean = getNavigationBarSize().x == getRealScreenSize().x

fun Context.getActionBarSize(): Int {
    val value = TypedValue()
    this.theme.resolveAttribute(R.attr.actionBarSize, value, true)
    val actionBarSize = TypedValue.complexToDimensionPixelSize(
            value.data, this.resources.displayMetrics)
    return actionBarSize
}

fun Resources.getStatusBarHeight(): Int {
    var statusBarHeight: Int = 0
    val resourceId = this.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = this.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}
