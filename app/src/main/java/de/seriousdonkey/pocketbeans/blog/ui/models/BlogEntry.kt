package de.seriousdonkey.pocketbeans.blog.ui.models

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

data class BlogEntry(
        val id: Int,
        val title: String,
        val subtitle: String,
        val thumbImage: String,
        val isSponsored: Boolean,
        val publishDate: DateTime) {

    fun formattedDate() : String {
        return FORMATTER.print(publishDate)
    }

    fun isSponsoredText() : String {
        if (isSponsored) {
            return "Beitrag enthält Werbung"
        }
        return ""
    }

    companion object {
        private val FORMATTER = DateTimeFormat.forPattern("dd MMM yyyy")
                .withLocale(Locale.GERMAN)
                .withZone(DateTimeZone.forID("Europe/Berlin"))
    }
}