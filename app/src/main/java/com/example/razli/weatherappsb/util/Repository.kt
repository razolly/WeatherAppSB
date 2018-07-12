package com.example.razli.weatherappsb.util

import com.example.razli.weatherappsb.model.Place
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Repository private constructor() {

    private val baseUrl: String = "http://api.openweathermap.org/"

    val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

    val networkApi = retrofit.create(NetworkApi::class.java)


    private object Holder {
        val INSTANCE = Repository()
    }

    companion object {

        val instance: Repository by lazy { Holder.INSTANCE }

    }

    fun getWeather(placeName: String, callback: Callback<Place>) {
        val call = networkApi.getPlaceWeather(placeName)
        call.enqueue(callback)
    }
}