package com.example.razli.weatherappsb.forecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.WeatherForecast
import kotlinx.android.synthetic.main.activity_weather_forecast.*

class ForecastActivity : AppCompatActivity(), ForecastContract.View {

    override fun passWeatherForecastToFragments(forecastList: List<WeatherForecast>) {
//        val args = Bundle()
//        val userProfileString = userProfileJsonObject.toString()
//        args.putString("userProfileString", userProfileString)
//        fragmentUserProfile.setArguments(args)
    }

    private lateinit var presenter: ForecastContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        // placeTextView.text = intent.getStringExtra("PLACE_NAME")
        Toast.makeText(this, intent.getStringExtra("PLACE_NAME"), Toast.LENGTH_SHORT).show()

        presenter = ForecastPresenter(this)
        setPresenter(presenter)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewpager.adapter = ForecastFragmentAdapter(supportFragmentManager, this)
        sliding_tabs.setupWithViewPager(viewpager)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()

        val place = intent.getStringExtra("PLACE_NAME")
        presenter.getWeatherForecast(place)
    }

    override fun setPresenter(presenter: ForecastContract.Presenter) {
        this.presenter = presenter
    }
}
