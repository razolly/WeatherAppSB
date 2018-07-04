package com.example.razli.weatherappsb.contract

import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {

        fun showFavouritePlace(listOfPlaces: HashSet<String>)

    }

    interface Presenter : BasePresenter {

        fun addFavouritePlace(place: String)

        fun getFavouritePlaces(): HashSet<String>

    }

}