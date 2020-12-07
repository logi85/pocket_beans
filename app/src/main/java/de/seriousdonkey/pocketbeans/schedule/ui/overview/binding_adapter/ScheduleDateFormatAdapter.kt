package de.seriousdonkey.pocketbeans.schedule.ui.overview.binding_adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

class ScheduleDateFormatAdapter {
    companion object {
        private val DAY_FORMATTER = DateTimeFormat.forPattern("EEEE")
                .withLocale(Locale.GERMAN)
                .withZone(DateTimeZone.forID("Europe/Berlin"))
        private val DATE_FORMATTER = DateTimeFormat.forPattern("dd. MMMM yyyy")
                .withLocale(Locale.GERMAN)
                .withZone(DateTimeZone.forID("Europe/Berlin"))


        @BindingAdapter("formattedDay")
        @JvmStatic
        fun dayAsStringFormatterBinding(textView: TextView, date: DateTime) {
            when {
                isToday(date) -> textView.text = "Heute"
                isTomorrow(date) -> textView.text = "Morgen"
                else -> textView.text = DAY_FORMATTER.print(date)
            }
        }

        @BindingAdapter("formattedDate")
        @JvmStatic
        fun dateFormatterBinding(textView: TextView, date: DateTime) {
            textView.text = DATE_FORMATTER.print(date)
        }

        private fun isToday(date: DateTime) : Boolean {
            val now = DateTime.now().toLocalDateTime()
            val localizedDate = date.plusDays(1).toLocalDateTime()
            return localizedDate.year == now.year && localizedDate.monthOfYear == now.monthOfYear
                    && localizedDate.dayOfMonth == now.dayOfMonth
        }

        private fun isTomorrow(date: DateTime): Boolean {
            val tomorrow = DateTime.now().plusDays(1).toLocalDateTime()
            val localizedDate = date.plusDays(1).toLocalDateTime()
            return localizedDate.year == tomorrow.year
                    && localizedDate.monthOfYear == tomorrow.monthOfYear
                    && localizedDate.dayOfMonth == tomorrow.dayOfMonth
        }
    }
}