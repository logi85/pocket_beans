package de.seriousdonkey.pocketbeans.media_library.api.show

data class SingleShowResponse(
        val success: Boolean,
        val data: SingleShowData
)

data class SingleShowData(
        val id: Int,
        val title: String,
        val description: String,
        val thumbnail: List<SingleShowThumbImage>,
        val seasons: List<SingleShowSeason>
)

data class SingleShowThumbImage(
        val width: Int,
        val height: Int,
        val name: String,
        val url: String
)

data class SingleShowSeason(
        val id: Int,
        val numeric: Int,
        val name: String?
)