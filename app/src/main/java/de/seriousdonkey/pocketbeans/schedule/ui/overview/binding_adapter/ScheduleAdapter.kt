package de.seriousdonkey.pocketbeans.schedule.ui.overview.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleAdapter
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleItemOnClickListener
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleListItem

class ScheduleAdapter {
    companion object {
        @BindingAdapter(value = ["scheduleItems", "onItemClick"], requireAll = false)
        @JvmStatic
        fun scheduleOverviewAdapter(recyclerView: RecyclerView, items: List<ScheduleListItem>?, clickListener: ScheduleItemOnClickListener?) {
            if (items == null) {
                return
            }
            val adapter = ScheduleAdapter(items, clickListener!!)
            recyclerView.adapter = adapter
        }
    }
}