package de.seriousdonkey.pocketbeans.schedule.services

import de.seriousdonkey.pocketbeans.schedule.api.ScheduleApiService
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleEntry
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleHeader
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.ScheduleType
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class ScheduleService @Inject constructor(private val _scheduleApiService: ScheduleApiService) {

    fun getNormalizedSchedule() : Observable<TreeMap<ScheduleHeader, List<ScheduleEntry>>> {
        val startDay = System.currentTimeMillis() / 1000L
        return _scheduleApiService.getNormalizedSchedule(startDay)
                .map {
                    val treeMap: TreeMap<ScheduleHeader, List<ScheduleEntry>> = TreeMap()
                    for (data in it.data) {
                        val header = ScheduleHeader(data.date)
                        val entries = mutableListOf<ScheduleEntry>()

                        for (entry in data.elements) {
                            val type: ScheduleType = when(entry.type) {
                                "live" -> ScheduleType.LIVE
                                "premiere" -> ScheduleType.PREMIERE
                                else -> ScheduleType.RERUN
                            }

                            var episodeImageUrl = ""
                            val episodeImage = entry.episodeImages.find { img -> img.name == "small" }
                            if (episodeImage != null) {
                                episodeImageUrl = episodeImage.url
                            }

                            entries.add(ScheduleEntry(entry.id, entry.title, entry.topic, type,
                                    entry.timeStart, entry.timeEnd, episodeImageUrl, entry.showId))
                        }

                        treeMap[header] = entries
                    }

                    return@map treeMap
                }
    }
}