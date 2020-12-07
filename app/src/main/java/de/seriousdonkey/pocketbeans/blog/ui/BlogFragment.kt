package de.seriousdonkey.pocketbeans.blog.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var _binding: FragmentBlogBinding
    private lateinit var _viewModel: BlogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BlogViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.executePendingBindings()

        val adapter = BlogListAdapter(this)
        _viewModel.blogEntries.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        _viewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            _binding.isLoading = state == NetworkState.LOADING
            if (state == NetworkState.FAILED) {
                Toast.makeText(this.context, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
        _binding.blogPreviewsRv.adapter = adapter
    }

    override fun onItemClicked(entry: BlogEntry) {
        val url = ROCKET_BEANS_API_URL + entry.id
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    companion object {
        private const val ROCKET_BEANS_API_URL = "https://rocketbeans.tv/blog/"
    }

}