package com.example.razli.weatherappsb.contract

import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {

        fun showFavouritePlaces(favouritePlaces: List<Place>)

        fun showFavouritePlace(favouritePlace: Place)

        fun stopRefreshing()

        fun showError(error: String)

        fun showAlertDialog(place: String)

    }

    interface Presenter : BasePresenter {

        fun addFavouritePlace(place: String)

        fun removePlace(place: String)

        fun refreshPlaceList()

    }

}