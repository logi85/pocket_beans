package de.seriousdonkey.pocketbeans.schedule.api

import org.joda.time.DateTime

data class ScheduleResponse(val success: Boolean, val data: List<ScheduleData>)

data class ScheduleData(val date: DateTime, val elements: List<ScheduleElement>)

data class ScheduleElement(
        val id: Int,
        val title: String,
        val topic: String,
        val type: String,
        val timeStart: DateTime,
        val timeEnd: DateTime,
        val episodeImages: List<Image>,
        val showId: Int
)

data class Image(
        val name: String,
        val url: String,
        val width: Int,
        val height: Int
)