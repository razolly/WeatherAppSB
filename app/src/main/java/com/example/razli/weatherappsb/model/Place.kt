package com.example.razli.weatherappsb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FullForecast(@Json(name = "list")
                        val forecastList: List<WeatherForecast>) : Parcelable {
}

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherForecast(@Json(name = "main")
                           val weatherDetail: WeatherDetail,

                           @Json(name = "weather")
                           val weatherIcon: List<WeatherIcon>,

                           @Json(name = "dt_txt")
                           val date: String) : Parcelable {
}

@Parcelize
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

                 var lastUpdated: String = "") : Parcelable {
}

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryDetail(val country: String) : Parcelable {
}

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherDetail(@Json(name = "temp")
                         val temperature: Double,
                         val temp_min: Double,
                         val temp_max: Double) : Parcelable {
}

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherIcon(val icon: String) : Parcelable {
}