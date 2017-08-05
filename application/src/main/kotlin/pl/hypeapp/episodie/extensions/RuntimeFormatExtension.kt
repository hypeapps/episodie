package pl.hypeapp.episodie.extensions

import android.widget.TextView
import pl.hypeapp.episodie.R
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MINUTES

fun TextView.setRuntime(runtime: Long?) {
    runtime?.let {
        if (isUpToOneHour(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_minutes), MINUTES.toMinutes(it))
        } else if (isLessThanOneDay(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_hours), MINUTES.toHours(it))
        } else {
            this.text = String.format(resources.getString(R.string.time_unit_format_days), MINUTES.toDays(it))
        }
    }
}

fun TextView.setFullRuntime(runtime: Long?) {
    runtime?.let {
        val days: Long = MINUTES.toDays(it)
        val hours = MINUTES.toHours((it - DAYS.toMinutes(days)))
        val minutes = MINUTES.toMinutes(it - HOURS.toMinutes(hours) - DAYS.toMinutes(days))
        var minutesFormatted: String = minutes.toString()
        if (minutes < 10) {
            minutesFormatted = "0$minutes"
        }
        if (isUpToOneHour(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_minutes), it)
        } else if (isAtLeastOneDay(it)) {
            if (isMinutesOnlyZeros(minutesFormatted)) {
                this.text = String.format(resources.getString(R.string.time_unit_format_days_hours), days, hours)
            } else {
                this.text = String.format(resources.getString(R.string.time_unit_format_full), days, hours, minutesFormatted)
            }
        } else {
            if (isMinutesOnlyZeros(minutesFormatted)) {
                this.text = String.format(resources.getString(R.string.time_unit_format_hours), hours)
            } else {
                this.text = String.format(resources.getString(R.string.time_unit_format_hours_minutes), hours, minutesFormatted)
            }
        }
    }
}

private fun isAtLeastOneDay(runtime: Long) = runtime >= MINUTES.convert(24 * 60, MINUTES)

private fun isLessThanOneDay(it: Long) = it <= MINUTES.convert(24 * 60, MINUTES)

private fun isUpToOneHour(runtime: Long) = runtime <= MINUTES.convert(60, MINUTES)

private fun isMinutesOnlyZeros(minutesFormatted: String) = minutesFormatted == "00"
