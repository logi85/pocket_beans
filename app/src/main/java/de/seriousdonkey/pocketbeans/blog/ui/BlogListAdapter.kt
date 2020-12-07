package de.seriousdonkey.pocketbeans.blog.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import de.seriousdonkey.pocketbeans.databinding.BlogRvItemBinding
import org.joda.time.DateTime

class BlogListAdapter(private val _onClickListener: BlogItemOnClickListener) : PagedListAdapter<BlogEntry, BlogListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BlogRvItemBinding.inflate(inflater, parent,false)
        return ViewHolder(binding, _onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        if (entry != null) {
            holder.bind(entry)
        } else {
            holder.bind(BlogEntry(0, "", "", "", false, DateTime.now()))
        }
    }

    companion object {
        val DIFF_CALLBACK = object: ItemCallback<BlogEntry>() {
            override fun areItemsTheSame(oldItem: BlogEntry, newItem: BlogEntry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BlogEntry, newItem: BlogEntry): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(private var binding: BlogRvItemBinding,
                     private val onClickListener: BlogItemOnClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BlogEntry) {
            binding.blogEntry = item
            binding.root.setOnClickListener { onClickListener.onItemClicked(item) }
            binding.executePendingBindings()
        }

    }
}