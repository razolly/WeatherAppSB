package com.example.razli.weatherappsb.contract

import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

        fun addFavouritePlace(place: String)

        fun getFavouritePlaces(): HashSet<String>

    }

}