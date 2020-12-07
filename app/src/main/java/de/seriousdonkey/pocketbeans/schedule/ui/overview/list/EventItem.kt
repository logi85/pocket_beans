package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

data class EventItem(val entry: ScheduleEntry) : ScheduleListItem() {
    override fun getType(): Int {
        return TYPE_EVENT
    }
}