package com.example.razli.weatherappsb.forecast

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastFragmentAdapter(fm: FragmentManager, private val context: Context, private val pageCount: Int, private val forecastObj: FullForecast)
    : FragmentPagerAdapter(fm) {

    private val tabTitles = mutableListOf<String>()

    // If today is 20 July, values should be 20, 21, 22, 23, 24
    private val dateList = mutableListOf<Int>()

    private val forecastDay0 = mutableListOf<WeatherForecast>()
    private val forecastDay1 = mutableListOf<WeatherForecast>()
    private val forecastDay2 = mutableListOf<WeatherForecast>()
    private val forecastDay3 = mutableListOf<WeatherForecast>()
    private val forecastDay4 = mutableListOf<WeatherForecast>()

    init {
        generateTabTitleDates(pageCount)

        // Split array into 5 by date
        val list: List<WeatherForecast> = forecastObj.forecastList

        for (i in 0 until list.size) {
            // Filters according to date
            when (list[i].date.substring(8, 10).toInt()) {
                dateList[0] -> forecastDay0.add(list[i])
                dateList[1] -> forecastDay1.add(list[i])
                dateList[2] -> forecastDay2.add(list[i])
                dateList[3] -> forecastDay3.add(list[i])
                dateList[4] -> forecastDay4.add(list[i])
                else -> {
                    // Do nothing
                }
            }
        }
    }

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItem(position: Int): Fragment {

        val forecastObj: FullForecast = when (position) {
            0 -> FullForecast(forecastDay0)
            1 -> FullForecast(forecastDay1)
            2 -> FullForecast(forecastDay2)
            3 -> FullForecast(forecastDay3)
            4 -> FullForecast(forecastDay4)
            else -> {
                FullForecast(forecastDay0)
            }
        }

        return ForecastFragment.newInstance(position + 1, forecastObj)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    private fun generateTabTitleDates(pageCount: Int) {
        val sdf = SimpleDateFormat("dd/M")
        val currentDate = sdf.format(Date())

        // Get day as integer
        var day = currentDate.substring(0, 2).toInt()
        var month = currentDate[3]

        for (index in 0 until pageCount) {
            dateList.add(day)
            tabTitles.add("${day++}/$month")
        }
    }
}