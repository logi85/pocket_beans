package de.seriousdonkey.pocketbeans.schedule.ui.list

import org.joda.time.DateTime

data class HeaderItem(val header: ScheduleHeader) : ScheduleListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}