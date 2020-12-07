package de.seriousdonkey.pocketbeans.schedule.ui.list

abstract class ScheduleListItem {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_EVENT = 1
    }

    abstract fun getType() : Int
}