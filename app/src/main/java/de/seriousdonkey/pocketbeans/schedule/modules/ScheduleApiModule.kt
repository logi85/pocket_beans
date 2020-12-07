package de.seriousdonkey.pocketbeans.schedule.modules

import dagger.Module
import dagger.Provides
import de.seriousdonkey.pocketbeans.schedule.api.ScheduleApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ScheduleApiModule {
    @Provides
    @Singleton
    fun providesScheduleApiService(retrofit: Retrofit) : ScheduleApiService {
        return retrofit.create(ScheduleApiService::class.java)
    }
}