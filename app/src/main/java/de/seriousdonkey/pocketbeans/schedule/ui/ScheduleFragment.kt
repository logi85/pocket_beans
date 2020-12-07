package de.seriousdonkey.pocketbeans.schedule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.databinding.FragmentScheduleBinding
import de.seriousdonkey.pocketbeans.schedule.ui.list.ScheduleAdapter
import de.seriousdonkey.pocketbeans.schedule.ui.viewmodels.ScheduleViewModel
import javax.inject.Inject

class ScheduleFragment : DaggerFragment() {

    private lateinit var _binding: FragmentScheduleBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)
        _binding.viewModel = viewModel
        _binding.executePendingBindings()

        _binding.scheduleRv.layoutManager = LinearLayoutManager(context)
        val adapter = ScheduleAdapter(mutableListOf())
        _binding.scheduleRv.adapter = adapter

        viewModel.scheduleListItems.observe(this, Observer {
            adapter.updateData(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        viewModel.loadNormalizedSchedule()

        return _binding.root
    }

}