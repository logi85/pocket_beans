package de.seriousdonkey.pocketbeans.media_library.ui.show.overview

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.api.show.AllShowPreviewData
import de.seriousdonkey.pocketbeans.media_library.api.show.AllShowPreviewResponse
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.models.ShowOverview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowOverviewDataSource constructor(private val _apiService: MediaLibraryApiService) : PageKeyedDataSource<Int, ShowOverview>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        networkState.postValue(NetworkState.LOADING)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ShowOverview>) {
        _apiService.getAllShows(0, params.requestedLoadSize).enqueue(object : Callback<AllShowPreviewResponse> {
            override fun onResponse(call: Call<AllShowPreviewResponse>, response: Response<AllShowPreviewResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    networkState.postValue(NetworkState.LOADED)
                    val data = response.body()!!.data
                    val allShows = buildShowOverview(data)
                    callback.onResult(allShows, null, 10)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<AllShowPreviewResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShowOverview>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShowOverview>) {
       _apiService.getAllShows(params.key, params.requestedLoadSize).enqueue(object : Callback<AllShowPreviewResponse> {

           override fun onResponse(call: Call<AllShowPreviewResponse>, response: Response<AllShowPreviewResponse>) {
               if (response.isSuccessful && response.body() != null) {
                   networkState.postValue(NetworkState.LOADED)
                   val data = response.body()!!.data
                   val allShows = buildShowOverview(data)
                   callback.onResult(allShows, params.key + params.requestedLoadSize)
               }
           }

           override fun onFailure(call: Call<AllShowPreviewResponse>, t: Throwable) {
               networkState.postValue(NetworkState.FAILED)
           }

       })
    }

    private fun buildShowOverview(allShowPreviewData: List<AllShowPreviewData>) : List<ShowOverview> {
        val models = arrayListOf<ShowOverview>()

        allShowPreviewData.forEach {
            var imageUrl = ""
            it.thumbnail.forEach { img ->
                if (img.name == "medium") {
                    imageUrl = img.url
                }
            }
            models.add(ShowOverview(it.id, it.title, imageUrl))
        }

        return models
    }
}