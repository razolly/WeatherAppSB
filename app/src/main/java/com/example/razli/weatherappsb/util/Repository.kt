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

//    fun fetchWeatherData(placeName: String): Place {
//
//        var place: Place? = null
//
//        val call: Call<Place> = networkApi.getPlaceWeather(placeName)
//
//        call.enqueue(object : Callback<Place> {
//
//            override fun onFailure(call: Call<Place>?, t: Throwable?) {
//                println(t?.message)
//            }
//
//            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
//
//                if (response != null && response.isSuccessful && response.body() != null) {
//
//                    //val place: Place = response.body()!!
//                    place = response.body() //as Place
//                    println("The place is " + place)
//
//                    // Set the date of "last updated"
//                    val currentTime = LocalDateTime.now()
//                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//                    val lastUpdated = currentTime.format(formatter)
//                    //place.lastUpdated = lastUpdated0
//
//                    val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//                    val currentDate = simpleDateFormat.format(Date())
//                    println("Current date: " + currentDate)
//
//                    println(place.toString())
//                }
//            }
//        })
//
//        return place!!
//    }

}