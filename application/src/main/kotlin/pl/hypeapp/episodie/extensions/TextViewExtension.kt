package pl.hypeapp.episodie.extensions

import android.os.Build
import android.text.Html
import android.widget.TextView
import pl.hypeapp.episodie.R
import java.util.concurrent.TimeUnit

fun TextView.setRuntime(runtime: Long?) {
    runtime?.let {
        if (isUpToOneHour(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_minutes), TimeUnit.MINUTES.toMinutes(it))
        } else if (isLessThanOneDay(it)) {
            this.text = String.format(resources.getString(R.string.time_unit_format_hours), TimeUnit.MINUTES.toHours(it))
        } else {
            this.text = String.format(resources.getString(R.string.time_unit_format_days), TimeUnit.MINUTES.toDays(it))
        }
    }
}

fun TextView.setFullRuntime(runtime: Long?) {
    runtime?.let {
        val days: Long = TimeUnit.MINUTES.toDays(it)
        val hours = TimeUnit.MINUTES.toHours((it - TimeUnit.DAYS.toMinutes(days)))
        val minutes = TimeUnit.MINUTES.toMinutes(it - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days))
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

@SuppressWarnings("deprecation")
fun TextView.setTextFromHtml(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString().trim()
    else
        this.text = Html.fromHtml(text).toString().trim()
}

fun TextView.setZeroPrefixUnderTen(number: Int) {
    if (number < 10)
        this.text = String.format(resources.getString(R.string.zero_prefix_format), number)
    else
        this.text = number.toString()
}

fun TextView.setHashTagPrefix(number: Int) {
    if (number < 10) {
        val zeroPrefix = String.format(resources.getString(R.string.zero_prefix_format), number)
        this.text = String.format(resources.getString(R.string.item_episode_hashtag_prefix_format), zeroPrefix)
    } else {
        this.text = String.format(resources.getString(R.string.item_episode_hashtag_prefix_format), number.toString())
    }
}

fun TextView.setSPrefix(number: Int) {
    if (number < 10) {
        val zeroPrefix = String.format(resources.getString(R.string.zero_prefix_format), number)
        this.text = String.format(resources.getString(R.string.item_season_s_prefix_format), zeroPrefix)
    } else {
        this.text = number.toString()
    }
}

private fun isAtLeastOneDay(runtime: Long) = runtime >= TimeUnit.MINUTES.convert(24 * 60, TimeUnit.MINUTES)

private fun isLessThanOneDay(it: Long) = it <= TimeUnit.MINUTES.convert(24 * 60, TimeUnit.MINUTES)

private fun isUpToOneHour(runtime: Long) = runtime <= TimeUnit.MINUTES.convert(60, TimeUnit.MINUTES)

private fun isMinutesZerosOnly(minutesFormatted: String) = minutesFormatted == "00"
