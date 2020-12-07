package de.seriousdonkey.pocketbeans.info.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import de.seriousdonkey.pocketbeans.info.services.InfoService
import javax.inject.Singleton

@Module
class InfoModule {

    @Provides
    @Singleton
    fun provideInfoService(context: Context) : InfoService {
        return InfoService(context)
    }

}