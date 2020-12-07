package de.seriousdonkey.pocketbeans.info.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.databinding.InfoListRvEntryBinding
import de.seriousdonkey.pocketbeans.info.ui.models.InfoEntry

class InfoListViewAdapter(private var _entries: List<InfoEntry>,
                          private val _callback: OnInfoItemEntryClickedCallback) : RecyclerView.Adapter<InfoListViewAdapter.ViewHolder>() {

    fun updateInfoEntries(entries: List<InfoEntry>) {
        _entries = entries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = InfoListRvEntryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, _callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = _entries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int {
        return _entries.size
    }

    class ViewHolder(private val _binding: InfoListRvEntryBinding,
                     private val _callback: OnInfoItemEntryClickedCallback) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(entry: InfoEntry) {
            _binding.entry = entry

            if (entry.hasExternalPage) {
                _binding.root.setOnClickListener { _callback.onItemClicked(entry) }
            }

            _binding.executePendingBindings()
        }
    }
}