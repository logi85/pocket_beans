package de.seriousdonkey.pocketbeans.media_library.api.episode

import org.joda.time.DateTime

data class EpisodeByLastPublishedResponse(
    val success: Boolean,
    val pagination: EpisodeByLastPublishedPagination,
    val data: EpisodeByLastPublishedData
)

data class EpisodeByLastPublishedPagination(
    val offset: Int,
    val limit: Int,
    val total: Int
)

data class EpisodeByLastPublishedData(
    val episodes: List<EpisodeByLastPublishedEpisode>
)

data class EpisodeByLastPublishedEpisode(
    val id: Int,
    val title: String,
    val showName: String,
    val thumbnail: List<EpisodeByLastPublishedImage>,
    val firstBroadcastdate: DateTime
)

data class EpisodeByLastPublishedImage(
    val name: String,
    val height: Int,
    val width: Int,
    val url: String
)