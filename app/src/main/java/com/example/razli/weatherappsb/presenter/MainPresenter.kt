package com.example.razli.weatherappsb.presenter

import android.content.Context
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
    private val PACKAGE_NAME = "com.example.razli.weatherappsb.presenter"

    // todo change this to List. Will encounter problems
    private var favouritePlaces: MutableList<Place> = mutableListOf()

//    private var sharedPreferences: SharedPreferences

    init {
        view.setPresenter(this)

        view.showFavouritePlaces(favouritePlaces)

//        // Initialize SharedPreferences
        // todo use this line
//        PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, 0)
//
//        // Check if SharedPreferences exist
//        if(sharedPreferences.contains(STRING_KEY)) {
//
//            // Replace Hashset values with the ones from SharedPreferences
//            favouritePlaces.clear()
//            favouritePlaces.addAll(sharedPreferences.getStringSet(STRING_KEY, hashSetOf("")))
//
//            // todo delete this
////            sharedPreferences.edit().clear().commit()
//        }
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
                    //favouritePlaces.add(place)

                    // pass info to recyclerview to update viewholder?
                    // but wont be able to save in sharedpreferences
                    // change data type to Place and use Serializer?
                    //view.updateTemperature(place.weatherDetail.temperature)
                }
            }
        })
    }

    override fun addFavouritePlace(placeName: String) {

        // Return a Place Object
        val place = fetchJson(placeName)

        // Add to HashSet
//        favouritePlaces.add(place)

        // Update SharedPreferences
//        sharedPreferences.edit().putStringSet(STRING_KEY, favouritePlaces).apply()
//        Toast.makeText(context, "Place added!", Toast.LENGTH_SHORT).show()
    }
}