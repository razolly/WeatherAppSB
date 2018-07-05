package com.example.razli.weatherappsb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(@Json(name = "main")
                 val weatherDetail: WeatherDetail,
                 val name: String) {
}

@JsonClass(generateAdapter = true)
data class WeatherDetail(@Json(name = "temp")
                         val temperature: Double,
                         val temp_min: Double,
                         val temp_max: Double) {

}