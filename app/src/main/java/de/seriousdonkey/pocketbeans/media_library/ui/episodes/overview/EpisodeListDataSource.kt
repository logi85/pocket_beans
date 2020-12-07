package de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByShowData
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByShowResponse
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeListDataSource(private val _apiService: MediaLibraryApiService,
                            private val showId: Int) : PageKeyedDataSource<Int, Episode>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        networkState.postValue(NetworkState.LOADING)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Episode>) {
        _apiService.getEpisodesByShowId(showId, 0, params.requestedLoadSize).enqueue(object : Callback<EpisodeByShowResponse> {
            override fun onResponse(call: Call<EpisodeByShowResponse>, response: Response<EpisodeByShowResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    networkState.postValue(NetworkState.LOADED)
                    val episodes = _buildEpisodeList(response.body()!!.data)
                    callback.onResult(episodes, null, 10)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<EpisodeByShowResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        _apiService.getEpisodesByShowId(showId, params.key, params.requestedLoadSize).enqueue(object : Callback<EpisodeByShowResponse> {
            override fun onResponse(call: Call<EpisodeByShowResponse>, response: Response<EpisodeByShowResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    networkState.postValue(NetworkState.LOADED)
                    val episodes = _buildEpisodeList(response.body()!!.data)
                    callback.onResult(episodes, params.key + params.requestedLoadSize)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<EpisodeByShowResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

        })
    }

    private fun _buildEpisodeList(remoteEpisodes: EpisodeByShowData) : List<Episode> {
        val episodes = arrayListOf<Episode>()

        remoteEpisodes.episodes.forEach { episode ->
            var thumbnail = ""
            episode.thumbnail.forEach { img ->
                if (img.name == "small") {
                    thumbnail = img.url
                }
            }

            var youtubeToken: String? = null;
            if (episode.youtubeTokens.size > 0) {
                youtubeToken = episode.youtubeTokens[0]
            }

            episodes.add(Episode(episode.id, episode.title, thumbnail, episode.firstBroadcastdate,
                    youtubeToken))
        }
        return episodes
    }
}