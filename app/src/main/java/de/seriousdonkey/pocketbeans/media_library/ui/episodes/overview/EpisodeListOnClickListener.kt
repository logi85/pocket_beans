package de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview

import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode

interface EpisodeListOnClickListener {
    fun onClick(episode: Episode)
}