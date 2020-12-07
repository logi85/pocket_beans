package de.seriousdonkey.pocketbeans.schedule.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.work.*
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.databinding.FragmentScheduleDetailsBinding
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleEntry
import de.seriousdonkey.pocketbeans.schedule.worker.NotificationWorker
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleDetailsFragment : DaggerFragment() {

    private lateinit var _binding: FragmentScheduleDetailsBinding

    private val _args: ScheduleDetailsFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var workManager: WorkManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_details, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.lifecycleOwner = this
        _binding.executePendingBindings()

        val entry = _args.scheduleEntry
        _binding.scheduleEntry = entry

        _binding.scheduleDetailsReminderSwitch.setOnClickListener {
            if (_binding.scheduleDetailsReminderSwitch.isChecked) {
                addNotification(entry)
            } else {
                cancelNotification(entry)
            }
        }

        val workList = workManager.getWorkInfosByTag(entry.id.toString()).get()
        if (workList.size > 0) {
            val work = workList.last()
            if (work.state == WorkInfo.State.ENQUEUED) {
                _binding.scheduleDetailsReminderSwitch.isChecked = true
            } else if (work.state == WorkInfo.State.CANCELLED) {
                _binding.scheduleDetailsReminderSwitch.isChecked = false
            }
        }

        _binding.scheduleDetailsShowButton.setOnClickListener {
            val action = ScheduleDetailsFragmentDirections.openShowInfo(entry.showId)
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun addNotification(entry: ScheduleEntry) {
        val now = DateTime.now()
        val reminderDate = entry.timeStart.minusMinutes(5)
        val duration = Duration(now, reminderDate)
        val minutes = duration.standardMinutes
        val notificationWorkRequest: WorkRequest = OneTimeWorkRequest
                .Builder(NotificationWorker::class.java)
                .addTag(entry.id.toString())
                .setInputData(workDataOf(
                        NotificationWorker.ID to entry.id,
                        NotificationWorker.TITLE to entry.title,
                        NotificationWorker.CONTENT to entry.topic
                ))
                .setInitialDelay(minutes, TimeUnit.MINUTES)
                .build()
        workManager.enqueue(notificationWorkRequest).state.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, resources.getString(R.string.schedule_details_notification_added),
                    Toast.LENGTH_SHORT).show()
        })
    }

    private fun cancelNotification(entry: ScheduleEntry) {
        workManager.cancelAllWorkByTag(entry.id.toString()).state.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, resources.getString(R.string.schedule_details_notification_deleted),
                    Toast.LENGTH_SHORT).show()
        })
    }

}