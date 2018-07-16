package com.example.razli.weatherappsb.forecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.activity_weather_forecast.*

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        // placeTextView.text = intent.getStringExtra("PLACE_NAME")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewpager.adapter = ForecastFragmentAdapter(supportFragmentManager, this)
        sliding_tabs.setupWithViewPager(viewpager)
    }
}
