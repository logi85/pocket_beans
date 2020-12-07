package de.seriousdonkey.pocketbeans.schedule.ui.list

data class EventItem(val entry: ScheduleEntry) : ScheduleListItem() {
    override fun getType(): Int {
        return TYPE_EVENT
    }
}