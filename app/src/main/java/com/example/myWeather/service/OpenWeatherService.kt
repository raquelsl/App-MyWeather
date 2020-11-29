package com.example.myWeather.service

import com.example.myWeather.model.City
import com.example.myWeather.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    
    private val apikey: String
        get() = "e4f24fd142c25cde57f5d6a8a6bc11bd"

    @GET("weather")
    fun getCityWeather(
            @Query("q") cityName: String,
            @Query("ApiKey") apiKey: String = apikey
    ) : Call<City>
    
    @GET("find")
    fun getTemperatures(
            @Query("q") cityName: String,
            @Query("units") units: String =  "metrics",
            @Query("ApiKey") apiKey: String = apikey
    ) : Call<Root>
}