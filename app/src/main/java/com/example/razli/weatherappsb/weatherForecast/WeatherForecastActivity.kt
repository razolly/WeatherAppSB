package com.example.razli.weatherappsb.weatherForecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.util.Repository
import kotlinx.android.synthetic.main.activity_weather_forecast.*

class WeatherForecastActivity : AppCompatActivity(), WeatherForecastContract.View {

    private lateinit var wfPresenter: WeatherForecastContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)
        //placeTextView.text = intent.getStringExtra("PLACE_NAME")

        wfPresenter = WeatherForecastPresenter(this)
        setPresenter(wfPresenter)

        viewpager.adapter = WeatherForecastFragmentAdapter(supportFragmentManager, this)
        sliding_tabs.setupWithViewPager(viewpager)
    }

    override fun onStart() {
        super.onStart()
        wfPresenter.start()
    }

    override fun setPresenter(presenter: WeatherForecastContract.Presenter) {
        wfPresenter = presenter
    }
}
