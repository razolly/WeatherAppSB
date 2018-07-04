package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract

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

            // Display
            view.showFavouritePlace(favouritePlaces)
        }
    }

    override fun start() {

    }

    override fun addFavouritePlace(place: String) {

        Log.i("BeforeContents", favouritePlaces.toString())

        favouritePlaces.add(place)

        // Display favourite places
        Log.i("AfterContents", favouritePlaces.toString())

        // Update SharedPreferences
        sharedPreferences.edit().putStringSet(STRING_KEY, favouritePlaces).apply()
        Toast.makeText(context, "Place added!", Toast.LENGTH_SHORT).show()

        view.showFavouritePlace(favouritePlaces)
    }
}