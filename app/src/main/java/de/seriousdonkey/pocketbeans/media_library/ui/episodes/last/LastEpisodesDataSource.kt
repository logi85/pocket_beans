package de.seriousdonkey.pocketbeans.media_library.ui.episodes.last

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByLastPublishedData
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByLastPublishedEpisode
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByLastPublishedResponse
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByShowData
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LastEpisodesDataSource @Inject constructor(private val _apiService: MediaLibraryApiService)
    : PageKeyedDataSource<Int, Episode>() {

    val networkState = MutableLiveData<NetworkState>(NetworkState.LOADING)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Episode>) {
        _apiService.getEpisodesByLastPublished(0, params.requestedLoadSize)
            .enqueue(object : Callback<EpisodeByLastPublishedResponse> {

                override fun onResponse(call: Call<EpisodeByLastPublishedResponse>, response: Response<EpisodeByLastPublishedResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        networkState.postValue(NetworkState.LOADED)
                        val episodes = _buildEpisodeList(response.body()!!.data)
                        callback.onResult(episodes, null, 10)
                    } else {
                        networkState.postValue(NetworkState.FAILED)
                    }
                }

                override fun onFailure(call: Call<EpisodeByLastPublishedResponse>, t: Throwable) {
                    networkState.postValue(NetworkState.FAILED)
                }

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        _apiService.getEpisodesByLastPublished(params.key, params.requestedLoadSize)
            .enqueue(object : Callback<EpisodeByLastPublishedResponse> {
                override fun onResponse(call: Call<EpisodeByLastPublishedResponse>, response: Response<EpisodeByLastPublishedResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        networkState.postValue(NetworkState.LOADED)
                        val episodes = _buildEpisodeList(response.body()!!.data)
                        callback.onResult(episodes, params.key + params.requestedLoadSize)
                    } else {
                        networkState.postValue(NetworkState.FAILED)
                    }
                }

                override fun onFailure(call: Call<EpisodeByLastPublishedResponse>, t: Throwable) {
                    networkState.postValue(NetworkState.FAILED)
                }

            })
    }

    private fun _buildEpisodeList(remoteEpisodes: EpisodeByLastPublishedData) : List<Episode> {
        val episodes = arrayListOf<Episode>()

        remoteEpisodes.episodes.forEach { episode ->
            var youtubeToken: String? = null
            var thumbnail = ""
            episode.thumbnail.forEach { img ->
                if (img.name == "small") {
                    thumbnail = img.url
                }

                if (img.name == "ytsmall") {
                    youtubeToken = img.url
                            .replace("https://img.youtube.com/vi/", "")
                            .replace("/mqdefault.jpg", "")
                }
            }

            episodes.add(Episode(episode.id, episode.title, thumbnail, episode.firstBroadcastdate, youtubeToken))
        }
        return episodes
    }
}