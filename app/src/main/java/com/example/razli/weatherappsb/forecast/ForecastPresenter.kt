package com.example.razli.weatherappsb.forecast

import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastPresenter(private val view: ForecastContract.View)
    : ForecastContract.Presenter {

    lateinit var fullForecastList: FullForecast

    private lateinit var aPlace: Place

    override fun start() {

    }

    override fun getWeatherForecast(place: String) {

        val repository = Repository.instance

//        repository.getWeather("London",
//                object : Callback<Place> {
//
//                    override fun onFailure(call: Call<Place>?, t: Throwable?) {
//                        println("Repo failed")
//                    }
//
//                    override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
//                        if (response != null && response.isSuccessful && response.body() != null) {
//
//                            aPlace = response.body() as Place
//                            //println("Place: $aPlace")
//
//                        } else {
//                            onFailure(null, null)
//                        }
//                    }
//                })

        repository.getWeatherForecast(place,
                object : Callback<FullForecast> {

                    override fun onResponse(call: Call<FullForecast>?, response: Response<FullForecast>?) {
                        if(response != null && response.isSuccessful && response.body() != null) {
                            println("onResponse")
                            fullForecastList = response.body() as FullForecast
                            println(fullForecastList)
                        }
                    }

                    override fun onFailure(call: Call<FullForecast>?, t: Throwable?) {
                        println("onFailure")
                    }
                })

    }


}