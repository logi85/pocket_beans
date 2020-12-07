package de.seriousdonkey.pocketbeans.schedule.ui.overview.binding_adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleType

class ScheduleEntryTypeAdapter {
    companion object {
        @BindingAdapter("scheduleTypeBackground")
        @JvmStatic
        fun scheduleType(textView: TextView, type: ScheduleType) {
            when(type) {
                ScheduleType.LIVE -> textView.setBackgroundResource(R.drawable.schedule_live_background)
                ScheduleType.PREMIERE -> textView.setBackgroundResource(R.drawable.schedule_premiere_background)
                ScheduleType.RERUN -> textView.setBackgroundResource(R.drawable.schedule_rerun_background)
            }
        }
    }
}