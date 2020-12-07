package de.seriousdonkey.pocketbeans.app.modules

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobScheduler
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import de.seriousdonkey.pocketbeans.PocketBeansApplication
import de.seriousdonkey.pocketbeans.app.ApiDateTimeDeserializer
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun providesContext(application: PocketBeansApplication) : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
        val gson = GsonBuilder()
                .registerTypeAdapter(DateTime::class.java, ApiDateTimeDeserializer())
                .create()
        return Retrofit.Builder()
            .baseUrl("https://api.rocketbeans.tv/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesWorkManager(context: Context) : WorkManager {
        return WorkManager.getInstance(context)
    }

}