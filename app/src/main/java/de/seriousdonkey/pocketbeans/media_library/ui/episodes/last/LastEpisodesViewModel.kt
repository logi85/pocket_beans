package de.seriousdonkey.pocketbeans.media_library.ui.episodes.last

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class LastEpisodesViewModel @Inject constructor(lastEpisodesFactory: LastEpisodesFactory) : ViewModel() {

    val networkState: LiveData<NetworkState>
    val lastEpisodes: LiveData<PagedList<Episode>>

    private val executor: Executor

    init {
        executor = Executors.newFixedThreadPool(8)
        networkState = Transformations.switchMap(lastEpisodesFactory.mutableLiveData) {dataSource -> dataSource.networkState}
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10).build()
        lastEpisodes = (LivePagedListBuilder(lastEpisodesFactory, pagedListConfig))
            .setFetchExecutor(executor).build();
    }

}