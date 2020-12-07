package de.seriousdonkey.pocketbeans.schedule.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.work.*
import de.seriousdonkey.pocketbeans.R
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationWorker(private val _context: Context, private val _params: WorkerParameters) : Worker(_context, _params) {

    override fun doWork(): Result {

        val id = inputData.getInt(ID, 0)
        val title = inputData.getString(TITLE)
        val topic = inputData.getString(CONTENT)
        if (title == null || topic == null) {
            return Result.failure()
        }

        val builder = NotificationCompat.Builder(_context, "pocketbeans")
            .setSmallIcon(R.drawable.baseline_calendar_today_24)
            .setContentTitle(title)
            .setContentText(topic)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(_context)) {
            notify(id, builder.build())
        }
        return Result.success()
    }

    companion object {
        const val ID = "ID"
        const val TITLE = "TITLE"
        const val CONTENT = "TOPIC"
    }
}