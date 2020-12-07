package de.seriousdonkey.pocketbeans.media_library.ui.show.info

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.ui.show.info.models.ShowInfo
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import de.seriousdonkey.pocketbeans.app.extensions.plusAssign
import de.seriousdonkey.pocketbeans.media_library.api.show.SingleShowResponse
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.overview.EpisodeListFactory
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.models.Episode
import de.seriousdonkey.pocketbeans.media_library.ui.show.info.models.ShowInfoSeason
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ShowInfoViewModel @Inject constructor(private val _apiService: MediaLibraryApiService) : ViewModel() {

    val showInfo = MutableLiveData<ShowInfo>()

    val showId = MutableLiveData<Int>()
    val episodes: LiveData<PagedList<Episode>>

    private val _compositeDisposable = CompositeDisposable()
    private val executor: Executor

    init {
        executor = Executors.newFixedThreadPool(5)
        episodes = Transformations.switchMap(showId) { showId ->
            val factory = EpisodeListFactory(_apiService, showId)

            val pagedListConfig = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10).build()
            return@switchMap (LivePagedListBuilder(factory, pagedListConfig))
                    .setFetchExecutor(executor)
                    .build()
        }
    }

    fun loadShowInfo(id: Int) {
        _compositeDisposable += _apiService.getSingleShow(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SingleShowResponse>() {

                    override fun onNext(response: SingleShowResponse) {

                        var thumbnailUrl = ""
                        response.data.thumbnail.forEach {
                            if (it.name == "large") {
                                thumbnailUrl = it.url
                            }
                        }

                        val seasons = mutableListOf<ShowInfoSeason>()
                        response.data.seasons.forEachIndexed { index, season ->
                            var name = season.name
                            if (name == null) {
                                name = "Staffel ${index + 1}"
                            }
                            seasons.add(ShowInfoSeason(season.id, season.numeric, name))
                        }

                        val show = ShowInfo(response.data.id, response.data.title, response.data.description, thumbnailUrl, seasons)
                        showInfo.postValue(show)
                        showId.postValue(show.id)
                    }

                    override fun onError(e: Throwable) {
                        print(e.message)
                    }

                    override fun onComplete() {

                    }

                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!_compositeDisposable.isDisposed) {
            _compositeDisposable.dispose()
        }
    }
}