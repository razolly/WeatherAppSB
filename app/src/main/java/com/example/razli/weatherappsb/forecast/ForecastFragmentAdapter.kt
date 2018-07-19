package com.example.razli.weatherappsb.forecast

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast

class ForecastFragmentAdapter(fm: FragmentManager, private val context: Context, private val pageCount: Int, private val forecastObj: FullForecast)
    : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("Day1", "Day2", "Day3", "Day4", "Day5")

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItem(position: Int): Fragment {
        return ForecastFragment.newInstance(position + 1, forecastObj)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}