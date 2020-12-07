package de.seriousdonkey.pocketbeans.blog.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry
import javax.inject.Inject

class BlogListFactory @Inject constructor(private val _blogListDataSourceFactory: BlogListDataSourceFactory) : DataSource.Factory<Int, BlogEntry>() {

    val mutableLiveData = MutableLiveData<BlogDataSource>()

    override fun create(): DataSource<Int, BlogEntry> {
        val dataSource = _blogListDataSourceFactory.create()
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}