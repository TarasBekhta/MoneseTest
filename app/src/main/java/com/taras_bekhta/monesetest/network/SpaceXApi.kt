package com.taras_bekhta.monesetest.network


import com.taras_bekhta.monesetest.BuildConfig
import com.taras_bekhta.monesetest.model.Launch
import com.taras_bekhta.monesetest.model.Rocket
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXApi {
    @GET("${BuildConfig.API_URL}${BuildConfig.LAUNCHES}")
    fun getLaunches(): Observable<ArrayList<Launch>>

    @GET("${BuildConfig.API_URL}${BuildConfig.ROCKETS}{rocket_id}")
    fun getRocket(@Path("rocket_id") rocket_id: String): Observable<Rocket>
}