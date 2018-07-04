package com.example.razli.weatherappsb.model

import com.squareup.moshi.Json

data class Place(val name: String,
                 @Json(name = "main") val weatherDetail: WeatherDetail) {
}

data class WeatherDetail(@Json(name = "temp") val temperature: Double,
                         val temp_min: Double,
                         val temp_max: Double) {

//    "main": {
//        "temp": 280.32,
//        "pressure": 1012,
//        "humidity": 81,
//        "temp_min": 279.15,
//        "temp_max": 281.15
//    }

}