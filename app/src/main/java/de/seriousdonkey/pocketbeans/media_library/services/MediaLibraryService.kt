package de.seriousdonkey.pocketbeans.media_library.services

import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import de.seriousdonkey.pocketbeans.media_library.models.MediaShow
import io.reactivex.Observable
import javax.inject.Inject

class MediaLibraryService @Inject constructor(private val _mediaLibraryApiService: MediaLibraryApiService) {

    fun getShowInfo(showId: Int) : Observable<MediaShow> {
        return _mediaLibraryApiService.getSingleShowOld(showId)
                .map { response ->
                    val data = response.data
                    return@map MediaShow(data.id, data.title, data.description, data.genre)
                }
    }

}