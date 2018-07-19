package com.example.razli.weatherappsb.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.WeatherForecast
import kotlinx.android.synthetic.main.fragment_list_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class ForecastListAdapter(val forecastList: List<WeatherForecast>)
    : RecyclerView.Adapter<ForecastListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // Nothing
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // todo pass values for forecast here
        holder.view.placeNameTextView.text = "Country"
        holder.view.frag_timeTextView.text = "9am"
    }
}