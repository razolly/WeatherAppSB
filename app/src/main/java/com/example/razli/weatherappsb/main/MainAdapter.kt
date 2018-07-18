package com.example.razli.weatherappsb.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.place_list_item.view.*
import com.example.razli.weatherappsb.model.Place

private const val LAYOUT_LIGHT = 0
private const val LAYOUT_DARK = 1

class MainAdapter(private val favouritePlaces: MutableList<Place>, private val context: Context)
    : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    private lateinit var listener: ItemListener

    fun setOnItemClickListener(listener: ItemListener) {
        this.listener = listener
    }

    // Default Version (White Background)
    inner class CustomViewHolder(val view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onItemClick(view, position)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            listener.onItemLongClick(view, position, favouritePlaces[position].placeIdentifier)
            return true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val icon = favouritePlaces[position].weatherIcon.first().icon.last()
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

    fun addFavouritePlace(place: Place) {
        favouritePlaces.add(place)
        notifyItemInserted(favouritePlaces.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        var placeView: View? = null

        when (viewType) {
            LAYOUT_DARK -> {
                placeView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.place_list_item_dark, parent, false)
            }
            // By default, use LAYOUT_LIGHT
            else -> {
                placeView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.place_list_item, parent, false)
            }
        }

        return CustomViewHolder(placeView!!)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.isLongClickable = true

        holder.view.placeNameTextView.text = favouritePlaces[position].name + ", " + favouritePlaces[position].countryDetail.country
        holder.view.temperatureTextView.text = "Temperature: " + favouritePlaces[position].weatherDetail.temperature + "\u00b0" + "c"
        holder.view.lastUpdatedTextView.text = "Last Updated: " + favouritePlaces[position].lastUpdated

        val url = "http://openweathermap.org/img/w/" + favouritePlaces[position].weatherIcon.first().icon + ".png"

        Glide.with(context).load(url).into(holder.view.imageView)
    }

    override fun getItemCount(): Int {

        return favouritePlaces.size
    }

    interface ItemListener {
        fun onItemClick(itemView: View, position: Int)
        fun onItemLongClick(itemView: View, position: Int, identifier: String)
    }
}