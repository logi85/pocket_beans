package de.seriousdonkey.pocketbeans.blog.ui

import de.seriousdonkey.pocketbeans.blog.api.BlogApiService
import javax.inject.Inject

class BlogListDataSourceFactory @Inject constructor(private val _blogApiService: BlogApiService) {

    fun create() : BlogDataSource {
        return BlogDataSource(_blogApiService)
    }

}