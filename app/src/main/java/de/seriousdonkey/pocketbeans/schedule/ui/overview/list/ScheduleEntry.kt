package de.seriousdonkey.pocketbeans.schedule.ui.overview.list

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import java.io.Serializable
import java.util.*


enum class ScheduleType(val typeAsString: String) {
    LIVE("LIVE"),
    PREMIERE("NEU"),
    RERUN("WDH")
}

data class ScheduleEntry(val id: Int,
                         val title: String,
                         val topic: String,
                         val type: ScheduleType,
                         val timeStart: DateTime,
                         val timeEnd: DateTime,
                         val episodeImageUrl: String,
                         val showId: Int) : Serializable {

    fun getFormattedStartAndEndTime() : String {
        return "${TIME_FORMATTER.print(timeStart)} - ${TIME_FORMATTER.print(timeEnd)}"
    }

    fun getRemainingTimeInMinutes() : String {
        val duration = Duration(DateTime.now(), timeEnd)
        return "Noch ${duration.standardMinutes} Minuten"
    }

    fun isAlreadyStarted(minutes: Int) : Boolean {
        val now = DateTime.now().minusMinutes(minutes)
        if (timeStart.isAfter(now)) {
            return false
        }
        return true
    }

    companion object {
        private val TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm")
                .withLocale(Locale.GERMANY)
                .withZone(DateTimeZone.forID("Europe/Berlin"))
    }

}