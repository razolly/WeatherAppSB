package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.NetworkApi
import com.example.razli.weatherappsb.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY = "favourite_place"

    // This is persistent data (saved in SharedPreferences)
    // When app starts, these strings will be used to populate favouritePlaces: MutableList<Place>
    private var favPlaceStrings: HashSet<String> = hashSetOf()

    private var favouritePlaces: MutableList<Place> = mutableListOf()

    private var sharedPreferences: SharedPreferences

    init {
        view.setPresenter(this)

        view.showFavouritePlaces(favouritePlaces)

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        // Check if SharedPreferences exist
        if(sharedPreferences.contains(STRING_KEY)) {

            println(favPlaceStrings)

            // Fill up HashSet with Strings from SharedPreferences
            favPlaceStrings.addAll(sharedPreferences.getStringSet(STRING_KEY, hashSetOf("")))

            println(favPlaceStrings)

            // Uncomment this line to delete everything in SharedPreferences
            // sharedPreferences.edit().clear().commit()
        }
    }

    override fun start() {

    }

    override fun fetchJson(placeName: String) {

        val baseUrl = "http://api.openweathermap.org/"

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

        val call: Call<Place> = networkApi.getPlaceWeather(placeName)

        call.enqueue(object: Callback<Place> {
            override fun onFailure(call: Call<Place>?, t: Throwable?) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                println(t?.message)
            }

            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {

                if(response != null && response.isSuccessful && response.body() != null) {
                    val place: Place = response.body()!!

                    println(place.toString())

                    favouritePlaces.add(place)

                    // pass info to recyclerview to update viewholder?
                    //view.updateTemperature(place.weatherDetail.temperature)
                }
            }
        })
    }

    override fun addFavouritePlace(placeName: String) {

        // Get info for place
        fetchJson(placeName)

        // Add to HashSet
        favPlaceStrings.add(placeName)

        // Update SharedPreferences
        sharedPreferences.edit().putStringSet(STRING_KEY, favPlaceStrings).apply()
        Toast.makeText(context, placeName + " added!", Toast.LENGTH_SHORT).show()
    }
}