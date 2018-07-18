package com.example.razli.weatherappsb.forecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast
import com.example.razli.weatherappsb.util.Repository
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity(), ForecastContract.View {

    private lateinit var presenter: ForecastContract.Presenter

    var fList: List<WeatherForecast> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        // placeTextView.text = intent.getStringExtra("PLACE_NAME")
        Toast.makeText(this, intent.getStringExtra("PLACE_NAME"), Toast.LENGTH_SHORT).show()

        presenter = ForecastPresenter(this)
        setPresenter(presenter)
        presenter.start()

        val place = intent.getStringExtra("PLACE_NAME")
        getWeatherForecast(place)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewpager.adapter = ForecastFragmentAdapter(supportFragmentManager, this)
        sliding_tabs.setupWithViewPager(viewpager)
    }

    override fun setPresenter(presenter: ForecastContract.Presenter) {
        this.presenter = presenter
    }

    private fun getWeatherForecast(place: String) {
        val repository = Repository.instance

        repository.getWeatherForecast(place,
                object : Callback<FullForecast> {
                    override fun onFailure(call: Call<FullForecast>?, t: Throwable?) {
                        println("onFailure")
                    }

                    override fun onResponse(call: Call<FullForecast>?, response: Response<FullForecast>?) {
                        if (response != null && response.isSuccessful && response.body() != null) {

                            val forecastObj = response.body() as FullForecast
                            fList = forecastObj.forecastList
                        }
                    }
                })
    }
}
