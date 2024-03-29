package com.example.weatherapp.model

import android.content.Context
import com.example.weatherapp.CONST
import com.example.weatherapp.model.RetrofitHelper.retrofit
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL= CONST.BASE_URL
interface SimpleService{

    @GET(CONST.onecall)
    suspend fun getCurrentWeather(
        @Query(CONST.lat) lat: String?,
        @Query(CONST.lon) lon: String?,
        @Query("units") units: String?,
        @Query(CONST.lang) lang: String?,
        @Query(CONST.exclude) exclude: String="minutely",
        @Query(CONST.appid) appId: String=CONST.API_KEY,
    ):Welcome

}
object RetrofitHelper {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}
object API {
    val retrofitService: SimpleService by lazy {
        retrofit.create(SimpleService::class.java)
    }
}


