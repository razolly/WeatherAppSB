package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY = "favourite_place"

    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    private var favPlaceStrings: HashSet<String> = hashSetOf()
    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val repository = Repository.instance

    override fun start() {

        // sharedPreferences.edit().clear().commit()     // deletes everything

        if (sharedPreferences.contains(STRING_KEY)) {

            favPlaceStrings.addAll(sharedPreferences.getStringSet(STRING_KEY, hashSetOf("")))

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
                            sharedPreferences.edit().putStringSet(STRING_KEY, favPlaceStrings).apply()
                            Toast.makeText(context, "$placeName added!", Toast.LENGTH_SHORT).show()

                            val placeInfo = response.body() as Place
                            val sdf = SimpleDateFormat("MMM dd, yyyy, hh:mm aaa")
                            val currentDate = sdf.format(Date())

                            placeInfo.lastUpdated = currentDate

                            view.showFavouritePlace(placeInfo)

                            printHashSet()

                        } else {
                            onFailure(null, null)
                        }
                    }
                })
    }

    override fun removePlace(place: String) {
        val placeConcatenated = place.split(",")

        favPlaceStrings.remove(placeConcatenated[0])

        sharedPreferences.edit().putStringSet(STRING_KEY, favPlaceStrings).apply()

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