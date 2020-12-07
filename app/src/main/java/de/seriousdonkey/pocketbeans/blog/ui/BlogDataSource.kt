package de.seriousdonkey.pocketbeans.blog.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.seriousdonkey.pocketbeans.app.NetworkState
import de.seriousdonkey.pocketbeans.blog.api.BlogApiService
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import javax.inject.Inject
import de.seriousdonkey.pocketbeans.blog.api.BlogResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BlogDataSource @Inject constructor(private val _blogApiService: BlogApiService) : PageKeyedDataSource<Int, BlogEntry>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        networkState.postValue(NetworkState.LOADING)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BlogEntry>) {
        _blogApiService.getBlogPreviews(0, params.requestedLoadSize).enqueue(object : Callback<BlogResponse> {
            override fun onResponse(call: Call<BlogResponse>, response: Response<BlogResponse>) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.LOADED)
                    val entries = buildBlogEntries(response)
                    callback.onResult(entries, null, 10)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<BlogResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BlogEntry>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BlogEntry>) {
        _blogApiService.getBlogPreviews(params.key, params.requestedLoadSize).enqueue(object : Callback<BlogResponse> {
            override fun onResponse(call: Call<BlogResponse>, response: Response<BlogResponse>) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.LOADED)
                    val entries = buildBlogEntries(response)
                    callback.onResult(entries, params.key + params.requestedLoadSize)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<BlogResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

        })
    }

    private fun buildBlogEntries(response: Response<BlogResponse>) : List<BlogEntry> {
        val blogResponse = response.body()
        val entries = arrayListOf<BlogEntry>()
        blogResponse?.data?.forEach { data ->
            var imageUrl = ""
            val thumbImage = data.thumbImage.findLast { image -> image.name == "source" }
            if (thumbImage != null) {
                imageUrl = thumbImage.url.replace("//", "https://")
            }
            entries.add(BlogEntry(data.id, data.title, data.subtitle, imageUrl, data.isSponsored, data.publishDate))
        }
        return entries
    }

}