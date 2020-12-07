package de.seriousdonkey.pocketbeans.schedule.ui.list

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

data class ScheduleHeader(val date: DateTime) : Comparable<ScheduleHeader> {

    fun formattedDay() : String {
        return DAY_FORMATTER.print(date)
    }

    fun formattedDate() : String {
        return DATE_FORMATTER.print(date)
    }

    override fun compareTo(other: ScheduleHeader): Int {
        return date.compareTo(other.date)
    }

    companion object {
        private val DAY_FORMATTER = DateTimeFormat.forPattern("EEEE")
                .withLocale(Locale.GERMAN)
                .withZone(DateTimeZone.forID("Europe/Berlin"))
        private val DATE_FORMATTER = DateTimeFormat.forPattern("dd. MMMM yyyy")
                .withLocale(Locale.GERMAN)
                .withZone(DateTimeZone.forID("Europe/Berlin"))
    }

}