package com.example.razli.weatherappsb.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.place_list_item.view.*
import com.example.razli.weatherappsb.model.Place

class MainAdapter(private val favouritePlaces: List<Place>, private val context: Context) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val placeView = LayoutInflater.from(parent.context)
                             .inflate(R.layout.place_list_item, parent, false)

        return CustomViewHolder(placeView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.placeNameTextView.text = favouritePlaces[position].name + ", " + favouritePlaces[position].countryDetail.country
        holder.view.temperatureTextView.text = "Temperature: " + favouritePlaces[position].weatherDetail.temperature + "\u00b0" + "c"
        holder.view.lastUpdatedTextView.text = "Last Updated: " + favouritePlaces[position].lastUpdated

        val url = "http://openweathermap.org/img/w/" + favouritePlaces[position].weatherIcon.first().icon + ".png"

        Glide.with(context).load(url).into(holder.view.imageView)
    }

    override fun getItemCount(): Int {

        return favouritePlaces.size
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}