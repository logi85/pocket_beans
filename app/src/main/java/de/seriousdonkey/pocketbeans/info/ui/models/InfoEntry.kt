package de.seriousdonkey.pocketbeans.info.ui.models

data class InfoEntry(
    val title: String,
    val content: String,
    val hasExternalPage: Boolean,
    val externalPage: String,
    val externalPageType: ExternalPageType
)

enum class ExternalPageType {
    WEBSITE,
    EMAIL,
    NONE
}