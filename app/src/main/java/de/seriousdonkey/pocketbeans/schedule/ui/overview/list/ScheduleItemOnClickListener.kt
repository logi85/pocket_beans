package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

import android.view.View

interface ScheduleItemOnClickListener {
    fun onClick(view: View, entry: ScheduleEntry)
}