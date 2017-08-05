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
        val minutesFormatted: String = if (minutes < 10) "0$minutes" else minutes.toString()
        if (isUpToOneHour(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_minutes), it)
        } else if (isAtLeastOneDay(it)) {
            this.text = if (isMinutesZerosOnly(minutesFormatted))
                String.format(resources.getString(R.string.time_unit_format_days_hours), days, hours)
            else
                String.format(resources.getString(R.string.time_unit_format_full), days, hours, minutesFormatted)
        } else {
            this.text = if (isMinutesZerosOnly(minutesFormatted))
                String.format(resources.getString(R.string.time_unit_format_hours), hours)
            else
                String.format(resources.getString(R.string.time_unit_format_hours_minutes), hours, minutesFormatted)
        }
    }
}

private fun isAtLeastOneDay(runtime: Long) = runtime >= MINUTES.convert(24 * 60, MINUTES)

private fun isLessThanOneDay(it: Long) = it <= MINUTES.convert(24 * 60, MINUTES)

private fun isUpToOneHour(runtime: Long) = runtime <= MINUTES.convert(60, MINUTES)

private fun isMinutesZerosOnly(minutesFormatted: String) = minutesFormatted == "00"
