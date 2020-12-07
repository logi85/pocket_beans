package de.seriousdonkey.pocketbeans.media_library.ui.show.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.models.ShowOverview
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class ShowOverviewViewModel @Inject constructor(private val showOverviewListFactory: ShowOverviewListFactory) : ViewModel() {

    private val executor: Executor
    val networkState: LiveData<NetworkState>
    val shows: LiveData<PagedList<ShowOverview>>

    init {
        executor = Executors.newFixedThreadPool(5)
        networkState = Transformations.switchMap(showOverviewListFactory.mutableData) { dataSource ->
            dataSource.networkState
        }
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        shows = (LivePagedListBuilder(showOverviewListFactory, pagedListConfig))
            .setFetchExecutor(executor).build()
    }

}