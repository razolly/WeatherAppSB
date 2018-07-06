package com.example.razli.weatherappsb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(@Json(name = "main")
                 val weatherDetail: WeatherDetail,
                 @Json(name = "weather")
                 val weatherIcon: List<WeatherIcon>,
                 val name: String,
                 var lastUpdated: String = "") {
}

@JsonClass(generateAdapter = true)
data class WeatherDetail(@Json(name = "temp")
                         val temperature: Double,
                         val temp_min: Double,
                         val temp_max: Double) {

}

@JsonClass(generateAdapter = true)
data class WeatherIcon(val icon: String) {
}