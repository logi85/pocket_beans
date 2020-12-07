package de.seriousdonkey.pocketbeans.media_library.ui.episodes.last

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import javax.inject.Inject

class LastEpisodesFactory @Inject constructor(private val _lastEpisodesDataSource: LastEpisodesDataSource)
    : DataSource.Factory<Int, Episode>() {

    val mutableLiveData = MutableLiveData<LastEpisodesDataSource>()

    override fun create(): DataSource<Int, Episode> {
        mutableLiveData.postValue(_lastEpisodesDataSource)
        return _lastEpisodesDataSource
    }
}