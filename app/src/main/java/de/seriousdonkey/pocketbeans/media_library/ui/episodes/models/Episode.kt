package de.seriousdonkey.pocketbeans.media_library.ui.episodes.models

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

data class Episode(
        val id: Int,
        val title: String,
        val thumbnail: String,
        val date: DateTime,
        val youtubeToken: String?
) {
        fun formattedDate() = FORMATTER.print(date)

        companion object {
                private val FORMATTER = DateTimeFormat.forPattern("dd MMM yyyy")
                        .withLocale(Locale.GERMAN)
                        .withZone(DateTimeZone.forID("Europe/Berlin"))
        }
}