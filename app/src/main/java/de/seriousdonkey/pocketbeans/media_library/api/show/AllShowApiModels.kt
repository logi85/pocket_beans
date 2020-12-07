package de.seriousdonkey.pocketbeans.media_library.api.show

data class AllShowPreviewResponse(
    val success: Boolean,
    val pagination: AllShowPreviewPagination,
    val data: List<AllShowPreviewData>
)

data class AllShowPreviewPagination(
    val offset: Int,
    val limit: Int,
    val total: Int
)

data class AllShowPreviewData(
    val id: Int,
    val title: String,
    val thumbnail: List<AllShowPreviewImage>
)

data class AllShowPreviewImage(
    val width: Int,
    val height: Int,
    val name: String,
    val url: String
)