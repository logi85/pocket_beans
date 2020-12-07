package de.seriousdonkey.pocketbeans.info.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.databinding.FragmentInfoBinding
import de.seriousdonkey.pocketbeans.info.ui.models.ExternalPageType
import de.seriousdonkey.pocketbeans.info.ui.models.InfoEntry
import de.seriousdonkey.pocketbeans.info.ui.viewmodel.InfoListViewModel
import javax.inject.Inject

class InfoFragment : DaggerFragment(), OnInfoItemEntryClickedCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var _binding: FragmentInfoBinding
    private lateinit var _viewModel: InfoListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(InfoListViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.executePendingBindings()

        val adapter = InfoListViewAdapter(mutableListOf(), this)
        _viewModel.infoEntries.observe(viewLifecycleOwner, Observer { adapter.updateInfoEntries(it) })
        _binding.infoListRv.adapter = adapter

        _viewModel.loadInfoEntries()
    }

    override fun onItemClicked(entry: InfoEntry) {
        if (entry.externalPageType == ExternalPageType.EMAIL) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(entry.externalPage)
            }
            startActivity(intent)
        } else if (entry.externalPageType == ExternalPageType.WEBSITE) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(entry.externalPage)
            }
            startActivity(intent)
        }
    }

}