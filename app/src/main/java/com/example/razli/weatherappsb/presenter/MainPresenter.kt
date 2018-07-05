package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.NetworkApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY = "favourite_place"
    private val PACKAGE_NAME = "com.example.razli.weatherappsb.presenter"

    private val favouritePlaces= HashSet<String>()
    private var sharedPreferences: SharedPreferences

    init {
        view.setPresenter(this)

        // Initialize SharedPreferences
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, 0)

        // Check if SharedPreferences exist
        if(sharedPreferences.contains(STRING_KEY)) {

            // Replace Hashset values with the ones from SharedPreferences
            favouritePlaces.clear()
            favouritePlaces.addAll(sharedPreferences.getStringSet(STRING_KEY, hashSetOf("")))

            // Fetch JSON
            fetchJson()
        }
    }

    override fun start() {

    }

    private fun fetchJson() {
        println("Fetching Json!")

        val baseUrl = "https://samples.openweathermap.org/"
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))

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

        val call: Call<Place> = networkApi.getPlaceWeather()

        call.enqueue(object: Callback<Place> {
            override fun onFailure(call: Call<Place>?, t: Throwable?) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                println(t?.message)
            }

            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
//                if(response?.isSuccessful()) {
                    val place: Place? = response?.body()
                println(place)

            }
        })
    }

    override fun addFavouritePlace(place: String) {

        favouritePlaces.add(place)

        // Update SharedPreferences
        sharedPreferences.edit().putStringSet(STRING_KEY, favouritePlaces).apply()
        Toast.makeText(context, "Place added!", Toast.LENGTH_SHORT).show()
    }

    override fun getFavouritePlaces(): HashSet<String> {
        return favouritePlaces
    }
}