package de.seriousdonkey.pocketbeans.media_library.ui.show.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.databinding.AllShowRvItemBinding
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.models.ShowOverview

class ShowOverviewAdapter(private val _clickListener: ShowOverviewItemClickListener) : PagedListAdapter<ShowOverview, ShowOverviewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AllShowRvItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, _clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        } else {
            holder.bind(ShowOverview(0, "", ""))
        }
    }

    class ViewHolder(private val _binding: AllShowRvItemBinding,
                     private val _clickListener: ShowOverviewItemClickListener) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(item: ShowOverview) {
            _binding.item = item
            _binding.root.setOnClickListener {
                _clickListener.onClick(item)
            }
            _binding.executePendingBindings()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowOverview>() {
            override fun areItemsTheSame(oldItem: ShowOverview, newItem: ShowOverview): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShowOverview, newItem: ShowOverview): Boolean {
                return oldItem == newItem
            }

        }
    }

}