package de.seriousdonkey.pocketbeans.blog.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import de.seriousdonkey.pocketbeans.blog.ui.viewmodels.BlogViewModel
import de.seriousdonkey.pocketbeans.databinding.FragmentBlogBinding
import javax.inject.Inject


class BlogFragment : DaggerFragment(), BlogItemOnClickListener {

    private lateinit var _binding: FragmentBlogBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BlogViewModel::class.java)
        _binding.viewModel = viewModel;
        _binding.executePendingBindings()

        _binding.blogPreviewsRv.layoutManager = LinearLayoutManager(this.context)

        val adapter = BlogListAdapter(this)
        viewModel.blogEntries.observe(this, Observer { pagedList -> adapter.submitList(pagedList) })
        viewModel.networkState.observe(this, Observer { state ->
            _binding.isLoading = state == NetworkState.LOADING
            if (state == NetworkState.FAILED) {
                Toast.makeText(this.context, "Netzwerkfehler", Toast.LENGTH_LONG)
                    .show()
            }
        })

        _binding.blogPreviewsRv.adapter = adapter


        return _binding.root
    }

    override fun onItemClicked(entry: BlogEntry) {
        val url = "https://rocketbeans.tv/blog/${entry.id}"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}