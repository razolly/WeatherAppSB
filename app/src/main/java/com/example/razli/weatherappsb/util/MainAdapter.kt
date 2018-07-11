package com.example.razli.weatherappsb.util

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.place_list_item.view.*
import com.example.razli.weatherappsb.model.Place

class MainAdapter(private val favouritePlaces: MutableList<Place>, private val context: Context)
    : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(itemView: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class CustomViewHolder(val view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onItemClick(view, position)
        }
    }


    fun addFavouritePlace(place: Place) {
        favouritePlaces.add(place)
        notifyItemInserted(favouritePlaces.size - 1)
    }

    fun removeFavouritePlace(placeIndex: Int) {
        favouritePlaces.removeAt(placeIndex)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.CustomViewHolder {
        val placeView = LayoutInflater.from(parent.context)
                .inflate(R.layout.place_list_item, parent, false)

        return CustomViewHolder(placeView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.isLongClickable = true

        holder.view.placeNameTextView.text = favouritePlaces[position].name + ", " + favouritePlaces[position].countryDetail.country
        holder.view.temperatureTextView.text = "Temperature: " + favouritePlaces[position].weatherDetail.temperature + "\u00b0" + "c"
        holder.view.lastUpdatedTextView.text = "Last Updated: " + favouritePlaces[position].lastUpdated

        val url = "http://openweathermap.org/img/w/" + favouritePlaces[position].weatherIcon.first().icon + ".png"

        Glide.with(context).load(url).into(holder.view.imageView)

        // Display AlertDialog
    }

    override fun getItemCount(): Int {

        return favouritePlaces.size
    }
}
