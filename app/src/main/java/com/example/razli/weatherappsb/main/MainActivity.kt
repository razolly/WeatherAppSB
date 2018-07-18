package com.example.razli.weatherappsb.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.forecast.ForecastActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class MainActivity : AppCompatActivity(), MainContract.View {

    val STRING_KEY_PLACE_NAME = "PLACE_NAME"

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)
        setPresenter(presenter)

        button.setOnClickListener { addFavouritePlace() }

        // Swipe-down-to-Refresh callback
        swipe_container.setOnRefreshListener { presenter.refreshPlaceList() }

        recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun stopRefreshing() {
        swipe_container.isRefreshing = false
    }

    private fun addFavouritePlace() {

        val aPlace = editText.text.toString()

        presenter.addFavouritePlace(aPlace)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showFavouritePlaces(favouritePlaces: List<Place>) {
        adapter = MainAdapter(favouritePlaces.toMutableList(), this)

        adapter.setOnItemClickListener(object: MainAdapter.ItemListener {
            override fun onItemClick(itemView: View, position: Int) {
                openWeatherForecastActivity(itemView.placeNameTextView.text.toString())

            }
            override fun onItemLongClick(itemView: View, position: Int, identifier: String) {
                showAlertDialog(identifier)
            }
        })

        recyclerViewPlaces.adapter = adapter
    }

    override fun showFavouritePlace(favouritePlace: Place) {
        if (this::adapter.isInitialized) {
            adapter.addFavouritePlace(favouritePlace)
        } else {
            adapter = MainAdapter(mutableListOf(favouritePlace), this)

            adapter.setOnItemClickListener(object: MainAdapter.ItemListener {
                override fun onItemClick(itemView: View, position: Int) {
                    openWeatherForecastActivity(itemView.placeNameTextView.text.toString())

                }
                override fun onItemLongClick(itemView: View, position: Int, identifier: String) {
                    showAlertDialog(identifier)
                }
            })

            recyclerViewPlaces.adapter = adapter
        }
    }

    private fun openWeatherForecastActivity(place: String) {
        val intent = Intent(this, ForecastActivity::class.java)
        intent.putExtra(STRING_KEY_PLACE_NAME, place)
        startActivity(intent)
    }

    override fun showAlertDialog(place: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete")

        builder.setMessage("Would you like to delete this place?")

        builder.setPositiveButton("YES") { dialog, which ->
            presenter.removePlace(place)
        }

        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT)
    }
}