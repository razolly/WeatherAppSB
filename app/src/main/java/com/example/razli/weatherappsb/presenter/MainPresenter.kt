package com.example.razli.weatherappsb.presenter

import android.content.Context
import com.example.razli.weatherappsb.contract.MainContract

class MainPresenter(private val mView: MainContract.View, val context: Context) : MainContract.Presenter {

    private val mFavouritePlaces= arrayListOf<String>()

    init {
        mView.setPresenter(this)
    }

    override fun start() {

    }

    override fun addFavouritePlace(place: String) {
        mFavouritePlaces.add(place)
        mView.showFavouritePlace(mFavouritePlaces.last())
    }
}