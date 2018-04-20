package pl.hypeapp.episodie.extensions

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.widget.TextView
import pl.hypeapp.episodie.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun TextView.setRuntime(runtime: Long?) {
    this.text = getRuntimeFormatted(resources, runtime)
}

fun getRuntimeFormatted(resources: Resources, runtime: Long?): String {
    runtime?.let {
        return when {
            isUpToOneHour(it) -> String.format(resources.getString(R.string.time_unit_format_minutes), TimeUnit.MINUTES.toMinutes(it))
            isLessThanOneDay(it) -> String.format(resources.getString(R.string.time_unit_format_hours), TimeUnit.MINUTES.toHours(it))
            else -> String.format(resources.getString(R.string.time_unit_format_days), TimeUnit.MINUTES.toDays(it))
        }
    }
    return ""
}

fun TextView.setFullRuntime(runtime: Long?) {
    this.text = getFullRuntimeFormatted(resources, runtime)
}

fun getFullRuntimeFormatted(resources: Resources, runtime: Long?): String {
    runtime?.let {
        val days: Long = TimeUnit.MINUTES.toDays(it)
        val hours = TimeUnit.MINUTES.toHours((it - TimeUnit.DAYS.toMinutes(days)))
        val minutes = TimeUnit.MINUTES.toMinutes(it - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days))
        val minutesFormatted: String = if (minutes < 10) "0$minutes" else minutes.toString()
        return if (isUpToOneHour(it)) {
            String.format(resources.getString(R.string.time_unit_format_minutes), it)
        } else if (isAtLeastOneDay(it)) {
            if (isMinutesZerosOnly(minutesFormatted))
                String.format(resources.getString(R.string.time_unit_format_days_hours), days, hours)
            else
                String.format(resources.getString(R.string.time_unit_format_full), days, hours, minutesFormatted)
        } else {
            if (isMinutesZerosOnly(minutesFormatted))
                String.format(resources.getString(R.string.time_unit_format_hours), hours)
            else
                String.format(resources.getString(R.string.time_unit_format_hours_minutes), hours, minutesFormatted)
        }
    }
    return ""
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

fun TextView.setDateFormat(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    this.text = simpleDateFormat.format(date)
}

fun daysBetween(startDate: Date, endDate: Date): Long {
    val sDate = getDatePart(startDate)
    val eDate = getDatePart(endDate)

    var daysBetween: Long = 0
    while (sDate.before(eDate)) {
        sDate.add(Calendar.DAY_OF_MONTH, 1)
        daysBetween++
    }
    return daysBetween
}

fun getDatePart(date: Date): Calendar {
    val cal = Calendar.getInstance()       // get calendar instance
    cal.time = date
    cal.set(Calendar.HOUR_OF_DAY, 0)            // set hour to midnight
    cal.set(Calendar.MINUTE, 0)                 // set minute in hour
    cal.set(Calendar.SECOND, 0)                 // set second in minute
    cal.set(Calendar.MILLISECOND, 0)            // set millisecond in second
    return cal                                  // return the date part
}

fun isAfterPremiereDate(date: Date): Boolean = date.let {
    return Calendar.getInstance().time.after(it)
}

private fun isAtLeastOneDay(runtime: Long) = runtime >= TimeUnit.MINUTES.convert(24 * 60, TimeUnit.MINUTES)

private fun isLessThanOneDay(it: Long) = it <= TimeUnit.MINUTES.convert(24 * 60, TimeUnit.MINUTES)

private fun isUpToOneHour(runtime: Long) = runtime <= TimeUnit.MINUTES.convert(60, TimeUnit.MINUTES)

private fun isMinutesZerosOnly(minutesFormatted: String) = minutesFormatted == "00"


