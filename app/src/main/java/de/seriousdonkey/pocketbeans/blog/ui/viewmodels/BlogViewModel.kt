package de.seriousdonkey.pocketbeans.blog.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.blog.ui.BlogDataSource
import de.seriousdonkey.pocketbeans.blog.ui.BlogListFactory
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class BlogViewModel @Inject constructor(blogListFactory: BlogListFactory) : ViewModel() {

    private val executor: Executor
    val networkState: LiveData<NetworkState>
    val blogEntries: LiveData<PagedList<BlogEntry>>

    init {
        executor = Executors.newFixedThreadPool(5)
        networkState = Transformations.switchMap(blogListFactory.mutableLiveData) { dataSource -> dataSource.networkState }
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10).build()
        blogEntries = (LivePagedListBuilder(blogListFactory, pagedListConfig))
                .setFetchExecutor(executor).build();
    }


}