package com.example.razli.weatherappsb.util

import com.example.razli.weatherappsb.model.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1")
    fun getPlaceWeather(): Call<Place>

    @GET("/data/2.5/weather?q={city}&appid=b1b15e88fa797225412429c1c50c122a1")
    fun getPlaceWeather(@Path("city") city: String): Call<Place>

}