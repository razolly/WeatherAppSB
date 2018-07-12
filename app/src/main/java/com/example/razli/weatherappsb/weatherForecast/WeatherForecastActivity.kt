package com.example.razli.weatherappsb.weatherForecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.activity_weather_forecast.*

class WeatherForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        placeTextView.text = intent.getStringExtra("PLACE_NAME")
    }
}
