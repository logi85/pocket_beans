package de.seriousdonkey.pocketbeans.media_library.ui.show.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.databinding.FragmentShowOverviewBinding
import de.seriousdonkey.pocketbeans.media_library.ui.MediaLibraryFragmentDirections
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.models.ShowOverview
import javax.inject.Inject

class ShowOverviewFragment : DaggerFragment(), ShowOverviewItemClickListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var _binding: FragmentShowOverviewBinding
    private lateinit var _viewModel: ShowOverviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_overview, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ShowOverviewViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this
        _binding.executePendingBindings()

        _binding.showOverviewRv.layoutManager = GridLayoutManager(requireContext(), 3)

        val adapter = ShowOverviewAdapter(this)
        _viewModel.shows.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        _viewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            if (state == NetworkState.FAILED) {
                Toast.makeText(requireContext(), resources.getString(R.string.network_error), Toast.LENGTH_SHORT)
                        .show()
            }
        })

        _binding.showOverviewRv.adapter = adapter
    }

    override fun onClick(item: ShowOverview) {
        val action = MediaLibraryFragmentDirections.openShowInfo(item.id)
        Navigation.findNavController(requireView()).navigate(action)
    }

    companion object {
        fun newInstance() : ShowOverviewFragment {
            return ShowOverviewFragment()
        }
    }
}