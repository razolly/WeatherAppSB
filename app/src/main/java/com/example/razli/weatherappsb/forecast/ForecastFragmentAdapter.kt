package com.example.razli.weatherappsb.forecast

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ForecastFragmentAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    private val PAGE_COUNT = 5
    private val tabTitles = arrayOf("Day1", "Day2", "Day3", "Day4", "Day5")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return ForecastFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}