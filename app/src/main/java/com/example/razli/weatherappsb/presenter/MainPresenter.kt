package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.NetworkApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

//        val url = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1/"
        val url = "https://samples.openweathermap.org/"

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val networkApi = retrofit.create(NetworkApi::class.java)

        val call: Call<Place> = networkApi.getPlaceWeather()

        call.enqueue(object: Callback<Place> {
            override fun onFailure(call: Call<Place>?, t: Throwable?) {
                Toast.makeText(context, "Got a response!", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
                val place: Place? = response?.body()
                Toast.makeText(context, "Got a response!", Toast.LENGTH_SHORT).show()
            }

            //
//            override fun onResponse(call: Call<List<Place>>?, response: Response<List<Place>>?) {
//                val places: List<Place>? = response?.body()
//
//                Toast.makeText(context, "Got a response!", Toast.LENGTH_SHORT).show()
//                println("onResponse")
//            }
//
//            override fun onFailure(call: Call<List<Place>>?, t: Throwable?) {
//                Toast.makeText(context, t?.message, Toast.LENGTH_SHORT).show()
//                println(t?.message)
//            }
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