package com.example.razli.weatherappsb.forecast

import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface ForecastContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

        fun getWeatherForecast(place: String)

    }
}