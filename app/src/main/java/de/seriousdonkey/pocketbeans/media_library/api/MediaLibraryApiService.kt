package de.seriousdonkey.pocketbeans.media_library.api

import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByLastPublishedResponse
import de.seriousdonkey.pocketbeans.media_library.api.episode.EpisodeByShowResponse
import de.seriousdonkey.pocketbeans.media_library.api.show.AllShowPreviewResponse
import de.seriousdonkey.pocketbeans.media_library.api.show.SingleShowResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaLibraryApiService {

    @GET("media/show/{id}")
    fun getSingleShowOld(@Path("id") id: Int) : Observable<MediaLibraryApiSingleResponse>

    @GET("media/episode/byshow/{id}?order=DESC")
    fun getEpisodesByShowId(@Path("id") id: Int, @Query("offset") offset: Int,
                            @Query("limit") limit: Int) : Call<EpisodeByShowResponse>

    @GET("media/show/preview/all")
    fun getAllShows(@Query("offset") offset: Int, @Query("limit") limit: Int) : Call<AllShowPreviewResponse>

    @GET("media/show/{id}")
    fun getSingleShow(@Path("id") id: Int) : Observable<SingleShowResponse>

    @GET("media/episode/preview/newest?order=ASC")
    fun getEpisodesByLastPublished(@Query("offset") offset: Int,
                                   @Query("limit") limit: Int) : Call<EpisodeByLastPublishedResponse>

}