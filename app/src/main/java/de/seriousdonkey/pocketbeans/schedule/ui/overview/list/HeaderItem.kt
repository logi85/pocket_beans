package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

data class HeaderItem(val header: ScheduleHeader) : ScheduleListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}