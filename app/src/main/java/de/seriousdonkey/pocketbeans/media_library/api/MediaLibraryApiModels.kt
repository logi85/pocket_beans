package de.seriousdonkey.pocketbeans.media_library.api

data class MediaLibraryApiSingleResponse(
        val success: Boolean,
        val data: MediaLibraryApiData
)

data class MediaLibraryApiData(
        val id: Int,
        val title: String,
        val description: String,
        val genre: String?
)