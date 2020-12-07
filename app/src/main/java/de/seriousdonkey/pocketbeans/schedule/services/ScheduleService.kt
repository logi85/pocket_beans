package de.seriousdonkey.pocketbeans.schedule.services

import de.seriousdonkey.pocketbeans.schedule.api.ScheduleApiService
import de.seriousdonkey.pocketbeans.schedule.ui.list.ScheduleEntry
import de.seriousdonkey.pocketbeans.schedule.ui.list.ScheduleHeader
import io.reactivex.Observable
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class ScheduleService @Inject constructor(private val _scheduleApiService: ScheduleApiService) {

    fun getNormalizedSchedule() : Observable<TreeMap<ScheduleHeader, List<ScheduleEntry>>> {
        return _scheduleApiService.getNormalizedSchedule(1607077681L)
                .map {
                    val treeMap: TreeMap<ScheduleHeader, List<ScheduleEntry>> = TreeMap()
                    for (data in it.data) {
                        val header = ScheduleHeader(data.date)
                        val entries = mutableListOf<ScheduleEntry>()

                        for (entry in data.elements) {
                            entries.add(ScheduleEntry(entry.id, entry.title, entry.topic))
                        }

                        treeMap[header] = entries
                    }

                    return@map treeMap
                }
    }
}