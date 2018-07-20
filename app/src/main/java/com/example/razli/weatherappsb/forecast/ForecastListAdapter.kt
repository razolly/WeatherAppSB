package com.example.razli.weatherappsb.forecast

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.WeatherForecast
import kotlinx.android.synthetic.main.fragment_list_item.view.*

private const val LAYOUT_LIGHT = 0
private const val LAYOUT_DARK = 1

class ForecastListAdapter(val forecastList: List<WeatherForecast>, val context: Context)
    : RecyclerView.Adapter<ForecastListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // Nothing
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
//        val aview = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)

        var view: View? = null

        when (viewType) {
            LAYOUT_DARK -> view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item_dark, parent, false)
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
            }
        }

        return CustomViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.frag_temperatureTextView.text = forecastList[position].weatherDetail.temperature.toString() + "\u00b0" + "c"
        holder.view.frag_timeTextView.text = forecastList[position].date

        val url = "http://openweathermap.org/img/w/" + forecastList[position].weatherIcon.first().icon + ".png"
        Glide.with(context).load(url).into(holder.view.frag_imageView)
    }

    override fun getItemViewType(position: Int): Int {
        val icon = forecastList[position].weatherIcon.first().icon.last()
        return when (icon) {
            'n' -> {
                LAYOUT_DARK
            }
            'd' -> {
                LAYOUT_LIGHT
            }
            else -> -1
        }
    }
}