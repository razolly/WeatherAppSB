package com.example.razli.weatherappsb.forecast

import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast
import com.example.razli.weatherappsb.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastPresenter(private val view: ForecastContract.View)
    : ForecastContract.Presenter {

    // Note: FullForecast is the top most level of the JSON data. Its only purpose
    // is to allow us to get the List<WeatherForecast> out
    lateinit var forecastObj: FullForecast
//    var forecastList = mutableListOf<WeatherForecast>()

    override fun start() {

    }

    override fun getWeatherForecast(place: String) {

        val repository = Repository.instance

        repository.getWeatherForecast(place,
                object : Callback<FullForecast> {
                    override fun onFailure(call: Call<FullForecast>?, t: Throwable?) {
                        println("onFailure")
                    }

                    override fun onResponse(call: Call<FullForecast>?, response: Response<FullForecast>?) {
                        if (response != null && response.isSuccessful && response.body() != null) {

                            forecastObj = response.body() as FullForecast
                            val fList = forecastObj.forecastList
                            printAllWeatherForecastObjects(fList)
                        }
                    }
                })

    }

    /**
     * Only extract objects where date contains "09:00:00"
     * Should extract only 5 WeatherForecast objects
     */
    private fun printAllWeatherForecastObjects(list: List<WeatherForecast>) {

        for (index in 0 until list.size - 1) {

            println("Contents: " + list[index])

        }
    }
}