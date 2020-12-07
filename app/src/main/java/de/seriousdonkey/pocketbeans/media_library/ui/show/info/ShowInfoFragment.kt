package de.seriousdonkey.pocketbeans.media_library.ui.show.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.databinding.FragmentShowInfoBinding
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview.EpisodeListAdapter
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview.EpisodeListOnClickListener
import javax.inject.Inject

class ShowInfoFragment : DaggerFragment(), EpisodeListOnClickListener {

    private val _youtubeUrl = "https://www.youtube.com/watch?v="

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var _binding: FragmentShowInfoBinding
    private lateinit var _viewModel: ShowInfoViewModel
    private val _args: ShowInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_info, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ShowInfoViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this
        _binding.executePendingBindings()

        _binding.episodeRv.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = EpisodeListAdapter(this)
        _viewModel.episodes.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        _binding.episodeRv.adapter = adapter


        _viewModel.loadShowInfo(_args.showId)
    }

    override fun onClick(episode: Episode) {
        if (episode.youtubeToken != null) {
            val url = _youtubeUrl + episode.youtubeToken
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

}