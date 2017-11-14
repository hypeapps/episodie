package pl.hypeapp.episodie

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Prefs @Inject constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var isEpisodeReminderNotificationsStarted: Boolean
        get() = prefs.getBoolean(NOTIFICATION_SEASON_TRACKER_ENABLED, false)
        set(value) = prefs.edit().putBoolean(NOTIFICATION_SEASON_TRACKER_ENABLED, value).apply()

    var reminderTvShowId: String
        get() = prefs.getString(REMINDER_TV_SHOW_ID, "")
        set(value) = prefs.edit().putString(REMINDER_TV_SHOW_ID, value).apply()

    var reminderSeasonId: String
        get() = prefs.getString(REMINDER_SEASON_ID, "")
        set(value) = prefs.edit().putString(REMINDER_SEASON_ID, value).apply()

    var isNotificationsSeasonTrackedDisplayed: Boolean
        get() = prefs.getBoolean(NOTIFICATION_SEASON_TRACKER_DISPLAYED, false)
        set(value) = prefs.edit().putBoolean(NOTIFICATION_SEASON_TRACKER_DISPLAYED, value).apply()

    var isNotificationsCanceled: Boolean
        get() = prefs.getBoolean(NOTIFICATION_SEASON_TRACKER_CANCELED, false)
        set(value) = prefs.edit().putBoolean(NOTIFICATION_SEASON_TRACKER_CANCELED, value).apply()

    fun clearPrefs() = prefs.edit().clear().apply()

    companion object {
        val PREFS_NAME = "pl.hypeapp.episodie.prefs"
        val NOTIFICATION_SEASON_TRACKER_DISPLAYED = "NOTIFICATION_SEASON_TRACKER_DISPLAYED"
        val NOTIFICATION_SEASON_TRACKER_ENABLED = "NOTIFICATION_SEASON_TRACKER_ENABLED"
        val NOTIFICATION_SEASON_TRACKER_CANCELED = "NOTIFICATION_SEASON_TRACKER_CANCELED"
        val REMINDER_TV_SHOW_ID = "REMINDER_TV_SHOW_ID"
        val REMINDER_SEASON_ID = "REMINDER_TV_SHOW_ID"
    }
}
