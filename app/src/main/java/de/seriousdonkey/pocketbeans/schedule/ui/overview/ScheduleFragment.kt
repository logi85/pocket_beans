package de.seriousdonkey.pocketbeans.schedule.ui.overview

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
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.databinding.FragmentScheduleBinding
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleEntry
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleItemOnClickListener
import de.seriousdonkey.pocketbeans.schedule.ui.overview.viewmodels.ScheduleViewModel
import javax.inject.Inject

class ScheduleFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var _binding: FragmentScheduleBinding
    private lateinit var _viewModel: ScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ScheduleViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this
        _binding.executePendingBindings()

        _viewModel.clickEvent.observe(viewLifecycleOwner, Observer { openScheduleDetails(it) })

        _viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        _viewModel.loadNormalizedSchedule()
    }

    private fun openScheduleDetails(item: ScheduleEntry?) {
        if (item == null) {
            return
        }
        val action = ScheduleFragmentDirections.openScheduleDetails(item)
        Navigation.findNavController(this.requireView()).navigate(action)
    }

}