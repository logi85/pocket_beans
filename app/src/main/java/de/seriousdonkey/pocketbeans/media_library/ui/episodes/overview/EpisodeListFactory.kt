package de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode

class EpisodeListFactory constructor(private val _mediaLibraryApiService: MediaLibraryApiService,
                                     private val showId: Int) : DataSource.Factory<Int, Episode>() {

    val mutableDataSource = MutableLiveData<EpisodeListDataSource>()

    override fun create(): DataSource<Int, Episode> {
        val dataSource = EpisodeListDataSource(_mediaLibraryApiService, showId)
        mutableDataSource.postValue(dataSource)
        return dataSource
    }


}