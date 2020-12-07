package de.seriousdonkey.pocketbeans.blog.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApiService {
    @GET("blog/preview/all")
    fun getBlogPreviews(@Query("offset") offset: Int, @Query("limit") limit: Int) : Call<BlogResponse>
}