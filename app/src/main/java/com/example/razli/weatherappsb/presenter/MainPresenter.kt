package com.example.razli.weatherappsb.presenter

import com.example.razli.weatherappsb.contract.MainContract

class MainPresenter(var mView: MainContract.View) : MainContract.Presenter {

    var mFavouritePlaces= arrayListOf<String>()

    init {
        mView.setPresenter(this)
    }

    override fun start() {

    }

    override fun addFavouritePlace(place: String) {
        mFavouritePlaces.add(place)
        mView.showFavouritePlace(mFavouritePlaces.get(0))
    }
}