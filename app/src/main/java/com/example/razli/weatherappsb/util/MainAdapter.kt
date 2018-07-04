package com.example.razli.weatherappsb.util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.razli.weatherappsb.R
import kotlinx.android.synthetic.main.place_list_item.view.*

class MainAdapter(val favouritePlaces: HashSet<String>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val placeView = LayoutInflater.from(parent.context)
                             .inflate(R.layout.place_list_item, parent, false)

        return CustomViewHolder(placeView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.placeNameTextView.text = favouritePlaces.elementAt(position)
    }

    override fun getItemCount(): Int {
        return favouritePlaces.size
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}