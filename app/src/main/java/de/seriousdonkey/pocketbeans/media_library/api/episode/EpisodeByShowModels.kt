package de.seriousdonkey.pocketbeans.media_library.api.episode

import org.joda.time.DateTime

data class EpisodeByShowResponse(
        val success: Boolean,
        val pagination: EpisodeByShowPagination,
        val data: EpisodeByShowData
)

data class EpisodeByShowPagination(
        val offset: Int,
        val limit: Int,
        val total: Int
)

data class EpisodeByShowData(
        val episodes: List<EpisodeByShowEpisode>
)

data class EpisodeByShowEpisode(
        val id: Int,
        val title: String,
        val description: String,
        val thumbnail: List<EpisodeByShowImage>,
        val firstBroadcastdate: DateTime,
        val youtubeTokens: List<String>
)

data class EpisodeByShowImage(
        val name: String,
        val height: Int,
        val width: Int,
        val url: String
)