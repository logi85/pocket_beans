package de.seriousdonkey.pocketbeans.media_library.ui.show.info.models

data class ShowInfo(
    val id: Int,
    val title: String,
    val description: String,
    val thumbImage: String,
    val seasons: List<ShowInfoSeason>
)