package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

import org.joda.time.DateTime

data class ScheduleHeader(val date: DateTime) : Comparable<ScheduleHeader> {

    override fun compareTo(other: ScheduleHeader): Int {
        return date.compareTo(other.date)
    }
}