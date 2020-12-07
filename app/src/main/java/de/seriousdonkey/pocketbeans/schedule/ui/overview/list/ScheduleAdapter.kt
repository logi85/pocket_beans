package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.databinding.ScheduleRvEventItemBinding
import de.seriousdonkey.pocketbeans.databinding.ScheduleRvHeaderItemBinding

class ScheduleAdapter(private var _items: List<ScheduleListItem>,
                      private val _clickListener: ScheduleItemOnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateData(items: List<ScheduleListItem>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return _items[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ScheduleListItem.TYPE_HEADER) {
            val binding = ScheduleRvHeaderItemBinding.inflate(layoutInflater, parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ScheduleRvEventItemBinding.inflate(layoutInflater, parent, false)
            EventViewHolder(binding, _clickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == ScheduleListItem.TYPE_HEADER) {
            val headerItem = _items[position] as HeaderItem
            val viewHolder = holder as HeaderViewHolder
            viewHolder.bind(headerItem.header)
        } else {
            val eventItem = _items[position] as EventItem
            val viewHolder = holder as EventViewHolder
            viewHolder.bind(eventItem.entry)
        }
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    class HeaderViewHolder(private val _binding: ScheduleRvHeaderItemBinding) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(header: ScheduleHeader) {
            _binding.header = header
            _binding.executePendingBindings()
        }
    }

    class EventViewHolder(private val _binding: ScheduleRvEventItemBinding,
                          private val _clickListener: ScheduleItemOnClickListener) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(entry: ScheduleEntry) {
            _binding.entry = entry;
            _binding.root.setOnClickListener { _clickListener.onClick(_binding.root, entry) }
            _binding.executePendingBindings()
        }
    }
}