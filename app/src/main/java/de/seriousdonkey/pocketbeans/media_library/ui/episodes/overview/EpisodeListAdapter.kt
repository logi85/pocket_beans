package de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.databinding.EpisodeRvItemBinding
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import org.joda.time.DateTime

class EpisodeListAdapter(val clickListener: EpisodeListOnClickListener)
    : PagedListAdapter<Episode, EpisodeListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EpisodeRvItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = getItem(position)
        if (episode != null) {
            holder.bind(episode)
        } else {
            holder.bind(Episode(0, "", "", DateTime.now(), null))
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(private val binding: EpisodeRvItemBinding, val clickListener: EpisodeListOnClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Episode) {
            binding.episode = item
            binding.root.setOnClickListener { clickListener.onClick(item) }
            binding.executePendingBindings()
        }
    }
}