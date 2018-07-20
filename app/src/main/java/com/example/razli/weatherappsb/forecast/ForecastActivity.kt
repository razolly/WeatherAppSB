package com.example.razli.weatherappsb.forecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast
import com.example.razli.weatherappsb.util.Repository
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    var fList: List<WeatherForecast> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        Toast.makeText(this, intent.getStringExtra("PLACE_NAME"), Toast.LENGTH_SHORT).show()

        val place = intent.getStringExtra("PLACE_NAME")
        getWeatherForecast(place)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // todo uncomment. trying to put this in onResponse
//        viewpager.adapter = ForecastFragmentAdapter(supportFragmentManager, this, 5)
//        sliding_tabs.setupWithViewPager(viewpager)
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

                            viewpager.adapter = ForecastFragmentAdapter(supportFragmentManager, applicationContext, 5, forecastObj)
                            sliding_tabs.setupWithViewPager(viewpager)

                            // Set arguments for fragment
                            val bundle = Bundle()
                            bundle.putParcelable("FORECAST_OBJ", forecastObj)
                        }
                    }
                })
    }
}
