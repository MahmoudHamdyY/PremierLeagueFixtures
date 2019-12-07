package com.fakss.premierleaguefixtures

import org.joda.time.Days
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.displayHeaderDate(): String {
    val df = SimpleDateFormat("EEE, MMM d", Locale.US)
    val currentLocalDate = LocalDate.now()
    val localDate = LocalDate(this)

    return when (Days.daysBetween(currentLocalDate, localDate).days) {
        -1 -> String.format("%s %s", "Yesterday, ", df.format(time))
        0 -> String.format("%s %s", "Today, ", df.format(time))
        1 -> String.format("%s %s", "Tomorrow, ", df.format(time))
        else -> df.format(time)
    }
}

fun String.getCalendar(): Calendar {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    df.timeZone = TimeZone.getTimeZone("UTC")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = df.parse(this).time
    return calendar
}

fun Calendar.isSameDate(calendar: Calendar): Boolean =
    (get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
            && get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
            && get(Calendar.YEAR) == calendar.get(Calendar.YEAR))

fun Calendar.isTodayOrFuture(): Boolean {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    return now.before(this)
}


fun Calendar.displayTime(): String = SimpleDateFormat("HH:mm", Locale.US).format(time)