package de.seriousdonkey.pocketbeans.blog.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import javax.inject.Inject

class BlogListFactory @Inject constructor(private val blogDataSource: BlogDataSource) : DataSource.Factory<Int, BlogEntry>() {

    val mutableLiveData = MutableLiveData<BlogDataSource>()

    override fun create(): DataSource<Int, BlogEntry> {
        mutableLiveData.postValue(blogDataSource)
        return blogDataSource
    }
}