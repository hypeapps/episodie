package pl.hypeapp.episodie

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Prefs @Inject constructor(context: Context) {

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var notificationSeasonTrackerDisplayed: Boolean
        get() = prefs.getBoolean(NOTIFICATION_SEASON_TRACKER_DISPLAYED, false)
        set(value) = prefs.edit().putBoolean(NOTIFICATION_SEASON_TRACKER_DISPLAYED, value).apply()

    companion object {
        val PREFS_NAME = "pl.hypeapp.episodie.prefs"
        val NOTIFICATION_SEASON_TRACKER_DISPLAYED = "NOTIFICATION_SEASON_TRACKER_DISPLAYED"
        val NOTIFICATION_SEASON_TRACKER_ENABLED = "NOTIFICATION_SEASON_TRACKER_ENABLED"
    }
}
