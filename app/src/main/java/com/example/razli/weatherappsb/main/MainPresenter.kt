package com.example.razli.weatherappsb.main

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY_SHAREDPREF = "favourite_place"

    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    private var favPlaceStrings: HashSet<String> = hashSetOf()
    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val repository = Repository.instance

    override fun start() {

        // sharedPreferences.edit().clear().commit()     // deletes everything

        // Check for internet connection
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (sharedPreferences.contains(STRING_KEY_SHAREDPREF) && isConnected) {

            favPlaceStrings.addAll(sharedPreferences.getStringSet(STRING_KEY_SHAREDPREF, hashSetOf("")))

            val size = favPlaceStrings.size
            val places = mutableListOf<Place>()

            for (index in 0 until size) {
                val favoritePlace = favPlaceStrings.elementAt(index)

                repository.getWeather(favoritePlace,
                        object : Callback<Place> {

                            override fun onFailure(call: Call<Place>?, t: Throwable?) {
                                view.showError("Error: Failed to get weather details")
                            }

                            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {

                                if (response != null && response.isSuccessful && response.body() != null) {

                                    val placeInfo = response.body() as Place
                                    val sdf = SimpleDateFormat("MMM dd, yyyy, hh:mm aaa")
                                    val currentDate = sdf.format(Date())

                                    placeInfo.lastUpdated = currentDate
                                    placeInfo.placeIdentifier = favoritePlace

                                    places.add(placeInfo)

                                    if (places.size == size) {
                                        view.showFavouritePlaces(places)
                                    }

                                    printHashSet()

                                } else {
                                    onFailure(null, null)
                                }
                            }
                        })
            }
        }

        //refreshEveryOneHour()
    }

    override fun addFavouritePlace(placeName: String) {

        repository.getWeather(placeName,
                object : Callback<Place> {

                    override fun onFailure(call: Call<Place>?, t: Throwable?) {
                        view.showError("Failed to get weather details :(")
                        Toast.makeText(context, "Failed to get weather details :(", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
                        if (response != null && response.isSuccessful && response.body() != null) {

                            // Saved to SharedPreferences & notify (via Toast)
                            favPlaceStrings.add(placeName)
                            sharedPreferences.edit().putStringSet(STRING_KEY_SHAREDPREF, favPlaceStrings).apply()
                            Toast.makeText(context, "$placeName added!", Toast.LENGTH_SHORT).show()

                            val placeInfo = response.body() as Place
                            val sdf = SimpleDateFormat("MMM dd, yyyy, hh:mm aaa")
                            val currentDate = sdf.format(Date())

                            placeInfo.lastUpdated = currentDate
                            placeInfo.placeIdentifier = placeName

                            view.showFavouritePlace(placeInfo)

                            printHashSet()

                        } else {
                            onFailure(null, null)
                        }
                    }
                })
    }

    override fun removePlace(place: String) {

        favPlaceStrings.remove(place)

        sharedPreferences.edit().putStringSet(STRING_KEY_SHAREDPREF, favPlaceStrings).apply()

        refreshPlaceList()

        printHashSet()
    }

    private fun refreshEveryOneHour() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                refreshPlaceList()
            }
        }, 0, 3600000)
    }

    override fun refreshPlaceList() {

        runnable = Runnable {
            start()

            view.stopRefreshing()
        }

        handler.postDelayed(runnable, 2000)
    }

    private fun printHashSet() {
        println(favPlaceStrings)
    }
}