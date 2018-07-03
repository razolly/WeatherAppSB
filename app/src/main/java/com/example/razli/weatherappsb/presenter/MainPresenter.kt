package com.example.razli.weatherappsb.presenter

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.razli.weatherappsb.contract.MainContract

class MainPresenter(private val view: MainContract.View, val context: Context) : MainContract.Presenter {

    private val STRING_KEY = "favourite_place"
    private val PACKAGE_NAME = "com.example.razli.weatherappsb.presenter"

    private val favouritePlaces= arrayListOf<String>()
    private var sharedPreferences: SharedPreferences

    init {
        view.setPresenter(this)

        // Initialize SharedPreferences
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, 0)

        // Check if SharedPreferences exist
        if(sharedPreferences.contains(STRING_KEY)) {
            favouritePlaces.add(sharedPreferences.getString(STRING_KEY, ""))
            view.showFavouritePlace(favouritePlaces.last())
            Toast.makeText(context, "The key exists!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun start() {

    }

    override fun addFavouritePlace(place: String) {
        favouritePlaces.add(place)

        // Update SharedPreferences
        sharedPreferences.edit().putString(STRING_KEY, place).apply()
        Toast.makeText(context, "Saved to SharedPreferences", Toast.LENGTH_SHORT).show()

        view.showFavouritePlace(favouritePlaces.last())
    }
}