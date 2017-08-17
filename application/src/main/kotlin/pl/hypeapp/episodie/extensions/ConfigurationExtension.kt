package pl.hypeapp.episodie.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Build

fun Context.isLandscapeOrientation(): Boolean =
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

inline fun doFromSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT >= version) f()
}
