package com.example.razli.weatherappsb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// region new classes
@JsonClass(generateAdapter = true)
data class FullForecast(@Json(name = "list")
                        val forecastList: List<WeatherForecast>) {
}

@JsonClass(generateAdapter = true)
data class WeatherForecast(@Json(name = "main")
                           val weatherDetail: WeatherDetail,

                           @Json(name = "weather")
                           val weatherIcon: List<WeatherIcon>,

                           @Json(name = "dt_txt")
                           val date: String) {
}
// endregion

@JsonClass(generateAdapter = true)
data class Place(@Json(name = "main")
                 val weatherDetail: WeatherDetail,

                 @Json(name = "weather")
                 val weatherIcon: List<WeatherIcon>,

                 @Json(name = "sys")
                 val countryDetail: CountryDetail,

                 @Json(name = "dt_txt")
                 var forecastDate: String = "",

                 val name: String,

                 var placeIdentifier: String = "",

                 var lastUpdated: String = "") {
}

@JsonClass(generateAdapter = true)
data class CountryDetail(val country: String) {
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