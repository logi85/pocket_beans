package de.seriousdonkey.pocketbeans.blog.modules

import dagger.Module
import dagger.Provides
import de.seriousdonkey.pocketbeans.blog.api.BlogApiService
import de.seriousdonkey.pocketbeans.blog.ui.BlogDataSource
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class BlogApiModule {
    @Provides
    @Singleton
    fun provideBlogApiService(retrofit: Retrofit) : BlogApiService {
        return retrofit.create(BlogApiService::class.java)
    }
}