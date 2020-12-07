package de.seriousdonkey.pocketbeans.media_library.api

data class MediaLibraryEpisodeApiEpisodeResponse(
        val success: Boolean,
        val pagination: MediaLibraryEpisodeApiPagination,
        val episodes: List<MediaLibraryEpisodeApiEpisode>
)

data class MediaLibraryEpisodeApiPagination(
        val offset: Int,
        val limit: Int,
        val total: Int
)

data class MediaLibraryEpisodeApiEpisode(
        val id: Int,
        val showId: Int,
        val showName: String,
        val title: String,
        val thumbnail: String
)