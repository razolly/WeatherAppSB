package com.example.razli.weatherappsb.forecast

import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastPresenter(private val view: ForecastContract.View)
    : ForecastContract.Presenter {

    private lateinit var aPlace: Place

//    init {
//        println("Presenter initialized")
//    }

    override fun start() {
        // todo initialize the Repository singleton?

        val repository = Repository.instance

        repository.getWeather("London",
                object : Callback<Place> {

                    override fun onFailure(call: Call<Place>?, t: Throwable?) {
                        println("Repo failed")
                    }

                    override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
                        if (response != null && response.isSuccessful && response.body() != null) {

                            aPlace = response.body() as Place
                            //println("Place: $aPlace")

                        } else {
                            onFailure(null, null)
                        }
                    }
                })
    }
}