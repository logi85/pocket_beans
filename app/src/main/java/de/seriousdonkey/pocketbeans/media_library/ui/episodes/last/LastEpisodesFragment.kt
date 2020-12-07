package de.seriousdonkey.pocketbeans.media_library.ui.episodes.last

import android.content.Context
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
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.databinding.FragmentLastEpisodesBinding
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview.EpisodeListAdapter
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview.EpisodeListOnClickListener
import javax.inject.Inject

class LastEpisodesFragment : DaggerFragment(), EpisodeListOnClickListener {

    lateinit var binding: FragmentLastEpisodesBinding

    lateinit var viewModel: LastEpisodesViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter = EpisodeListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_last_episodes, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LastEpisodesViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            binding.isLoading = state == NetworkState.LOADING
            if (state == NetworkState.FAILED) {
                Toast.makeText(requireContext(), "Fehler beim Laden der Daten", Toast.LENGTH_LONG)
                        .show()
            }
        })

        binding.lastEpisodesRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.lastEpisodesRv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.lastEpisodes.observe(viewLifecycleOwner, Observer { pagedList ->
            adapter.submitList(pagedList)
            adapter.notifyItemRangeChanged(0, pagedList.size)
        })
    }

    override fun onClick(episode: Episode) {
        if (episode.youtubeToken != null) {
            val url = "https://www.youtube.com/watch?v=${episode.youtubeToken}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() : LastEpisodesFragment {
            return LastEpisodesFragment()
        }
    }

}