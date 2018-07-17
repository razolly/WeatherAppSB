package com.example.razli.weatherappsb.forecast

import com.example.razli.weatherappsb.model.WeatherForecast
import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun displayWeatherForecast(forecast: WeatherForecast)

    }

    interface Presenter : BasePresenter {

        fun getWeatherForecast(place: String)

    }
}