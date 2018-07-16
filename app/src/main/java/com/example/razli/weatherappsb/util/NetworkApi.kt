package com.example.razli.weatherappsb.util

import com.example.razli.weatherappsb.model.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {

    //http://api.openweathermap.org/data/2.5/weather?q=singapore&units=metric&appid=1bab5a3cc4e7423879bea7b2dea70edc

    @GET("/data/2.5/weather?units=metric&appid=1bab5a3cc4e7423879bea7b2dea70edc")
    fun getPlaceWeather(@Query("q") city: String): Call<Place>

    @GET("/data/2.5/forecast?&appid=1bab5a3cc4e7423879bea7b2dea70edc")
    fun getPlaceWeatherForecast(@Query("q") city: String): Call<List<Place>>

}