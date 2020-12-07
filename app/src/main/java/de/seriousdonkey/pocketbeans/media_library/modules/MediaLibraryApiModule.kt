package de.seriousdonkey.pocketbeans.media_library.modules

import dagger.Module
import dagger.Provides
import de.seriousdonkey.pocketbeans.media_library.api.MediaLibraryApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MediaLibraryApiModule {

    @Provides
    @Singleton
    fun providesMediaLibraryApiService(retrofit: Retrofit) : MediaLibraryApiService {
        return retrofit.create(MediaLibraryApiService::class.java)
    }

}