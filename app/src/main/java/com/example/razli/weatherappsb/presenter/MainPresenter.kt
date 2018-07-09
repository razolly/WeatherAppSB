package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.NetworkApi
import com.example.razli.weatherappsb.util.Repository
import com.example.razli.weatherappsb.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY = "favourite_place"

    private var favPlaceStrings: HashSet<String> = hashSetOf()
    private var sharedPreferences: SharedPreferences

    val repository = Repository.instance

    init {
        //view.showFavouritePlaces(favouritePlaces)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    }

    override fun start() {

        // Check if SharedPreferences exist
        if (sharedPreferences.contains(STRING_KEY)) {

            favPlaceStrings.addAll(sharedPreferences.getStringSet(STRING_KEY, hashSetOf("")))

            updateListOfPlaces()
        }
    }

    override fun addFavouritePlace(placeName: String) {

        favPlaceStrings.add(placeName)

        sharedPreferences.edit().putStringSet(STRING_KEY, favPlaceStrings).apply()

        Toast.makeText(context, placeName + " added!", Toast.LENGTH_SHORT).show()
    }

    override fun updateListOfPlaces() {

        var places = mutableListOf<Place>()

        for (index in 0 until favPlaceStrings.size) {
            //fetchJson(favPlaceStrings.elementAt(index))
            places.add(repository.fetchWeatherData(favPlaceStrings.elementAt(index)))
        }

        view.showFavouritePlaces(places)
    }

    // Argument is the name of a place. Details of place is fetched in Json and parsed
    // This method then adds a Place object to a List
//    fun fetchJson(placeName: String) {
//
//        val baseUrl = "http://api.openweathermap.org/"
//
//        val client = OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor()
//                        .setLevel(HttpLoggingInterceptor.Level.BODY))
//                .build()
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .client(client)
//                .build()
//
//        val networkApi = retrofit.create(NetworkApi::class.java)
//
//        val call: Call<Place> = networkApi.getPlaceWeather(placeName)
//
//        call.enqueue(object : Callback<Place> {
//            override fun onFailure(call: Call<Place>?, t: Throwable?) {
//                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
//                println(t?.message)
//            }
//
//            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
//
//                if (response != null && response.isSuccessful && response.body() != null) {
//
//                    val place: Place = response.body()!!
//
//                    // Set the date of "last updated"
//                    val currentTime = LocalDateTime.now()
//                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//                    val lastUpdated = currentTime.format(formatter)
//                    place.lastUpdated = lastUpdated
//
//                    println(place.toString())
//
//                    favouritePlaces.add(place)
//
//                    view.showFavouritePlaces(favouritePlaces)
//                }
//            }
//        })
//    }
}