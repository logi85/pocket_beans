package de.seriousdonkey.pocketbeans.schedule.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("schedule/normalized")
    fun getNormalizedSchedule(@Query("startDay") startDay: Long) : Observable<ScheduleResponse>
}