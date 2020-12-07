package de.seriousdonkey.pocketbeans.media_library.ui.show.overview

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.models.ShowOverview
import javax.inject.Inject

class ShowOverviewListFactory @Inject constructor(private val _apiService: MediaLibraryApiService) : DataSource.Factory<Int, ShowOverview>() {

    val mutableData = MutableLiveData<ShowOverviewDataSource>()

    override fun create(): DataSource<Int, ShowOverview> {
        val dataSource = ShowOverviewDataSource(_apiService)
        mutableData.postValue(dataSource)
        return dataSource
    }
}