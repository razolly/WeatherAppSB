package com.example.razli.weatherappsb.weatherForecast

import com.example.razli.weatherappsb.util.BasePresenter
import com.example.razli.weatherappsb.util.BaseView

interface WeatherForecastContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

    }
}